package mx.edu.utez.u3_04_jggj.service;

import mx.edu.utez.u3_04_jggj.config.ApiResponse;
import mx.edu.utez.u3_04_jggj.controller.almacen.AlmacenDto;
import mx.edu.utez.u3_04_jggj.model.Almacen;
import mx.edu.utez.u3_04_jggj.model.Cede;
import mx.edu.utez.u3_04_jggj.repository.AlmacenRepository;
import mx.edu.utez.u3_04_jggj.repository.CedeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenService {
    private final AlmacenRepository almacenRepository;
    private final CedeRepository cedeRepository;

    public AlmacenService(AlmacenRepository almacenRepository, CedeRepository cedeRepository) {
        this.almacenRepository = almacenRepository;
        this.cedeRepository = cedeRepository;
    }

    public ResponseEntity<ApiResponse<List<Almacen>>> findAll() {
        ApiResponse<List<Almacen>> response = new ApiResponse<>(
                almacenRepository.findAll(),
                "Todos los almacenes registrados",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Almacen>> save(AlmacenDto dto) {
        // Retrieve the related Cede entity
        Optional<Cede> cedeOpt = cedeRepository.findById(dto.getCedeId());
        if (cedeOpt.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cede not found", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }

        Almacen almacen = new Almacen();
        almacen.setFechaDeRegostro(dto.getFechaDeRegostro());
        almacen.setPrecioDeVenta(dto.getPrecioDeVenta());
        almacen.setTamano(dto.getTamano());
        almacen.setCede(cedeOpt.get());

        // First save to generate ID
        Almacen saved = almacenRepository.save(almacen);

        // Generate claveAlmacen and save again
        saved.generateClaveAlmacen();
        Almacen updated = almacenRepository.save(saved);

        ApiResponse<Almacen> response = new ApiResponse<>(
                updated,
                "Almacen saved successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







}
