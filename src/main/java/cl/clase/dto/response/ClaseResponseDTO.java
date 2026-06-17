package cl.clase.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseResponseDTO {
    private Long id;
    private String nombreClase;
    private String horarioNombre;
    private String entrenadorNombre;
}
