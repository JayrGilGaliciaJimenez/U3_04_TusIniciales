package mx.edu.utez.u3_04_jggj.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cedes")
public class Cede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String claveCede;
    private String estado;
    private String municipio;

    @PrePersist
    public void generateClaveCede() {
        String date = new java.text.SimpleDateFormat("ddMMyyyy").format(new java.util.Date());
        int randomDigits = (int)(Math.random() * 9000) + 1000; // ensures 4 digits
        this.claveCede = String.format("C%d-%s-%04d", this.id != null ? this.id : 0, date, randomDigits);
    }




}
