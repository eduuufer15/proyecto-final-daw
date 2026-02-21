-- ============================================
-- SCRIPT DE INICIALIZACIÓN MYSQL
-- Se ejecuta automáticamente al crear el contenedor
-- ============================================

USE quiz_db;

-- ============================================
-- INSERTAR CATEGORÍAS
-- ============================================
INSERT INTO categoria (nombre) VALUES ('Programación');
INSERT INTO categoria (nombre) VALUES ('Deporte');
INSERT INTO categoria (nombre) VALUES ('Java');
INSERT INTO categoria (nombre) VALUES ('Bases de Datos');
INSERT INTO categoria (nombre) VALUES ('Fútbol');
INSERT INTO categoria (nombre) VALUES ('Baloncesto');

-- ============================================
-- INSERTAR PREGUNTAS (40 preguntas con opciones)
-- ============================================

-- VERDADERO/FALSO (15 preguntas)
INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('Java es un lenguaje de programación orientado a objetos', 2);
SET @id1 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id1, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('Python fue creado por Guido van Rossum', 2);
SET @id2 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id2, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('HTML es un lenguaje de programación', 2);
SET @id3 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id3, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('JavaScript y Java son el mismo lenguaje', 2);
SET @id4 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id4, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('Java utiliza recolección de basura automática', 4);
SET @id5 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id5, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('SQL significa Standard Query Language', 5);
SET @id6 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id6, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('Una base de datos relacional organiza datos en tablas', 5);
SET @id7 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id7, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('MySQL es un sistema gestor de bases de datos', 5);
SET @id8 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id8, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('El Real Madrid ha ganado más Champions League que cualquier otro equipo', 6);
SET @id9 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id9, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('Lionel Messi ha jugado en el FC Barcelona', 6);
SET @id10 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id10, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('En fútbol se puede usar las manos', 6);
SET @id11 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id11, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('El fútbol se juega con 5 jugadores por equipo', 6);
SET @id12 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id12, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('El Baloncesto se juega con 11 jugadores por equipo', 7);
SET @id13 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id13, false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('En baloncesto se puede botar el balón', 7);
SET @id14 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id14, true);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('La NBA es una liga de baloncesto', 7);
SET @id15 = LAST_INSERT_ID();
INSERT INTO pregunta_verdadero_falso (id, respuesta_correcta) VALUES (@id15, true);

-- SELECCIÓN ÚNICA (15 preguntas con opciones)
INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué es una variable en programación?', 2);
SET @su1 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su1);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su1, 'Un espacio en memoria para almacenar datos', true),
    (@su1, 'Una función', false),
    (@su1, 'Un bucle', false),
    (@su1, 'Un comentario', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿En qué lenguaje está escrito el sistema operativo Linux?', 2);
SET @su2 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su2);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su2, 'C', true),
    (@su2, 'Java', false),
    (@su2, 'Python', false),
    (@su2, 'JavaScript', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuál es un lenguaje de backend?', 2);
SET @su3 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su3);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su3, 'Node.js', true),
    (@su3, 'HTML', false),
    (@su3, 'CSS', false),
    (@su3, 'Photoshop', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué método se usa para imprimir en Java?', 4);
SET @su4 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su4);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su4, 'System.out.println()', true),
    (@su4, 'console.log()', false),
    (@su4, 'print()', false),
    (@su4, 'echo()', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué es un IDE en programación?', 4);
SET @su5 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su5);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su5, 'Entorno de Desarrollo Integrado', true),
    (@su5, 'Una base de datos', false),
    (@su5, 'Un navegador web', false),
    (@su5, 'Un sistema operativo', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuál de estos NO es un sistema gestor de bases de datos?', 5);
SET @su6 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su6);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su6, 'Adobe Photoshop', true),
    (@su6, 'MySQL', false),
    (@su6, 'PostgreSQL', false),
    (@su6, 'Oracle', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuál es el comando SQL para consultar datos?', 5);
SET @su7 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su7);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su7, 'SELECT', true),
    (@su7, 'INSERT', false),
    (@su7, 'DELETE', false),
    (@su7, 'CREATE', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué base de datos NO es relacional?', 5);
SET @su8 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su8);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su8, 'MongoDB', true),
    (@su8, 'MySQL', false),
    (@su8, 'PostgreSQL', false),
    (@su8, 'Oracle', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuál es la capital de España donde juega el Real Madrid?', 6);
SET @su9 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su9);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su9, 'Madrid', true),
    (@su9, 'Barcelona', false),
    (@su9, 'Sevilla', false),
    (@su9, 'Valencia', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué jugador es conocido como CR7?', 6);
SET @su10 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su10);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su10, 'Cristiano Ronaldo', true),
    (@su10, 'Lionel Messi', false),
    (@su10, 'Neymar', false),
    (@su10, 'Kylian Mbappé', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuántos jugadores hay en un equipo de fútbol en el campo?', 6);
SET @su11 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su11);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su11, '11', true),
    (@su11, '9', false),
    (@su11, '7', false),
    (@su11, '5', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Quién ganó el Mundial de Fútbol 2022?', 6);
SET @su12 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su12);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su12, 'Argentina', true),
    (@su12, 'Francia', false),
    (@su12, 'Brasil', false),
    (@su12, 'Alemania', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuánto dura un partido de fútbol profesional?', 6);
SET @su13 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su13);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su13, '90 minutos', true),
    (@su13, '60 minutos', false),
    (@su13, '120 minutos', false),
    (@su13, '45 minutos', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuántos puntos vale un triple en baloncesto?', 7);
SET @su14 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su14);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su14, '3 puntos', true),
    (@su14, '2 puntos', false),
    (@su14, '1 punto', false),
    (@su14, '4 puntos', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuál es el número de jugadores en un equipo de baloncesto en cancha?', 7);
SET @su15 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_unica (id) VALUES (@su15);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@su15, '5', true),
    (@su15, '6', false),
    (@su15, '7', false),
    (@su15, '11', false);

-- SELECCIÓN MÚLTIPLE (10 preguntas con opciones)
INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuáles de estos son lenguajes de programación?', 2);
SET @sm1 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm1);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm1, 'Java', true),
    (@sm1, 'Python', true),
    (@sm1, 'MySQL', false),
    (@sm1, 'HTML', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuáles son características de la programación orientada a objetos?', 2);
SET @sm2 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm2);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm2, 'Herencia', true),
    (@sm2, 'Encapsulación', true),
    (@sm2, 'Tablas', false),
    (@sm2, 'Consultas', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué deportes se juegan con balón?', 3);
SET @sm3 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm3);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm3, 'Fútbol', true),
    (@sm3, 'Baloncesto', true),
    (@sm3, 'Natación', false),
    (@sm3, 'Atletismo', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué deportes se practican en equipo?', 3);
SET @sm4 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm4);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm4, 'Fútbol', true),
    (@sm4, 'Baloncesto', true),
    (@sm4, 'Natación individual', false),
    (@sm4, 'Golf', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué características tiene Java?', 4);
SET @sm5 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm5);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm5, 'Orientado a objetos', true),
    (@sm5, 'Multiplataforma', true),
    (@sm5, 'No tipado', false),
    (@sm5, 'Solo frontend', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuáles son tipos de datos en Java?', 4);
SET @sm6 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm6);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm6, 'int', true),
    (@sm6, 'String', true),
    (@sm6, 'tabla', false),
    (@sm6, 'consulta', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuáles son bases de datos relacionales?', 5);
SET @sm7 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm7);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm7, 'MySQL', true),
    (@sm7, 'PostgreSQL', true),
    (@sm7, 'MongoDB', false),
    (@sm7, 'Redis', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué comandos SQL sirven para consultar datos?', 5);
SET @sm8 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm8);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm8, 'SELECT', true),
    (@sm8, 'WHERE', true),
    (@sm8, 'DELETE', false),
    (@sm8, 'DROP', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Qué equipos han ganado la Champions League?', 6);
SET @sm9 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm9);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm9, 'Real Madrid', true),
    (@sm9, 'Barcelona', true),
    (@sm9, 'PSG', false),
    (@sm9, 'Manchester City', false);

INSERT INTO pregunta (texto_pregunta, categoria_id) VALUES ('¿Cuáles son equipos de la Liga Española?', 6);
SET @sm10 = LAST_INSERT_ID();
INSERT INTO pregunta_seleccion_multiple (id) VALUES (@sm10);
INSERT INTO opcion (pregunta_id, texto_opcion, es_correcta) VALUES 
    (@sm10, 'Real Madrid', true),
    (@sm10, 'Barcelona', true),
    (@sm10, 'Manchester United', false),
    (@sm10, 'Bayern Munich', false);
