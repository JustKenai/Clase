package cl.clase.service;

import cl.clase.dto.request.ClaseRequestDTO;
import cl.clase.dto.response.ClaseResponseDTO;
import cl.clase.model.Clase;
import cl.clase.repository.ClaseRepository;
import cl.clase.webclient.EntrenadorClient;
import cl.clase.webclient.HorarioClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaseService {

    private final ClaseRepository claseRepository;
    private final EntrenadorClient entrenadorClient;
    private final HorarioClient horarioClient;



    private ClaseResponseDTO mapToDTO(Clase clase){
        Map<String, Object> entrenador = entrenadorClient.obtenerEntrenadorId(clase.getEntrenadorId());
        Map<String, Object> horario = horarioClient.obtenerHorarioId(clase.getHorarioId());

        String horarioNombre = (String) horario.get("nombreHorario");
        String entrenadorNombre = (String) entrenador.get("nombres") + " " + entrenador.get("apellidos");


        return new ClaseResponseDTO(
                clase.getId(),
                clase.getNombre(),
                horarioNombre,
                entrenadorNombre
        );

    }

    public void eliminar(Long id){
        claseRepository.deleteById(id);
    }


    public List<ClaseResponseDTO> buscarTodo(){
        return claseRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ClaseResponseDTO> buscarPorId(Long id){
        return claseRepository.findById(id).map(this::mapToDTO);
    }

    public ClaseResponseDTO guardar(ClaseRequestDTO dto){
        Map<String, Object> entrenador = entrenadorClient.obtenerEntrenadorId(dto.getEntrenadorId());
        Map<String, Object> horario = horarioClient.obtenerHorarioId(dto.getHorarioId());

        if (entrenador.isEmpty()){
            throw new RuntimeException("Entrenador no encontrado");
        }

        if (horario.isEmpty()){
            throw new RuntimeException("Horario no encontrado");
        }

        Clase clase = new Clase(
                null,
                dto.getNombreClase(),
                dto.getHorarioId(),
                dto.getEntrenadorId()
        );
        return mapToDTO(claseRepository.save(clase));
    }

    public Optional<ClaseResponseDTO> actualizar(Long id, ClaseRequestDTO dto) {
        return claseRepository.findById(id).map(existente -> {
            Map<String, Object> entrenador = entrenadorClient.obtenerEntrenadorId(dto.getEntrenadorId());
            Map<String, Object> horario = horarioClient.obtenerHorarioId(dto.getHorarioId());

            if (entrenador.isEmpty()){
                throw new RuntimeException("Entrenador no encontrado");
            }
            if (horario.isEmpty()){
                throw new RuntimeException("Horario no encontrado");
            }
            existente.setNombre(dto.getNombreClase());
            existente.setHorarioId(dto.getHorarioId());
            existente.setEntrenadorId(dto.getEntrenadorId());
            return mapToDTO(claseRepository.save(existente));
        });
    }

    public List<Clase> buscarPorEntrenador(Long id){
        return claseRepository.findByEntrenadorId(id);
    }

    public List<Clase> buscarPorHorario(Long id){
        return claseRepository.findByHorarioId(id);
    }
}
