package com.miempresa.Proyecto_1.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Opcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String textoOpcion;
    private boolean esCorrecta;
    
    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    @JsonBackReference
    private Pregunta pregunta;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTextoOpcion() {
        return textoOpcion;
    }
    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }
    public boolean isEsCorrecta() {
        return esCorrecta;
    }
    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
    public Pregunta getPregunta() {
        return pregunta;
    }
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}