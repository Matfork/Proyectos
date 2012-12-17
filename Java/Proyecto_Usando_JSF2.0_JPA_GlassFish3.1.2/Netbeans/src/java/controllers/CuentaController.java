package controllers;

import model.entities.Cuenta;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import session.CuentaFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import model.entities.Usuario;
import model.entities.UsuarioLogeado;

@ManagedBean(name = "cuentaController")
@SessionScoped
public class CuentaController implements Serializable {

    private Cuenta current;
    private DataModel items = null;
    @EJB
    private session.CuentaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CuentaController() {
    }

    public Cuenta getSelected() {
        if (current == null) {
            current = new Cuenta();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CuentaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Cuenta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cuenta();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            //current.setIdcuenta(new BigDecimal(0));
            //getFacade().create(current);
            /*
            System.out.println(current.getIdtipocuenta().getIdtipocuenta());
            System.out.println(current.getIdcliente().getIdcliente());
            System.out.println(current.getIdusuario().getIdlocal().getIdlocal());
            System.out.println(current.getIdusuario().getIdusuario());
            System.out.println(current.getSaldo());
            */
            UsuarioLogeado us = (UsuarioLogeado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            Usuario u = new Usuario();
                    u.setIdlocal(us.getIdlocal());
                    u.setIdusuario(us.getIdusuario());
                    
            current.setIdusuario(u);
                     
            getFacade().p_apertura_cuenta(current);
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CuentaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e,e.getMessage()+" "+ ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cuenta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CuentaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cuenta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
      //  recreatePagination();
      //  recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
         //   getFacade().remove(current);
            
            getFacade().p_cierre_cuenta(current);
            current.setFlagestado('0'); // para que se renderize la lista de la pagina
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CuentaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Cuenta.class)
    public static class CuentaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CuentaController controller = (CuentaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cuentaController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cuenta) {
                Cuenta o = (Cuenta) object;
                return getStringKey(o.getIdcuenta());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CuentaController.class.getName());
            }
        }
    }
    
    
    public String irACuentaController(){
        recreateModel();
        return "/views/cuenta/List";
    }
}
