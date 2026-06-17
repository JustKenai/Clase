package cl.clase.controller;

import cl.clase.dto.request.ClaseRequestDTO;
import cl.clase.dto.response.ClaseResponseDTO;
import cl.clase.service.ClaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clase")
@RequiredArgsConstructor
@Tag(name = "Clases", description = "Operaciones relacionadas con las clases")
public class ClaseController {

    @Autowired
    private final ClaseService claseService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar clase", description = "Obtiene clase por su Id.")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        return ResponseEntity.ok(claseService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Busca todas las clases", description = "Obtiene toda una lista de clases.")
    public ResponseEntity<?> buscarTodo(){
        return ResponseEntity.ok(claseService.buscarTodo());
    }

    @PostMapping
    @Operation(summary = "Agregar una clase", description = "Agrega una clase.")
    public ResponseEntity<?> agregar(@Valid @RequestBody ClaseRequestDTO dto) {
        return ResponseEntity.status(201).body(claseService.guardar(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar clase", description = "Elimina una clase por su Id.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (claseService.buscarPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        claseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza clase", description = "Obtiene actualizar clase por Id.")
    public ResponseEntity<ClaseResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClaseRequestDTO dto) {
        return claseService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/entrenador/{id}")
    @Operation(summary = "Buscar clase por id entrenador", description = "Obtiene buscar clase especifica por el Id de entrenador.")
    public ResponseEntity<?> buscarPorIDEntrenador(@PathVariable Long id){
        return ResponseEntity.ok(claseService.buscarPorEntrenador(id));
    }

    @GetMapping("/buscar/horario/{id}")
    @Operation(summary = "Buscar clase por id horario", description = "Busca una clase especifica por el ID de horario." )
    public ResponseEntity<?> buscarPorIdHorario(@PathVariable Long id) {
        return ResponseEntity.ok(claseService.buscarPorHorario(id));
    }
}
