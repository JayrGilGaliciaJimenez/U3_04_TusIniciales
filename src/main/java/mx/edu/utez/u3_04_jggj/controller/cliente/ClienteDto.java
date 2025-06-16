package mx.edu.utez.u3_04_jggj.controller.cliente;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link mx.edu.utez.u3_04_jggj.model.Cliente}
 */
@Value
public class ClienteDto implements Serializable {
    String nombreCompleto;
    String numeroDeTelefono;
    String correoElectronico;
}