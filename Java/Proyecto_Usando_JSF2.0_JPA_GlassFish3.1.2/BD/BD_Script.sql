 --DROP USER tm4_bancoEureka CASCADE;

CREATE USER tm4_bancoEureka IDENTIFIED BY tm4_bancoEureka;
GRANT CONNECT, RESOURCE TO tm4_bancoEureka;
CONNECT tm4_bancoEureka/tm4_bancoEureka;

CREATE TABLE CLIENTE
(
	idCliente            CHAR(6) NOT NULL ,
	nombreCompleto       VARCHAR2(60) NULL 
);

CREATE UNIQUE INDEX XPKCLIENTE ON CLIENTE (idCliente   ASC);
ALTER TABLE CLIENTE ADD CONSTRAINT  XPKCLIENTE PRIMARY KEY (idCliente);

CREATE TABLE CUENTA
(
	idCuenta             INTEGER NOT NULL ,
	saldo                NUMBER(7,2) NULL ,
	idCliente            CHAR(6) NULL ,
	idTipoCuenta         INTEGER NULL ,
	idLocal              INTEGER NULL ,
	claveCuenta          NUMBER(4) NULL ,
	flagEstado           CHAR(1) NULL ,
	codCuenta            CHAR(6) NULL ,
	idUsuario            CHAR(6) NULL 
);


CREATE UNIQUE INDEX XPKCUENTA ON CUENTA (idCuenta   ASC);
ALTER TABLE CUENTA	ADD CONSTRAINT  XPKCUENTA PRIMARY KEY (idCuenta);
 
CREATE TABLE LOCAL
(
	idLocal              INTEGER NOT NULL ,
	nomLocal             VARCHAR2(40) NULL 
);

CREATE UNIQUE INDEX XPKLOCAL ON LOCAL(idLocal   ASC);
ALTER TABLE LOCAL	ADD CONSTRAINT  XPKLOCAL PRIMARY KEY (idLocal);

CREATE TABLE MOVIMIENTO
(
	numMovimiento        INTEGER NULL ,	
	idCliente            CHAR(6) NULL ,
	idTipoMovimiento     INTEGER NULL ,
	fechaMovimiento      DATE NULL ,
	idLocal              INTEGER NULL ,
	cantMovimiento       NUMBER(6,2) NULL ,
	idMovimiento         INTEGER NOT NULL ,
  codCuenta            CHAR(6) NULL 
);

CREATE UNIQUE INDEX XPKMOVIMIENTO ON MOVIMIENTO(idMovimiento   ASC);
ALTER TABLE MOVIMIENTO	ADD CONSTRAINT  XPKMOVIMIENTO PRIMARY KEY (idMovimiento);

CREATE TABLE OPERACION
(
	idOperacion          INTEGER NOT NULL ,
	fechaOperacion       DATE NULL ,
	idUsuario            CHAR(6) NULL ,	
	idLocal              INTEGER NULL ,
	idTipoOperacion      INTEGER NULL ,
  codCuenta            CHAR(6) NULL 
);


CREATE UNIQUE INDEX XPKOPERACION ON OPERACION(idOperacion   ASC);
ALTER TABLE OPERACION	ADD CONSTRAINT  XPKOPERACION PRIMARY KEY (idOperacion);

CREATE TABLE TIPO_CUENTA
(
	idTipoCuenta         INTEGER NOT NULL ,
	tipoCuenta           VARCHAR2(20) NULL 
);

CREATE UNIQUE INDEX XPKTIPO_CUENTA ON TIPO_CUENTA(idTipoCuenta   ASC);
ALTER TABLE TIPO_CUENTA	ADD CONSTRAINT  XPKTIPO_CUENTA PRIMARY KEY (idTipoCuenta);

CREATE TABLE TIPO_MOVIMIENTO
(
	idTipoMovimiento     INTEGER NOT NULL ,
	tipoMovimiento       VARCHAR2(30) NULL 
);

CREATE UNIQUE INDEX XPKTIPO_MOVIMIENTO ON TIPO_MOVIMIENTO(idTipoMovimiento   ASC);
ALTER TABLE TIPO_MOVIMIENTO	ADD CONSTRAINT  XPKTIPO_MOVIMIENTO PRIMARY KEY (idTipoMovimiento);

CREATE TABLE TIPO_OPERACION
(
	idTipoOperacion      INTEGER NOT NULL ,
	tipoOperacion        VARCHAR2(30) NULL 
);

CREATE UNIQUE INDEX XPKTIPO_OPERACION ON TIPO_OPERACION(idTipoOperacion   ASC);
ALTER TABLE TIPO_OPERACION	ADD CONSTRAINT  XPKTIPO_OPERACION PRIMARY KEY (idTipoOperacion);

CREATE TABLE USUARIO
(
	idUsuario            CHAR(6) NOT NULL ,
	nomUsuario           VARCHAR2(60) NULL ,
	nick                 VARCHAR2(20) NULL ,
	clave                VARCHAR2(40) NULL ,
	idLocal              INTEGER NULL 
);

CREATE UNIQUE INDEX XPKUSUARIO ON USUARIO(idUsuario   ASC);
ALTER TABLE USUARIO	ADD CONSTRAINT  XPKUSUARIO PRIMARY KEY (idUsuario);

ALTER TABLE CUENTA	ADD (CONSTRAINT R_2 FOREIGN KEY (idCliente) REFERENCES CLIENTE (idCliente) ON DELETE SET NULL);
ALTER TABLE CUENTA	ADD (CONSTRAINT R_6 FOREIGN KEY (idTipoCuenta) REFERENCES TIPO_CUENTA (idTipoCuenta) ON DELETE SET NULL);
ALTER TABLE CUENTA	ADD (CONSTRAINT R_8 FOREIGN KEY (idLocal) REFERENCES LOCAL (idLocal) ON DELETE SET NULL);
ALTER TABLE CUENTA	ADD (CONSTRAINT R_17 FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario) ON DELETE SET NULL);
--ALTER TABLE MOVIMIENTO	ADD (CONSTRAINT R_4 FOREIGN KEY (idCuenta) REFERENCES CUENTA (idCuenta) ON DELETE SET NULL);
ALTER TABLE MOVIMIENTO	ADD (CONSTRAINT R_5 FOREIGN KEY (idCliente) REFERENCES CLIENTE (idCliente) ON DELETE SET NULL);
ALTER TABLE MOVIMIENTO	ADD (CONSTRAINT R_7 FOREIGN KEY (idTipoMovimiento) REFERENCES TIPO_MOVIMIENTO (idTipoMovimiento) ON DELETE SET NULL);
ALTER TABLE MOVIMIENTO	ADD (CONSTRAINT R_9 FOREIGN KEY (idLocal) REFERENCES LOCAL (idLocal) ON DELETE SET NULL);
ALTER TABLE OPERACION	ADD (CONSTRAINT R_12 FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario) ON DELETE SET NULL);
--ALTER TABLE OPERACION	ADD (CONSTRAINT R_13 FOREIGN KEY (idCuenta) REFERENCES CUENTA (idCuenta) ON DELETE SET NULL);
ALTER TABLE OPERACION	ADD (CONSTRAINT R_14 FOREIGN KEY (idLocal) REFERENCES LOCAL (idLocal) ON DELETE SET NULL);
ALTER TABLE OPERACION	ADD (CONSTRAINT R_15 FOREIGN KEY (idTipoOperacion) REFERENCES TIPO_OPERACION (idTipoOperacion) ON DELETE SET NULL);
ALTER TABLE USUARIO	ADD (CONSTRAINT R_11 FOREIGN KEY (idLocal) REFERENCES LOCAL (idLocal) ON DELETE SET NULL);

ALTER TABLE CUENTA add CONSTRAINT U_COD_CUENTA UNIQUE (codCuenta);
ALTER TABLE OPERACION	ADD (CONSTRAINT R_16 FOREIGN KEY (codCuenta) REFERENCES CUENTA (codCuenta) ON DELETE SET NULL);
ALTER TABLE MOVIMIENTO	ADD (CONSTRAINT R_20 FOREIGN KEY (codCuenta) REFERENCES CUENTA (codCuenta) ON DELETE SET NULL);


/*****************************************
Creamos secuencias para autoincrementales
*****************************************/
   
create sequence sq_id_cuenta
   start with 1 
   increment by 1 
   nomaxvalue;
   
create sequence sq_id_movimiento
   start with 1 
   increment by 1 
   nomaxvalue;

   
create sequence sq_id_operacion
   start with 1 
   increment by 1 
   nomaxvalue;

/***************************
Procedimientos Almacenados
****************************/
/
-- A: Auntencacion de Usuario
   create or replace procedure p_verificarLogin(
        p_nick usuario.nick%type,   
        p_contrasenia usuario.clave%type ,
        p_recordset OUT SYS_REFCURSOR       
    ) 
    AS
    BEGIN  
     OPEN p_recordset FOR
     
     SELECT idusuario, nomusuario, nick, clave, u.idlocal, l.nomlocal
     FROM USUARIO U, LOCAL L
     WHERE u.idlocal = l.idlocal 
     AND enc_dec.decrypt(clave)=p_contrasenia AND nick=p_nick;
        
    END;   
/

-- B:CRUD CLIENTE
   -- Select Todos los cliente
   create or replace procedure p_select_todos_cliente(
        p_recordset OUT SYS_REFCURSOR       
    ) 
    AS      
    BEGIN  
     OPEN p_recordset FOR
          SELECT idcliente, nombrecompleto FROM CLIENTE;     
    END;   
 
  /  
   -- Get One cliente
   create or replace procedure p_get_one_usuario(
        p_codigo_cliente cliente.idcliente%type,
        p_recordset OUT SYS_REFCURSOR       
    ) 
    AS      
    BEGIN  
     OPEN p_recordset FOR
        SELECT idcliente, nombrecompleto FROM CLIENTE
        WHERE idcliente = p_codigo_cliente;
    END;  
  /
   -- Delete cliente
   create or replace procedure p_delete_cliente(
        p_codigo_cliente cliente.idcliente%type       
    ) 
    AS      
    BEGIN       
       DELETE FROM CLIENTE
        WHERE idcliente = p_codigo_cliente;
    END;  
  /  
    -- insert cliente
   create or replace procedure p_insert_cliente(        
        p_nombre_cliente cliente.nombrecompleto%type        
      --  ,p_MENSAJE out varchar2        
    ) 
    AS      
       p_codigo_cliente cliente.idcliente%type;       
    BEGIN             
      SELECT concat('CL',lpad(to_number(substr(MAX(idcliente), 3, 5))+1,4,0 )) INTO p_codigo_cliente FROM CLIENTE;
      
      IF p_codigo_cliente='CL' THEN
        p_codigo_cliente:='CL0001' ;
      END IF; 
       
      INSERT INTO CLIENTE VALUES (p_codigo_cliente,p_nombre_cliente );
     -- p_MENSAJE := 'Se inserto correctamente la fila';
      COMMIT;
    END;  
  /
  -- call p_insert_cliente('JUAN VARGAS');
      
  /  
    -- UPDATE cliente
   create or replace procedure p_update_cliente(       
        p_codigo_cliente cliente.idcliente%type,
        p_nombre_cliente cliente.nombrecompleto%type
    ) 
    AS            
    BEGIN                   
      UPDATE CLIENTE SET nombrecompleto=p_nombre_cliente WHERE idcliente = p_codigo_cliente;    
      COMMIT;
    END;  

/
  -- call p_update_usuario('U00001','Daniel Hernandez Vasquez','matfork','matfork',1);
  
-- C: APERTURA DE UNA CUENTA
   create or replace procedure p_apertura_cuenta(
        p_idTipoCuenta  cuenta.idtipocuenta%type,
        p_idCliente cuenta.idcliente%type,
        p_idLocal cuenta.idLocal%type,        
        p_idUsuario cuenta.idUsuario%type,
        p_saldo cuenta.saldo%type
    ) 
    AS    
      p_codCuenta cuenta.codcuenta%type;  
    BEGIN          
       SELECT concat('C',lpad(to_number(substr(MAX(codcuenta), 2, 5))+1,5,0 )) INTO p_codCuenta FROM CUENTA;
      
        IF p_codCuenta ='C' THEN
          p_codCuenta:='C00001' ;
        END IF; 
    
       INSERT INTO CUENTA VALUES (SQ_ID_CUENTA.nextval,p_saldo,p_idCliente,p_idTipoCuenta,p_idLocal,0000,1,p_codCuenta, p_idUsuario );
       COMMIT;
    END;   
    
/
-- CALL p_apertura_cuenta (1,NULL,1,NULL);
-- INSERT INTO CUENTA VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,'C00001',NULL);
-- SELECT * FROM operacion;

-- D: Registro de Movimientos
   create or replace procedure p_registro_movimientos(
        p_codCuenta  movimiento.codCuenta%type,
        p_idCliente movimiento.idcliente%type,
        p_tipoMovimiento movimiento.idtipoMovimiento%type,        
        p_idLocal movimiento.idlocal%type,        
        p_cantMovimiento movimiento.cantMovimiento%type        
    ) 
    AS    
      p_numMovimiento movimiento.numMovimiento%type;  
      p_tipoCuenta cuenta.idtipocuenta%type;
      p_auxiliar cuenta.saldo%type;
    BEGIN          
       
      -- Se debe de verificar que si es un retiro, tengamos el saldo suficiente para poder retirar el dinero       
        IF p_tipoMovimiento = 2 THEN
           SELECT saldo-p_cantMovimiento INTO p_auxiliar FROM CUENTA WHERE codcuenta = p_codCuenta;
           IF p_auxiliar < 0 THEN 
              RAISE_APPLICATION_ERROR(-20002,'Usted no cuenta con el saldo suficiente para realizar un retiro');
           ELSE
              UPDATE CUENTA SET SALDO=SALDO-p_cantMovimiento WHERE codcuenta = p_codCuenta;   -- Procedemos a retirar el monto propicion
           END IF;
        ELSE
          UPDATE CUENTA SET SALDO=SALDO+p_cantMovimiento WHERE codcuenta = p_codCuenta;       -- Procedemos a depositar el monto propicion
        END IF;
       
       --Seleccionamos el ultimo movimiento realizado por dicha cuenta en el mes vigente
       SELECT MAX(numMovimiento) INTO p_numMovimiento
       FROM movimiento 
       WHERE fechamovimiento between trunc(sysdate,'MM') and last_day(sysdate)   --Entre el primer y ultimo dia del mes actual
       AND codcuenta = p_codCuenta ;
    
       IF p_numMovimiento IS NULL THEN
          p_numMovimiento := 0;
       END IF;
       
       p_numMovimiento := p_numMovimiento +1;
       
       -- Si ya expiraron sus 7 movimientos gratis por del mes, entonces se le debe de cobrar por hacer un movimiento
         IF p_numMovimiento >= 8 THEN
            SELECT idtipocuenta INTO p_tipoCuenta FROM CUENTA WHERE codcuenta = p_codCuenta;
            --Para soles
            IF p_tipoCuenta = 1 THEN
              UPDATE CUENTA SET SALDO=SALDO-3 WHERE codcuenta = p_codCuenta;
            ELSE
              --Para Dolares
              UPDATE CUENTA SET SALDO=SALDO-1 WHERE codcuenta = p_codCuenta;
            END IF;            
         END IF;
         
        INSERT INTO MOVIMIENTO VALUES (p_numMovimiento,p_idCliente,p_tipoMovimiento,sysdate,p_idLocal,p_cantMovimiento,SQ_ID_MOVIMIENTO.nextval,p_codCuenta  );
        COMMIT;
    END;   
    
    --SELECT * FROM cuenta;
    --SELECT * FROM movimiento;

   -- CALL p_registro_movimientos('C00001','CL0001',1,2,500);
/

-- E: CIERRE DE UNA CUENTA
   CREATE OR REPLACE PROCEDURE p_cierre_cuenta(
        p_codCuenta  cuenta.codCuenta%type
    ) 
    AS         
    BEGIN              
       UPDATE CUENTA SET flagestado=0 WHERE codCuenta=p_codCuenta ;
       COMMIT;
    END;   

/

-- F: Reportes de Movimientos de una Cuenta
   create or replace procedure p_reportes_movimiento_cuenta(
        p_codCuenta cuenta.codCuenta%type ,
        p_recordset OUT SYS_REFCURSOR       
    ) 
    AS
      p_codUsuario usuario.idusuario%type;
      p_codLocal usuario.idlocal%type;
    BEGIN  
    
 /*   SELECT u.idusuario, u.idlocal INTO p_codUsuario,p_codLocal  FROM cuenta c
    INNER JOIN usuario u ON u.idusuario = c.idusuario
    WHERE c.codCuenta=p_codCuenta;
    
    INSERT INTO operacion VALUES (sq_id_operacion.NEXTVAL,SYSDATE,p_codUsuario,p_codLocal,2,p_codCuenta);
   */  
    OPEN p_recordset FOR     
      SELECT m.nummovimiento AS NUMMOV, m.idcliente, m.idtipomovimiento, m.fechamovimiento, m.idlocal, m.cantmovimiento, m.idmovimiento, m.codcuenta, c.nombrecompleto, l.nomlocal, tm.tipomovimiento AS TIPOMOV
      FROM MOVIMIENTO m, LOCAL l, CLIENTE c , tipo_movimiento TM
      WHERE m.idlocal = l.idlocal
      AND c.idcliente = m.idcliente 
      AND tm.idtipomovimiento = m.idtipomovimiento
      AND CODCUENTA = p_codCuenta;        
    END; 
    
/

/
-- G: Reportes de Operaciones de una Cuenta
   create or replace procedure p_reportes_ope_fecha_local(
        p_fechaOperacion DATE,
        p_idLocal operacion.idlocal%type, 
        p_recordset OUT SYS_REFCURSOR       
    ) 
    AS  
    BEGIN   
      OPEN p_recordset FOR     
          SELECT op.idoperacion idop, op.idusuario idus, op.codcuenta , top.tipooperacion tiop, lo.nomlocal nomlocal, op.fechaoperacion fecha, lo.idlocal codlocal, top.idtipooperacion codtipo
          FROM operacion op, tipo_operacion top, local lo
          WHERE top.idtipooperacion = op.idtipooperacion
            AND lo.idlocal = op.idlocal
            AND op.idlocal=p_idLocal
            AND TRUNC(op.fechaoperacion)=p_fechaOperacion;
    END; 
    
/

--CALL p_reportes_ope_fecha_local('14/12/2012',1,@resultado);
/*************************
 Insertando algunas Filas
**************************/

INSERT INTO LOCAL VALUES (1,'San Juan de Miraflores');
INSERT INTO LOCAL VALUES (2,'ATE');
INSERT INTO LOCAL VALUES (3,'Lince');
INSERT INTO LOCAL VALUES (4,'San Isidro');
INSERT INTO LOCAL VALUES (5,'La Molina');

INSERT INTO TIPO_MOVIMIENTO VALUES (1,'Deposito');
INSERT INTO TIPO_MOVIMIENTO VALUES (2,'Retiro');

INSERT INTO TIPO_CUENTA VALUES (1,'Soles');
INSERT INTO TIPO_CUENTA VALUES (2,'Dolares');

INSERT INTO TIPO_OPERACION VALUES (1,'Apertura');
INSERT INTO TIPO_OPERACION VALUES (2,'Reporte Movimientos');
INSERT INTO TIPO_OPERACION VALUES (3,'Reporte Operacion');
INSERT INTO TIPO_OPERACION VALUES (4,'Cierre');

INSERT INTO USUARIO VALUES ('U00001','Daniel Hernandez Vasquez','matfork',enc_dec.encrypt('matfork'),1);
INSERT INTO USUARIO VALUES ('U00002','Juan Perez Perez','jperez',enc_dec.encrypt('jperez'),2);
INSERT INTO USUARIO VALUES ('U00003','Pamela Miranda Mejia','pmiranda',enc_dec.encrypt('pmiranda'),2);
INSERT INTO USUARIO VALUES ('U00004','Kimberly Clark','clark',enc_dec.encrypt('clark'),3);

COMMIT;
/*****************
    Triggers
*****************/
/
CREATE OR REPLACE TRIGGER TR_CUENTA
AFTER INSERT OR UPDATE OR DELETE ON CUENTA 
FOR EACH ROW
  DECLARE       
  BEGIN
    IF Inserting THEN  
      INSERT INTO OPERACION VALUES (sq_id_operacion.NEXTVAL,SYSDATE, :new.idUsuario, :new.idLocal, 1, :new.codCuenta );
    END IF;
    
    IF Updating THEN  
      IF :new.flagEstado = 0 THEN
        INSERT INTO OPERACION VALUES (sq_id_operacion.NEXTVAL,SYSDATE, :new.idUsuario, :new.idLocal, 4, :new.codCuenta );
      END IF;
    END IF;
  END;
/          
