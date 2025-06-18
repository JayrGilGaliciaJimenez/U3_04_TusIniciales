package mx.edu.utez.u3_04_jggj.service;

import mx.edu.utez.u3_04_jggj.config.ApiResponse;
import mx.edu.utez.u3_04_jggj.controller.cede.CedeDto;
import mx.edu.utez.u3_04_jggj.model.Cede;
import mx.edu.utez.u3_04_jggj.repository.CedeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CedeService {
    private final CedeRepository cedeRepository;

    public CedeService(CedeRepository cedeRepository) {
        this.cedeRepository = cedeRepository;
    }

    public ResponseEntity<ApiResponse<List<Cede>>> findAll() {
        List<Cede> cedes = cedeRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(cedes, "Cedes retrieved successfully", HttpStatus.OK),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ApiResponse<Cede>> findById(Integer id) {
        Optional<Cede> cedeOpt = cedeRepository.findById(id);
        if (cedeOpt.isPresent()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(cedeOpt.get(), "Cede found", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cede not found", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public ResponseEntity<ApiResponse<Cede>> save(CedeDto dto) {
        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El estado no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getMunicipio() == null || dto.getMunicipio().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El municipio no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

        Cede cede = new Cede();
        cede.setEstado(dto.getEstado().trim());
        cede.setMunicipio(dto.getMunicipio().trim());
        Cede saved = cedeRepository.save(cede);

        String claveCede = String.format("C%d-%s-%04d",
                saved.getId(),
                new java.text.SimpleDateFormat("ddMMyyyy").format(new java.util.Date()),
                new java.util.Random().nextInt(10000)
        );
        saved.setClaveCede(claveCede);

        Cede updated = cedeRepository.save(saved);

        return new ResponseEntity<>(
                new ApiResponse<>(updated, "Cede saved successfully", HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<ApiResponse<Cede>> update(Integer id, CedeDto dto) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El ID proporcionado no es válido", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El estado no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getMunicipio() == null || dto.getMunicipio().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El municipio no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<Cede> cedeOpt = cedeRepository.findById(id);
        if (cedeOpt.isPresent()) {
            Cede cede = cedeOpt.get();
            cede.setMunicipio(dto.getMunicipio().trim());
            cede.setEstado(dto.getEstado().trim());
            Cede updated = cedeRepository.save(cede);
            return new ResponseEntity<>(
                    new ApiResponse<>(updated, "Cede updated successfully", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cede not found", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public ResponseEntity<ApiResponse<Void>> deleteById(Integer id) {
        if (cedeRepository.existsById(id)) {
            cedeRepository.deleteById(id);
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cede deleted successfully", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cede not found", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}