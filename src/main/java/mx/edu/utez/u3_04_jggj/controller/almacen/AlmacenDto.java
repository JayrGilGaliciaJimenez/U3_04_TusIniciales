package mx.edu.utez.u3_04_jggj.controller.almacen;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link mx.edu.utez.u3_04_jggj.model.Almacen}
 */
@Value
public class AlmacenDto implements Serializable {
    String claveAlmacen;
    Date fechaDeRegostro;
    double precioDeVenta;
    char tamano;
    Integer cedeId;
}