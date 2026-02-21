package com.miempresa.Proyecto_1.Model;

import jakarta.persistence.Entity;

@Entity
public class PreguntaVerdaderoFalso extends Pregunta {

    private Boolean respuestaCorrecta;

    public String getTipo() {
        return "verdadero_falso";
    }

    public Boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(Boolean respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}