package mx.edu.utez.u3_04_jggj.controller.cliente;

import mx.edu.utez.u3_04_jggj.config.ApiResponse;
import mx.edu.utez.u3_04_jggj.model.Cliente;
import mx.edu.utez.u3_04_jggj.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Cliente>> save (@RequestBody ClienteDto dto){
        return clienteService.save(dto);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Cliente>>> getAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> getById(@PathVariable Integer id) {
        return clienteService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Integer id) {
        return clienteService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> update(
            @PathVariable Integer id,
            @RequestBody ClienteDto dto) {
        return clienteService.update(id, dto);
    }





}
