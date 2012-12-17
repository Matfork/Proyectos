package controllers;

import model.entities.Movimiento;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import session.MovimientoFacade;

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
import javax.swing.JOptionPane;
import model.entities.Cuenta;

@ManagedBean(name = "movimientoController")
@SessionScoped
public class MovimientoController implements Serializable {

    private Movimiento current;
    private DataModel items = null;
    @EJB
    private session.MovimientoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    //Para la llamada al Facacade Procedures
    @EJB
    private session.ProceduresFacade proceduresFacade;
    
    
     private Cuenta cuenta;
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public MovimientoController() {
    }

    public Movimiento getSelected() {
        if (current == null) {
            current = new Movimiento();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MovimientoFacade getFacade() {
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
        current = (Movimiento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Movimiento();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        
        try {
         //   getFacade().create(current);
        
          //  current.setIdmovimiento(new BigDecimal(0) );
                current.setCodcuenta(cuenta.getCodcuenta());
                current.setIdcliente(cuenta.getIdcliente());
                
             System.out.println(current.getCodcuenta()+"  "+ current.getIdcliente()+"  "+ current.getIdtipomovimiento()+"  "+ current.getIdlocal()+"  "+ current.getCantmovimiento());     
            
            proceduresFacade.p_registro_movimientos(current);
                    
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientoCreated"));
            
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage()+"aca:"+ ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           //  JsfUtil.addErrorMessage(e, e.getMessage());
            return null;
        }
    }

    public String prepareEdit() {
        current = (Movimiento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Movimiento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
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
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientoDeleted"));
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

    @FacesConverter(forClass = Movimiento.class)
    public static class MovimientoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientoController controller = (MovimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientoController");
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
            if (object instanceof Movimiento) {
                Movimiento o = (Movimiento) object;
                return getStringKey(o.getIdmovimiento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MovimientoController.class.getName());
            }
        }
    }
}
