package com.miempresa.Proyecto_1.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "logs_actividad")  
public class LogActividad {

    @Id
    private String id;  

    private String accion;         
    private String tipoPregunta;   
    private Long idPregunta;       
    private String textoPregunta;  
    private String usuario;        
    private Date fecha;            

 
    public LogActividad(String accion, String tipoPregunta, Long idPregunta,
                        String textoPregunta, String usuario) {
        this.accion = accion;
        this.tipoPregunta = tipoPregunta;
        this.idPregunta = idPregunta;
        this.textoPregunta = textoPregunta;
        this.usuario = usuario;
        this.fecha = new Date();  
    }

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getTipoPregunta() { return tipoPregunta; }
    public void setTipoPregunta(String tipoPregunta) { this.tipoPregunta = tipoPregunta; }

    public Long getIdPregunta() { return idPregunta; }
    public void setIdPregunta(Long idPregunta) { this.idPregunta = idPregunta; }

    public String getTextoPregunta() { return textoPregunta; }
    public void setTextoPregunta(String textoPregunta) { this.textoPregunta = textoPregunta; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}