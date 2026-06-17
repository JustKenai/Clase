package cl.clase.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class EntrenadorClient {

    private WebClient webClient;

    public EntrenadorClient(@Value("${entrenador-service.url}") String entrenadorServidor){
        this.webClient = WebClient.builder().baseUrl(entrenadorServidor).build();
    }

    public Map<String, Object> obtenerEntrenadorId(Long id){

        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Entrenador no encontrado")))
                .bodyToMono(Map.class).block();

    }
}