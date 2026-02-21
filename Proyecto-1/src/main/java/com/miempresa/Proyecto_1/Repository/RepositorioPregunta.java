package com.miempresa.Proyecto_1.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.miempresa.Proyecto_1.Model.Pregunta;
import com.miempresa.Proyecto_1.Model.PreguntaVerdaderoFalso;

@Repository
public interface RepositorioPregunta extends JpaRepository<Pregunta, Long> {

    // Obtener solo preguntas de Verdadero/Falso
    @Query("SELECT p FROM PreguntaVerdaderoFalso p")
    List<PreguntaVerdaderoFalso> obtenerPreguntasVerdaderoFalso();

    // Paginación (findAll con Pageable ya viene de JpaRepository)
    Page<Pregunta> findAll(Pageable pageable);

    // Búsqueda por texto con paginación
    @Query("SELECT p FROM Pregunta p WHERE LOWER(p.textoPregunta) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<Pregunta> buscarPorTexto(String texto, Pageable pageable);

    // Filtrar por tipo usando el nombre de la clase
    @Query("SELECT p FROM PreguntaVerdaderoFalso p")
    Page<Pregunta> buscarVerdaderoFalso(Pageable pageable);

    @Query("SELECT p FROM PreguntaSeleccionUnica p")
    Page<Pregunta> buscarSeleccionUnica(Pageable pageable);

    @Query("SELECT p FROM PreguntaSeleccionMultiple p")
    Page<Pregunta> buscarSeleccionMultiple(Pageable pageable);
}