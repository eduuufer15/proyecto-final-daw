package com.miempresa.Proyecto_1.Model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreguntaVerdaderoFalso.class, name = "verdadero_falso"),
    @JsonSubTypes.Type(value = PreguntaSeleccionUnica.class, name = "seleccion_unica"),
    @JsonSubTypes.Type(value = PreguntaSeleccionMultiple.class, name = "seleccion_multiple")
})
public abstract class Pregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String textoPregunta;
    
    public abstract String getTipo();
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTextoPregunta() {
        return textoPregunta;
    }
    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}