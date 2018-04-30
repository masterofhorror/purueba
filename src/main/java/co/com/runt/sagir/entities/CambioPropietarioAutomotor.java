package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@Entity
@SqlResultSetMappings ({
    @SqlResultSetMapping(name = "estadoAutomotor", entities = {
        @EntityResult (entityClass = CambioPropietarioAutomotor.class, fields = {
            @FieldResult (name = "nroVehiculo", column = "NROVEHICULO"),
            @FieldResult (name = "placa", column = "PLACA"),
            @FieldResult (name = "estadoVehic", column = "ESTADOVEHIC"),
        })
    })
})
public class CambioPropietarioAutomotor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String nroVehiculo; 
    private String placa;
    private String estadoVehic;

    public String getNroVehiculo() {
        return nroVehiculo;
    }

    public void setNroVehiculo(String nroVehiculo) {
        this.nroVehiculo = nroVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }
    
    
    
}
