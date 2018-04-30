/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.utils;

/**
 *
 * @author Hmoreno
 */
public enum TipoArchivoCargue {

    PROPIETARIO(1),
    TRAMITE(2),
    VEHICULO(3),
    MEDIDAS_CAUTELARES(12),
    RADICADO(15),
    MEDIDAS_CAUTELARES_ADICIONAL(112),
    PRENDAS(16),
    RADICADO_MT(19);

    private final Integer codigo;

    private TipoArchivoCargue(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

}
