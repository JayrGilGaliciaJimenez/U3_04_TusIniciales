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
    private Date fechaDeRegostro;
    private double precioDeVenta;
    private char tamano;

    @ManyToOne
    @JoinColumn(name = "cede_id", nullable = false)
    @JsonIgnore
    private Cede cede;

    public void generateClaveAlmacen() {
        if (this.cede != null && this.cede.getClaveCede() != null && this.id != null) {
            this.claveAlmacen = String.format("(%s-A%d)", this.cede.getClaveCede(), this.id);
        }
    }


}
