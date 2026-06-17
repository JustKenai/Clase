package cl.clase.ClaseTestMock;

import cl.clase.dto.request.ClaseRequestDTO;
import cl.clase.dto.response.ClaseResponseDTO;
import cl.clase.model.Clase;

public class ClaseServiceMock {

    public static Clase crearClase(Long id, String nombre, Long horarioId, Long entrenadorId) {
        return new Clase(id, nombre, horarioId, entrenadorId);
    }

    public static ClaseRequestDTO crearClaseRequest(String nombre, Long horarioId, Long entrenadorId) {
        return new ClaseRequestDTO(nombre, horarioId, entrenadorId);
    }

    public static ClaseResponseDTO crearClaseResponse(Long id, String nombre, String horarioNombre, String entrenadorNombre) {
        return new ClaseResponseDTO(id, nombre, horarioNombre, entrenadorNombre);
    }
}
