package com.miempresa.Proyecto_1.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miempresa.Proyecto_1.Model.Pregunta;
import com.miempresa.Proyecto_1.Repository.RepositorioPregunta;

@Service
public class ServicioPregunta {

    @Autowired
    private RepositorioPregunta repositorioPregunta;

    public List<Pregunta> dameTodasLasPreguntas() {
        return repositorioPregunta.findAll();
    }

    public Pregunta damePreguntaPorId(Long id) {
        return repositorioPregunta.findById(id).orElse(null);
    }

    public Pregunta guardarPregunta(Pregunta pregunta) {
        return repositorioPregunta.save(pregunta);
    }

    public void borraPreguntaPorId(Long id) {
        repositorioPregunta.deleteById(id);
    }

    // ── PAGINACIÓN ─────────────
    
    public Page<Pregunta> damePreguntasPaginadas(Pageable pageable) {
        return repositorioPregunta.findAll(pageable);
    }

    public Page<Pregunta> buscarPreguntasPorTexto(String texto, Pageable pageable) {
        return repositorioPregunta.buscarPorTexto(texto, pageable);
    }

    public Page<Pregunta> buscarPorTipo(String tipo, Pageable pageable) {
        switch (tipo) {
            case "verdadero_falso":    return repositorioPregunta.buscarVerdaderoFalso(pageable);
            case "seleccion_unica":    return repositorioPregunta.buscarSeleccionUnica(pageable);
            case "seleccion_multiple": return repositorioPregunta.buscarSeleccionMultiple(pageable);
            default:                   return repositorioPregunta.findAll(pageable);
        }
    }
}