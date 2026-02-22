package com.miempresa.Proyecto_1.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class ResultadoTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioNombre; 
    private Integer totalPreguntas;
    private Integer correctas;
    private Integer incorrectas;
    private Double nota;           
    private Double porcentaje;     

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public ResultadoTest() {
        this.fecha = new Date();
    }

    public ResultadoTest(String usuarioNombre, Integer totalPreguntas, Integer correctas, 
                         Integer incorrectas, Double nota, Double porcentaje) {
        this.usuarioNombre = usuarioNombre;
        this.totalPreguntas = totalPreguntas;
        this.correctas = correctas;
        this.incorrectas = incorrectas;
        this.nota = nota;
        this.porcentaje = porcentaje;
        this.fecha = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public Integer getTotalPreguntas() { return totalPreguntas; }
    public void setTotalPreguntas(Integer totalPreguntas) { this.totalPreguntas = totalPreguntas; }

    public Integer getCorrectas() { return correctas; }
    public void setCorrectas(Integer correctas) { this.correctas = correctas; }

    public Integer getIncorrectas() { return incorrectas; }
    public void setIncorrectas(Integer incorrectas) { this.incorrectas = incorrectas; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public Double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Double porcentaje) { this.porcentaje = porcentaje; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}