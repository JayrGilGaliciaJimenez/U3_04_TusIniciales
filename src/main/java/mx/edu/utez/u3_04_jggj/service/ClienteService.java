package mx.edu.utez.u3_04_jggj.service;

import mx.edu.utez.u3_04_jggj.config.ApiResponse;
import mx.edu.utez.u3_04_jggj.controller.cliente.ClienteDto;
import mx.edu.utez.u3_04_jggj.model.Cliente;
import mx.edu.utez.u3_04_jggj.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity<ApiResponse<Cliente>> save(ClienteDto dto) {
        if (dto.getNombreCompleto() == null || dto.getNombreCompleto().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El nombre completo no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getNumeroDeTelefono() == null || !dto.getNumeroDeTelefono().matches("\\d{10}")) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El número de teléfono debe ser de 10 dígitos", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getCorreoElectronico() == null || !dto.getCorreoElectronico().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El correo electrónico no es válido", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(dto.getNombreCompleto().trim());
        cliente.setNumeroDeTelefono(dto.getNumeroDeTelefono().trim());
        cliente.setCorreoElectronico(dto.getCorreoElectronico().trim());
        Cliente savedCliente = clienteRepository.save(cliente);

        ApiResponse<Cliente> response = new ApiResponse<>(
                savedCliente,
                "Cliente guardado",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<Cliente>>> findAll() {
        ApiResponse<List<Cliente>> response = new ApiResponse<>(
                clienteRepository.findAll(),
                "Todos los clientes registrados",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<Cliente>> findById(Integer id) {
        return clienteRepository.findById(id)
                .map(cliente -> new ResponseEntity<>(
                        new ApiResponse<>(cliente, "Cliente encontrado", HttpStatus.OK), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        new ApiResponse<>(null, "Cliente no encontrado", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<ApiResponse<Void>> deleteById(Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El ID proporcionado no es válido", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cliente eliminado", HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "Cliente no encontrado", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ApiResponse<Cliente>> update(Integer id, ClienteDto dto) {
        if (dto.getNombreCompleto() == null || dto.getNombreCompleto().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El nombre completo no puede estar vacío", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getNumeroDeTelefono() == null || !dto.getNumeroDeTelefono().matches("\\d{10}")) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El número de teléfono debe ser de 10 dígitos", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getCorreoElectronico() == null || !dto.getCorreoElectronico().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return new ResponseEntity<>(
                    new ApiResponse<>(null, "El correo electrónico no es válido", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST
            );
        }

        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombreCompleto(dto.getNombreCompleto().trim());
                    cliente.setNumeroDeTelefono(dto.getNumeroDeTelefono().trim());
                    cliente.setCorreoElectronico(dto.getCorreoElectronico().trim());
                    Cliente updated = clienteRepository.save(cliente);
                    return new ResponseEntity<>(
                            new ApiResponse<>(updated, "Cliente actualizado", HttpStatus.OK), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(
                        new ApiResponse<>(null, "Cliente no encontrado", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND));
    }

}
