/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.utils;

import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Fabrica de Dozer
 *
 * @author Juan Pablo Quiroga
 *
 */
public class FabricaDozer {

    /**
     * Archivo de configuración de dozer
     */
    public static final String DOZER_CONFIG = FabricaDozer.class.getResource("dozerMapping.xml").toString();

    /**
     * Unica instancia de la fabrica
     */
    protected static FabricaDozer instancia = new FabricaDozer();

    /**
     * Mapper de dozer para realizar las transformaciones
     */
    private DozerBeanMapper mapper;

    /**
     * Construye la única instancia de dozer
     */
    protected FabricaDozer() {
        mapper = new DozerBeanMapper();
        List<String> myMappingFiles = buildMappingFiles();
        mapper.setMappingFiles(myMappingFiles);
        for (String arch : mapper.getMappingFiles()) {
            System.out.println("arch " + arch);
        }
    }

    /**
     * Construye el mapping de archivos que contienen la configuración de dozer
     *
     * @return Listado de archivos de configuración
     */
    protected List<String> buildMappingFiles() {
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(DOZER_CONFIG);
        return myMappingFiles;
    }

    /**
     * Unica instancia de la clase
     *
     * @return Instancia
     */
    public static FabricaDozer getInstancia() {
        return instancia;
    }

    /**
     * Obtiene el mapper asociado a Dozer
     *
     * @return Mapper de Dozer
     */
    public Mapper getMapper() {
        return mapper;
    }
}
