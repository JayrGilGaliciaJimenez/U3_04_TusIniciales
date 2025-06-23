package mx.edu.utez.u3_04_jggj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "almacenes")
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String claveAlmacen;
    private Date fechaDeRegistro;
    private double precioDeVenta;
    private char tamano;
    @Column(name = "isRented", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isRented = false;
    @Column(name = "isSold", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isSold = false;

    @ManyToOne
    @JoinColumn(name = "cede_id", nullable = false)
    @JsonIgnore
    private Cede cede;

}
