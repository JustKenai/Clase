package cl.clase.ClaseTestMock;
import cl.clase.dto.request.ClaseRequestDTO;
import cl.clase.dto.response.ClaseResponseDTO;
import cl.clase.model.Clase;
import cl.clase.repository.ClaseRepository;
import cl.clase.service.ClaseService;
import cl.clase.webclient.EntrenadorClient;
import cl.clase.webclient.HorarioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ClaseServiceTest {
    @Mock
    private ClaseRepository claseRepository;

    @Mock
    private EntrenadorClient entrenadorClient;

    @Mock
    private HorarioClient horarioClient;

    @InjectMocks
    private ClaseService claseService;

    @Test
    void deberiaGuardarClase() {
        ClaseRequestDTO dto = ClaseServiceMock.crearClaseRequest("Clase Yoga", 1L, 1L);

        Map<String, Object> entrenador = Map.of("nombres", "Juan", "apellidos", "Pérez");
        Map<String, Object> horario = Map.of("nombreHorario", "Horario Mañana");

        when(entrenadorClient.obtenerEntrenadorId(1L)).thenReturn(entrenador);
        when(horarioClient.obtenerHorarioId(1L)).thenReturn(horario);

        Clase claseGuardada = ClaseServiceMock.crearClase(1L, dto.getNombreClase(), dto.getHorarioId(), dto.getEntrenadorId());
        when(claseRepository.save(any(Clase.class))).thenReturn(claseGuardada);

        ClaseResponseDTO resultado = claseService.guardar(dto);

        assertEquals("Clase Yoga", resultado.getNombreClase());
        assertEquals("Horario Mañana", resultado.getHorarioNombre());
        assertEquals("Juan Pérez", resultado.getEntrenadorNombre());
        verify(claseRepository).save(any(Clase.class));
    }

    @Test
    void deberiaActualizarClase() {
        Clase existente = ClaseServiceMock.crearClase(1L, "Clase Spinning", 1L, 1L);
        ClaseRequestDTO dto = ClaseServiceMock.crearClaseRequest("Clase Crossfit", 2L, 2L);

        Map<String, Object> entrenador = Map.of("nombres", "Ana", "apellidos", "Gómez");
        Map<String, Object> horario = Map.of("nombreHorario", "Horario Tarde");

        when(claseRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(entrenadorClient.obtenerEntrenadorId(2L)).thenReturn(entrenador);
        when(horarioClient.obtenerHorarioId(2L)).thenReturn(horario);
        when(claseRepository.save(any(Clase.class))).thenReturn(existente);

        Optional<ClaseResponseDTO> resultado = claseService.actualizar(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Clase Crossfit", resultado.get().getNombreClase());
        assertEquals("Horario Tarde", resultado.get().getHorarioNombre());
        assertEquals("Ana Gómez", resultado.get().getEntrenadorNombre());
        verify(claseRepository).findById(1L);
        verify(claseRepository).save(any(Clase.class));
    }

    @Test
    void deberiaEliminarClase() {
        claseService.eliminar(1L);
        verify(claseRepository).deleteById(1L);
    }

    @Test
    void deberiaBuscarTodasLasClases() {
        Clase c1 = ClaseServiceMock.crearClase(1L, "Clase Yoga", 1L, 1L);
        Clase c2 = ClaseServiceMock.crearClase(2L, "Clase Crossfit", 2L, 2L);

        Map<String, Object> entrenador = Map.of("nombres", "Pedro", "apellidos", "López");
        Map<String, Object> horario = Map.of("nombreHorario", "Horario Mañana");

        when(claseRepository.findAll()).thenReturn(List.of(c1, c2));
        when(entrenadorClient.obtenerEntrenadorId(any(Long.class))).thenReturn(entrenador);
        when(horarioClient.obtenerHorarioId(any(Long.class))).thenReturn(horario);

        List<ClaseResponseDTO> resultado = claseService.buscarTodo();

        assertEquals(2, resultado.size());
        assertEquals("Clase Yoga", resultado.get(0).getNombreClase());
        verify(claseRepository).findAll();
    }

    @Test
    void deberiaBuscarClasePorId() {
        Clase clase = ClaseServiceMock.crearClase(1L, "Clase Spinning", 1L, 1L);

        Map<String, Object> entrenador = Map.of("nombres", "Carlos", "apellidos", "Ramírez");
        Map<String, Object> horario = Map.of("nombreHorario", "Horario Noche");

        when(claseRepository.findById(1L)).thenReturn(Optional.of(clase));
        when(entrenadorClient.obtenerEntrenadorId(1L)).thenReturn(entrenador);
        when(horarioClient.obtenerHorarioId(1L)).thenReturn(horario);

        Optional<ClaseResponseDTO> resultado = claseService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Clase Spinning", resultado.get().getNombreClase());
        assertEquals("Horario Noche", resultado.get().getHorarioNombre());
        assertEquals("Carlos Ramírez", resultado.get().getEntrenadorNombre());
        verify(claseRepository).findById(1L);
    }
}
