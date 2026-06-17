CREATE TABLE IF NOT EXISTS clase (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  entrenador_id bigint(20) NOT NULL,
  horario_id bigint(20) NOT NULL
);

INSERT INTO clase (id, nombre, entrenador_id, horario_id) VALUES
(1, 'Clase de Yoga', 1, 1),
(2, 'Clase de Brazo', 2, 2),
(3, 'Clase de Fuerza y Acondicionamiento', 3, 3);