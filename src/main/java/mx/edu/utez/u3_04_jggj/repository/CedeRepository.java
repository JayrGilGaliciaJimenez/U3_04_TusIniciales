package mx.edu.utez.u3_04_jggj.repository;

import mx.edu.utez.u3_04_jggj.model.Cede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CedeRepository extends JpaRepository<Cede, Integer> {

    Optional<Cede> findByClaveCede(String claveCede);
}
