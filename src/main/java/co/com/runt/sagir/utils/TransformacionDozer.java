/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.utils;

import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;

/**
 * Realiza transformaciones hacia Dozer de listas y objetos por defecto
 * @author Juan Pablo Quiroga
 *
 */
public class TransformacionDozer {
    /**
     * Transforma un listado de objetos fuente a destino
     * @param <F>
     * @param <D>
     * @param fuente Objetos fuente
     * @param classDestino Clase de los objetos destino
     * @return Lista de objetos destino con la informacion de la fuente
     */
    public static <F,D> List<D> transformar( List<F> fuente, Class<D> classDestino ) {
        FabricaDozer fabrica = FabricaDozer.getInstancia();
        Mapper  mapper = fabrica.getMapper();
        List<D> destino = new ArrayList<>();
        for ( F dto : fuente) {
            destino.add(mapper.map(dto, classDestino ));      
        }
        return destino;
    }
    
    /**
     * Transforma un objeto fuente a destino
     * @param <F>
     * @param <D>
     * @param fuente Objeto fuente
     * @param classDestino Clase del objeto destino
     * @return Objeto destino con la informacion de la fuente
     */
    public static <F,D> D transformar( F fuente, Class<D> classDestino ) {
        FabricaDozer fabrica = FabricaDozer.getInstancia();
        Mapper  mapper = fabrica.getMapper();

        return mapper.map(fuente, classDestino );
    }
    
    /**
    * Transforma un objeto fuente a destino sobre el objeto destino
     * @param <F>
     * @param <D>
    * @param fuente Objeto fuente
    * @param destino objeto destino
    * @return Objeto destino con la informacion de la fuente
    */
    public static <F,D> D transformar( F fuente, D destino ) {
        FabricaDozer fabrica = FabricaDozer.getInstancia();
        Mapper  mapper = fabrica.getMapper();
        mapper.map(fuente, destino ); 
        return destino;
    }
}
