package com.miempresa.Proyecto_1.Service;

import com.miempresa.Proyecto_1.Model.LogActividad;
import com.miempresa.Proyecto_1.Repository.RepositorioLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLog {

    @Autowired
    private RepositorioLog repositorioLog;

    // Registrar una acción en MongoDB
    
    public void registrar(String accion, String tipoPregunta,
                          Long idPregunta, String textoPregunta, String usuario) {
        LogActividad log = new LogActividad(accion, tipoPregunta, idPregunta, textoPregunta, usuario);
        repositorioLog.save(log);
    }

    // Obtener todos los logs ordenados por fecha (más reciente primero)
    
    public List<LogActividad> obtenerTodos() {
        return repositorioLog.findAllByOrderByFechaDesc();
    }

    // Obtener logs de un usuario concreto
    
    public List<LogActividad> obtenerPorUsuario(String usuario) {
        return repositorioLog.findByUsuario(usuario);
    }

    // Obtener logs por acción
    
    public List<LogActividad> obtenerPorAccion(String accion) {
        return repositorioLog.findByAccion(accion);
    }
}