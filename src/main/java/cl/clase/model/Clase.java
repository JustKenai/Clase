package cl.clase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Clase")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "horario_id", nullable = true)
    private Long horarioId;

    @Column(name = "entrenador_id", nullable = true)
    private Long entrenadorId;

}
