/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.entities.BoletinCambioPropietario;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class BoletinCambioPropietarioDAO {

    @PersistenceContext(unitName = "sagirDS")

    private EntityManager entityManager;
    private static final Logger LOG = Logger.getLogger(ConstanteDAO.class.getName());

    public static final String BOLETIN_CARGA = "SELECT DISTINCT PV.ID_USUARIO AS USUARIO, \n"
            + "AC.IDENTIFICADOR AS PLACA,\n"
            + "CASE WHEN PV.MIGRADO_PROPIETAR = 'M' AND DESERR IS NULL THEN 'Procesado'\n"
            + "     WHEN PV.MIGRADO_PROPIETAR = 'NO SE PROCESA LA INFORMACION DEBIDO A QUE NO SE ENCONTRO EL ARCHIVO DE VEHÍCULOS.' THEN 'El vehículo no está reportado o fue rechazado por el proceso normal de migración.'\n"
            + "     WHEN PV.MIGRADO_PROPIETAR = 'El veh????????????????culo posee tr????????????????mites con RUNT' THEN 'El vehiculo posee tramites con RUNT'\n"
            + "     WHEN deserr = 'ORA-018100381: (full) year must be between -10038713 and +9999, and not be 0' THEN 'Error de Poblamiento: Error en el campo FECHAPROPIEDAD' \n"
            + "     WHEN DESERR = 'ORA-018100383: not a valid month' then 'Error de Poblamiento: Error en el campo FECHAPROPIEDAD'\n"
            + "     WHEN deserr = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"NOMBRES\")' THEN 'Error de Poblamiento: PROPIETARIOS_VEHICULO - No se puede ingresar un valor nulo'\n"
            + "     WHEN DESERR = 'Error de Poblamiento: Fecha Mayor a la creación del RUNT' THEN 'La fecha de propiedad es superior a la fecha de inicio de operación del RUNT'\n"
            + "     WHEN deserr = 'ORA-00001: unique constraint (MIGRACIONQX.PROPIETARIOS_VEHICULO_PK) violated' THEN 'Error de poblamiento: Valores duplicados Propietarios' \n"
            + "     WHEN DESERR = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"ID_CIUDAD\")' then 'Error de Poblamiento: ID_CIUDAD - No se puede ingresar un valor nulo'\n"
            + "     WHEN DESERR = 'Error en ID_SECRETARIA: El codigo del organismo en el registro no corresponde al del archivo enviado...' THEN 'Error de poblamiento: El código del Organismo en el registro no corresponde al del archivo enviado' \n"
            + "     WHEN DESERR = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"APELLIDOS\")' THEN 'Error en poblamiento: USUARIOS_PERSONAS - APELLIDO1- No se puede ingresar un valor nulo' \n"
            + "     WHEN DESERR = 'ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"PROPIETARIOS_VEHICULO\".\"ID_DOCUMENTO\")' THEN 'Error de Poblamiento: No se encuentra el tipo de documento'\n"
            + "     WHEN DESERR = 'ORA-06502: PL/SQL: numeric or value error: character to number conversion error' THEN 'Error de poblamiento: Un campo numérico no puede contener caracteres alfanuméricos.'  \n"
            + "     WHEN DESERR IS NULL and PV.MIGRADO_PROPIETAR not in ('M','N') THEN  PV.MIGRADO_PROPIETAR\n"
            + "     WHEN DESERR = 'ORA-01830: date format picture ends before converting entire input string' then 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD'\n"
            + "     ELSE DESERR END AS ESTADO, \n"
            + "     M1.DESCRIPCION_CORTA AS NOMBREORGANISMO \n"
            + "     FROM  MIGRACIONQX.PROPIETARIOS_VEHICULO PV    \n"
            + "INNER JOIN MIGRACIONQX.ARC_CAR AC ON AC.IDENTIFICADOR = PV.NRO_PLACA\n"
            + "                               AND AC.COD_CARGA = PV.COD_CARGA\n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1 ON PV.ID_SECRETARIA = M1.ID_SECRETARIA\n"
            + "WHERE PV.COD_CARGA = ?\n"
            + "UNION\n"
            + "SELECT DISTINCT PV.ID_USUARIO AS USUARIO, \n"
            + "AC.IDENTIFICADOR AS PLACA,  \n"
            + "CASE WHEN DESERR = 'ORA-06502: PL/SQL: numeric or value error: character to number conversion error' THEN 'Error de poblamiento: Un campo numérico no puede contener caracteres alfanuméricos.' \n"
            + "      WHEN DESERR = 'ORA-018100381: (full) year must be between -10038713 and +9999, and not be 0' THEN 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD'\n"
            + "      WHEN DESERR = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"APELLIDOS\")' THEN 'Error en poblamiento: USUARIOS_PERSONAS - APELLIDO1- No se puede ingresar un valor nulo' WHEN deserr = 'Error de Poblamiento: Fecha Mayor a la creación del RUNT' THEN 'La fecha de propiedad es superior a la fecha de inicio de operacion del RUNT'\n"
            + "      WHEN DESERR = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"NOMBRES\")' THEN 'Error de Poblamiento: PROPIETARIOS_VEHICULO - No se puede ingresar un valor nulo' WHEN deserr = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_PERSONAS\".\"ID_CIUDAD\")' then 'Error de Poblamiento: ID_CIUDAD - No se puede ingresar un valor nulo'\n"
            + "      WHEN DESERR = 'Error en poblamiento: ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"USUARIOS_EMPRESAS\".\"RAZON_SOCIAL\")' THEN 'Para tipo de documento \"N\" el campo 86 no puede estar vacio.' WHEN DESERR = 'ORA-011003800: cannot insert NULL into (\"MIGRACIONQX\".\"PROPIETARIOS_VEHICULO\".\"ID_DOCUMENTO\")' THEN 'Error de Poblamiento: No se encuentra el tipo de documento'\n"
            + "      WHEN DESERR = 'ORA-018100383: not a valid month' THEN 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD' WHEN DESERR = 'ORA-01839: date not valid for month specified' THEN 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD' \n"
            + "      WHEN DESERR = 'ORA-01830: date format picture ends before converting entire input string' THEN 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD'\n"
            + "      WHEN DESERR = 'ORA-018100387: day of month must be between 1 and last day of month' then 'Error de Poblamiento: Error en el campo FECHA DE PROPIEDAD' \n"
            + "      ELSE DESERR END AS ESTADO,\n"
            + "M1.DESCRIPCION_CORTA AS NOMBREORGANISMO \n"
            + "FROM  MIGRACIONQX.PROPIETARIOS_VEHICULO PV   \n"
            + "RIGHT JOIN MIGRACIONQX.ARC_CAR AC ON AC.IDENTIFICADOR = PV.NRO_PLACA\n"
            + "                               AND AC.COD_CARGA = PV.COD_CARGA\n"
            + "INNER JOIN MIGRUNT1.ORGANISMOS_TRANSITO M1 ON AC.NOMOT = M1.ID_SECRETARIA\n"
            + "WHERE PV.NRO_PLACA IS NULL AND AC.COD_CARGA = ?";

    public List<BoletinCambioPropietario> consultaBoletinCambioPropietario(final Long idCarga) {
        try {
            Query query = entityManager.createNativeQuery(BOLETIN_CARGA, "boletinCambioPropietario");
            query.setParameter(1, idCarga);
            query.setParameter(2, idCarga);
            return query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al consultar el boletin de la carga {0}", idCarga);
        }
        return Collections.emptyList();
    }
    
    public SgCarguearchivos consultaIdCarga (final Long idCarga){
        try {
            return (SgCarguearchivos) entityManager.createNamedQuery("SgCarguearchivos.findByCarguearchivosId")
                    .setParameter(Constantes.CODCARGA, idCarga)
                    .getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }   
        return null;
    }
}
