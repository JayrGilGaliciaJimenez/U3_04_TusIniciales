package mx.edu.utez.u3_04_jggj.controller.almacen;

import mx.edu.utez.u3_04_jggj.config.ApiResponse;
import mx.edu.utez.u3_04_jggj.model.Almacen;
import mx.edu.utez.u3_04_jggj.service.AlmacenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacen")
@CrossOrigin(origins = "*")
public class AlmacenController {

    private final AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Almacen>>> getAll() {
        return almacenService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Almacen>> getById(@PathVariable Integer id) {
        return almacenService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Almacen>> save(@RequestBody AlmacenDto dto) {
        return almacenService.save(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Almacen>> update(@PathVariable Integer id, @RequestBody AlmacenDto dto) {
        return almacenService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Integer id) {
        return almacenService.deleteById(id);
    }

}
