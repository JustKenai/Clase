package cl.clase.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseRequestDTO {

    @NotBlank(message = "La clase debe tener un nombre")
    private String nombreClase;

    @NotNull(message = "Todas las clases deben tener un horario")
    private Long horarioId;

    @NotNull(message = "Una clase no puede estar sin entrenador")
    private Long entrenadorId;
}
