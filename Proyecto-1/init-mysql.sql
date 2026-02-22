-- ============================================
-- SCRIPT DE INICIALIZACION MYSQL
-- Compatible con JPA Hibernate
-- Se ejecuta automaticamente al crear el contenedor
-- ============================================

USE quiz_db;

-- ============================================
-- 1. CREAR TABLAS (Necesario para el arranque en Docker)
-- ============================================
CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pregunta (
    dtype VARCHAR(31) NOT NULL,
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto_pregunta VARCHAR(255) NOT NULL,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE IF NOT EXISTS pregunta_verdadero_falso (
    id BIGINT PRIMARY KEY,
    respuesta_correcta TINYINT(1) NOT NULL,
    FOREIGN KEY (id) REFERENCES pregunta(id)
);

CREATE TABLE IF NOT EXISTS pregunta_seleccion_unica (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES pregunta(id)
);

CREATE TABLE IF NOT EXISTS pregunta_seleccion_multiple (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES pregunta(id)
);

CREATE TABLE IF NOT EXISTS opcion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto_opcion VARCHAR(255) NOT NULL,
    es_correcta TINYINT(1) NOT NULL,
    pregunta_id BIGINT,
    FOREIGN KEY (pregunta_id) REFERENCES pregunta(id)
);

-- ============================================
-- 2. INSERTAR CATEGORIAS
-- ============================================
INSERT INTO categoria (nombre) VALUES ('Programacion');
INSERT INTO categoria (nombre) VALUES ('Deporte');
INSERT INTO categoria (nombre) VALUES ('Java');
INSERT INTO categoria (nombre) VALUES ('Bases de Datos');
INSERT INTO categoria (nombre) VALUES ('Futbol');
INSERT INTO categoria (nombre) VALUES ('Baloncesto');

-- ============================================
-- 3. INSERTAR PREGUNTAS (Con el DTYPE correcto)
-- ============================================

-- VERDADERO/FALSO (15 preguntas)
INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'Java es un lenguaje de programacion orientado a objetos', 1);
SET @id1 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id1, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'Python fue creado por Guido van Rossum', 1);
SET @id2 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id2, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'HTML es un lenguaje de programacion', 2);
SET @id3 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id3, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'JavaScript y Java son el mismo lenguaje', 2);
SET @id4 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id4, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'Java utiliza recoleccion de basura automatica', 3);
SET @id5 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id5, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'SQL significa Standard Query Language', 4);
SET @id6 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id6, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'Una base de datos relacional organiza datos en tablas', 4);
SET @id7 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id7, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'MySQL es un sistema gestor de bases de datos', 4);
SET @id8 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id8, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'El Real Madrid ha ganado mas Champions League que cualquier otro equipo', 5);
SET @id9 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id9, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'Lionel Messi ha jugado en el FC Barcelona', 5);
SET @id10 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id10, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'En futbol se puede usar las manos', 5);
SET @id11 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id11, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'El futbol se juega con 5 jugadores por equipo', 5);
SET @id12 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id12, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'El Baloncesto se juega con 11 jugadores por equipo', 6);
SET @id13 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id13, 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'En baloncesto se puede botar el balon', 6);
SET @id14 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id14, 1);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaVerdaderoFalso', 'La NBA es una liga de baloncesto', 6);
SET @id15 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id15, 1);

-- SELECCION UNICA (15 preguntas con opciones)
INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que es una variable en programacion?', 1);
SET @su1 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su1);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su1, 'Un espacio en memoria para almacenar datos', 1),
    (@su1, 'Una funcion', 0),
    (@su1, 'Un bucle', 0),
    (@su1, 'Un comentario', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'En que lenguaje esta escrito el sistema operativo Linux?', 1);
SET @su2 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su2);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su2, 'C', 1),
    (@su2, 'Java', 0),
    (@su2, 'Python', 0),
    (@su2, 'JavaScript', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que es HTML?', 1);
SET @su3 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su3);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su3, 'Lenguaje de marcado de hipertexto', 1),
    (@su3, 'Un lenguaje de programacion', 0),
    (@su3, 'Una base de datos', 0),
    (@su3, 'Un sistema operativo', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Cual es el creador de Java?', 3);
SET @su4 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su4);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su4, 'James Gosling', 1),
    (@su4, 'Linus Torvalds', 0),
    (@su4, 'Guido van Rossum', 0),
    (@su4, 'Dennis Ritchie', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que significa JVM?', 3);
SET @su5 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su5);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su5, 'Java Virtual Machine', 1),
    (@su5, 'Java Variable Method', 0),
    (@su5, 'Java Version Manager', 0),
    (@su5, 'Java Visual Model', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que es SQL?', 4);
SET @su6 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su6);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su6, 'Structured Query Language', 1),
    (@su6, 'Simple Question Language', 0),
    (@su6, 'Standard Query Logic', 0),
    (@su6, 'System Quality Language', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Cual es un sistema gestor de bases de datos NoSQL?', 4);
SET @su7 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su7);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su7, 'MongoDB', 1),
    (@su7, 'MySQL', 0),
    (@su7, 'PostgreSQL', 0),
    (@su7, 'Oracle', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que tipo de relacion es cuando un registro de una tabla se relaciona con varios de otra?', 4);
SET @su8 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su8);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su8, 'Uno a muchos', 1),
    (@su8, 'Uno a uno', 0),
    (@su8, 'Muchos a muchos', 0),
    (@su8, 'Ninguna de las anteriores', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Cuantos jugadores hay en un equipo de futbol en el campo?', 5);
SET @su9 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su9);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su9, '11 jugadores', 1),
    (@su9, '10 jugadores', 0),
    (@su9, '9 jugadores', 0),
    (@su9, '12 jugadores', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Quien gano el mundial de futbol 2022?', 5);
SET @su10 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su10);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su10, 'Argentina', 1),
    (@su10, 'Francia', 0),
    (@su10, 'Brasil', 0),
    (@su10, 'Alemania', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'En que club juega actualmente Cristiano Ronaldo?', 5);
SET @su11 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su11);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su11, 'Al-Nassr', 1),
    (@su11, 'Real Madrid', 0),
    (@su11, 'Manchester United', 0),
    (@su11, 'Juventus', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Cuantos jugadores hay en un equipo de baloncesto en la cancha?', 6);
SET @su12 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su12);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su12, '5 jugadores', 1),
    (@su12, '6 jugadores', 0),
    (@su12, '7 jugadores', 0),
    (@su12, '4 jugadores', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Quien es considerado el mejor jugador de baloncesto de todos los tiempos?', 6);
SET @su13 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su13);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su13, 'Michael Jordan', 1),
    (@su13, 'LeBron James', 0),
    (@su13, 'Kobe Bryant', 0),
    (@su13, 'Magic Johnson', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Cuantos puntos vale una canasta desde fuera de la linea de tres puntos?', 6);
SET @su14 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su14);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su14, '3 puntos', 1),
    (@su14, '2 puntos', 0),
    (@su14, '4 puntos', 0),
    (@su14, '1 punto', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionUnica', 'Que equipo de la NBA tiene mas campeonatos?', 6);
SET @su15 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su15);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su15, 'Boston Celtics', 1),
    (@su15, 'Los Angeles Lakers', 0),
    (@su15, 'Chicago Bulls', 0),
    (@su15, 'Golden State Warriors', 0);

-- SELECCION MULTIPLE (10 preguntas)
INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales de los siguientes son lenguajes de programacion?', 1);
SET @sm1 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm1);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm1, 'Java', 1),
    (@sm1, 'Python', 1),
    (@sm1, 'HTML', 0),
    (@sm1, 'CSS', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales son tipos de datos primitivos en Java?', 3);
SET @sm2 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm2);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm2, 'int', 1),
    (@sm2, 'boolean', 1),
    (@sm2, 'String', 0),
    (@sm2, 'Array', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales son frameworks de Java?', 3);
SET @sm3 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm3);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm3, 'Spring', 1),
    (@sm3, 'Hibernate', 1),
    (@sm3, 'React', 0),
    (@sm3, 'Angular', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales son comandos SQL?', 4);
SET @sm4 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm4);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm4, 'SELECT', 1),
    (@sm4, 'INSERT', 1),
    (@sm4, 'PRINT', 0),
    (@sm4, 'LOOP', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales son bases de datos relacionales?', 4);
SET @sm5 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm5);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm5, 'MySQL', 1),
    (@sm5, 'PostgreSQL', 1),
    (@sm5, 'MongoDB', 0),
    (@sm5, 'Redis', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales jugadores han ganado el Balon de Oro?', 5);
SET @sm6 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm6);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm6, 'Lionel Messi', 1),
    (@sm6, 'Cristiano Ronaldo', 1),
    (@sm6, 'Neymar', 0),
    (@sm6, 'Sergio Ramos', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales equipos han ganado la Champions League mas de 10 veces?', 5);
SET @sm7 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm7);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm7, 'Real Madrid', 1),
    (@sm7, 'AC Milan', 1),
    (@sm7, 'Barcelona', 0),
    (@sm7, 'Manchester United', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales son ligas importantes de futbol europeo?', 5);
SET @sm8 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm8);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm8, 'Premier League', 1),
    (@sm8, 'La Liga', 1),
    (@sm8, 'MLS', 0),
    (@sm8, 'Liga MX', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales jugadores son leyendas de la NBA?', 6);
SET @sm9 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm9);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm9, 'Michael Jordan', 1),
    (@sm9, 'Magic Johnson', 1),
    (@sm9, 'Cristiano Ronaldo', 0),
    (@sm9, 'Lionel Messi', 0);

INSERT INTO pregunta (dtype, texto_pregunta, categoria_id) VALUES ('PreguntaSeleccionMultiple', 'Cuales equipos pertenecen a la NBA?', 6);
SET @sm10 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm10);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm10, 'Los Angeles Lakers', 1),
    (@sm10, 'Boston Celtics', 1),
    (@sm10, 'Real Madrid', 0),
    (@sm10, 'FC Barcelona', 0);