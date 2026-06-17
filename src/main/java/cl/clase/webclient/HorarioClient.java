package cl.clase.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class HorarioClient {

    private final WebClient webClient;

    public HorarioClient(@Value("${horario-service.url}") String horarioServidor){
        this.webClient = WebClient.builder().baseUrl(horarioServidor).build();
    }

    public Map<String, Object> obtenerHorarioId(Long id){

        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Horario no encontrado")))
                .bodyToMono(Map.class).block();

    }
}