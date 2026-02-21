package com.miempresa.Proyecto_1.Model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class PreguntaSeleccionUnica extends Pregunta {

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL)
    private List<Opcion> opciones;

    public String getTipo() {
        return "seleccion_unica";
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        if (opciones != null) {
            for (Opcion op : opciones) {
                op.setPregunta(this);
            }
        }
    }
}