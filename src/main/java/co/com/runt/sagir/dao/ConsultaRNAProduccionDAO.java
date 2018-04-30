/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dao;

import co.com.runt.sagir.entities.ConsultaRNA;
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
public class ConsultaRNAProduccionDAO {

    private static final Logger LOGGER = Logger.getLogger(ConsultaRNAProduccionDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")

    EntityManager entityManager;

    private static final String QUERY_CONSULTA_RNA_SAGIR = "SELECT RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA AS IDOT, \n"
            + "ot.empresa_razosocia AS NOMBREOT,           \n"
            + "RA.AUTOMOTOR_NROREGVEH AS IDVEHICULO,		  \n"
            + "RA.AUTOMOTOR_PLACA_NUMPLACA AS PLACA,	\n"
            + "RA.AUTOMOTOR_ESTAVEHIC_NOMBRE AS ESTADOVEHIC,\n"
            + "CV.CLASVEHIC_NOMBRE AS CLASE,\n"
            + "SER.TIPOSERVI_NOMBRE AS SERVICIO, \n"
            + "TO_CHAR(RA.AUTOMOTOR_FECHREGIS, 'DD/MM/YYYY') AS FECHAMATRICULA,\n"
            + "CASE WHEN RA.AUTOMOTOR_MIGRADO = 'M' THEN 'MIGRADO' ELSE RA.AUTOMOTOR_MIGRADO END AS MIGRADO,\n"
            + "TO_CHAR(RA.AUTOMOTOR_FECMIGRA, 'DD/MM/YYYY') AS FECMIGRA,\n"
            + "RA.AUTOMOTOR_MODELO AS MODELO,\n"
            + "MR.MARCA_NOMBRE AS MARCA,   \n"
            + "TC.TIPOCARRO_NOMBRE AS CARROCERIA, \n"
            + "LN.LINEA_NOMBRE AS LINEA,\n"
            + "TEC.TECNAUTOM_CAPACARGA AS CARGA,   \n"
            + "TEC.TECNAUTOM_PESO AS PESO, \n"
            + "RA.AUTOMOTOR_NROCHASIS AS NROCHASIS,\n"
            + "RA.AUTOMOTOR_NROMOTOR AS NROMOTOR,   \n"
            + "RA.AUTOMOTOR_NROSERIE AS NROSERIE,  \n"
            + "MS.MODASERVI_NOMBRE AS MODASERVI,\n"
            + "PTC.TIPOCOMBU_DESCRIPCI AS COMBUSTIBLE\n"
            + "FROM RUNTPROD.RA_AUTOMOTOR RA\n"
            + "LEFT JOIN RUNTPROD.GE_AUTOTRANS AUT ON AUT.AUTOTRANS_IDAUTTRA = RA.AUTOMOTOR_AUTOTRANS_IDAUTTRA\n"
            + "LEFT JOIN RUNTPROD.PA_TIPOSERVI SER ON SER.TIPOSERVI_IDETIPSER = RA.AUTOMOTOR_TIPOSERVI_IDETIPSER\n"
            + "LEFT JOIN RUNTPROD.GE_EMPRESA OT ON OT.EMPRESA_PERSONA_IDPERSONA = AUT.AUTOTRANS_EMPRESA_PERSONA\n"
            + "LEFT JOIN RUNTPROD.PA_CLASVEHIC CV ON CV.CLASVEHIC_IDCLASE = RA.AUTOMOTOR_CLASVEHIC_IDCLASE\n"
            + "LEFT JOIN RUNTPROD.PA_COLOR CL ON CL.COLOR_IDCOLOR = RA.AUTOMOTOR_COLOR_IDCOLOR\n"
            + "LEFT JOIN RUNTPROD.PA_LINEA LN ON LN.LINEA_MARCA_IDMARCA = RA.AUTOMOTOR_MARCA_IDMARCA\n"
            + "                                AND LN.LINEA_IDLINEA = RA.AUTOMOTOR_LINEA_IDLINEA\n"
            + "LEFT JOIN RUNTPROD.PA_MARCA MR ON MR.MARCA_IDMARCA = RA.AUTOMOTOR_MARCA_IDMARCA\n"
            + "LEFT JOIN RUNTPROD.PA_MODASERVI MS ON MS.MODASERVI_IDEMODSER = RA.AUTOMOTOR_MODASERVI_IDEMODSER\n"
            + "LEFT JOIN RUNTPROD.PA_TIPOCARRO TC ON TC.TIPOCARRO_CLASVEHIC_IDCLASE = RA.AUTOMOTOR_CLASVEHIC_IDCLASE\n"
            + "                                    AND TC.TIPOCARRO_IDCARROCE = RA.AUTOMOTOR_TIPOCARRO_IDCARROCE\n"
            + "LEFT JOIN RUNTPROD.RA_TECNAUTOM TEC ON TEC.TECNAUTOM_AUTOMOTOR_NROREGVEH = RA.AUTOMOTOR_NROREGVEH\n"
            + "LEFT JOIN RUNTPROD.RA_TIPCOMVEH TCV ON TCV.TIPCOMVEH_AUTOMOTOR_NROREGVEH = RA.AUTOMOTOR_NROREGVEH\n"
            + "LEFT JOIN RUNTPROD.PA_TIPOCOMBU PTC ON PTC.TIPOCOMBU_NOMBRE = TCV.TIPCOMVEH_TIPOCOMBU_NOMBRE\n"
            + "WHERE RA.AUTOMOTOR_PLACA_NUMPLACA = ? \n"
            + "AND TCV.TIPCOMVEH_ESTADO_NOMBRE = 'ACTIVO'";

    /**
     * Metodo encargado de consultar las placas utilizadas por autoridad y fecha
     *
     * @param placa
     * @return 
     */
    public List<ConsultaRNA> consultaRNASagir(final String placa) {
        try {
            Query query = entityManager.createNativeQuery(QUERY_CONSULTA_RNA_SAGIR, "consultaRNASagir");
            query.setParameter(1, placa);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar el automotor identificado con {0}", placa);
        }
        return Collections.emptyList();
    }

}
