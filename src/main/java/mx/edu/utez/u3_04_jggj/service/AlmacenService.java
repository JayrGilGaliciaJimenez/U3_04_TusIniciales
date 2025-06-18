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
        if (dto.getFechaDeRegostro() == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "La fecha de registro no puede estar vacía", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getPrecioDeVenta() <= 0) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El precio de venta debe ser mayor a 0", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

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
        if (dto.getTamano() == '\0') { // Check if the char is uninitialized
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El tamaño no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        almacen.setTamano(dto.getTamano());
        Almacen saved = almacenRepository.save(almacen);
        saved.generateClaveAlmacen();
        Almacen updated = almacenRepository.save(saved);

        ApiResponse<Almacen> response = new ApiResponse<>(
                updated,
                "Almacen saved successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Almacen>> findById(Integer id) {
        Optional<Almacen> almacenOpt = almacenRepository.findById(id);
        if (almacenOpt.isPresent()) {
            ApiResponse<Almacen> response = new ApiResponse<>(
                    almacenOpt.get(),
                    "Almacen found",
                    HttpStatus.OK
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Almacen> response = new ApiResponse<>(
                    null,
                    "Almacen not found",
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ApiResponse<Almacen>> update(Integer id, AlmacenDto dto) {
        Optional<Almacen> almacenOpt = almacenRepository.findById(id);
        if (almacenOpt.isPresent()) {
            Almacen almacen = almacenOpt.get();
            Optional<Cede> cedeOpt = cedeRepository.findById(dto.getCedeId());
            if (cedeOpt.isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse<>(null, "Cede not found", HttpStatus.NOT_FOUND),
                        HttpStatus.NOT_FOUND
                );
            }

            almacen.setFechaDeRegostro(dto.getFechaDeRegostro());
            almacen.setPrecioDeVenta(dto.getPrecioDeVenta());
            almacen.setTamano(dto.getTamano());
            almacen.setCede(cedeOpt.get());
            Almacen updated = almacenRepository.save(almacen);

            ApiResponse<Almacen> response = new ApiResponse<>(
                    updated,
                    "Almacen updated successfully",
                    HttpStatus.OK
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Almacen> response = new ApiResponse<>(
                    null,
                    "Almacen not found",
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ApiResponse<Void>> deleteById(Integer id) {
        if (almacenRepository.existsById(id)) {
            almacenRepository.deleteById(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    "Almacen deleted successfully",
                    HttpStatus.OK
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    "Almacen not found",
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
