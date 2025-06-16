package mx.edu.utez.u3_04_jggj.controller.cede;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link mx.edu.utez.u3_04_jggj.model.Cede}
 */
@Value
public class CedeDto implements Serializable {
    String claveCede;
    String estado;
    String municipio;
}