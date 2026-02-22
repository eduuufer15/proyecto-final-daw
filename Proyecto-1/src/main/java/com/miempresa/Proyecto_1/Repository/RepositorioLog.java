package com.miempresa.Proyecto_1.Repository;

import com.miempresa.Proyecto_1.Model.LogActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioLog extends MongoRepository<LogActividad, String> {

    // Busca los logs por usuario
    List<LogActividad> findByUsuario(String usuario);

    // Busca los logs por acción (CREAR, EDITAR, BORRAR... etc)
    List<LogActividad> findByAccion(String accion);

    // Busca los logs por tipo de pregunta
    List<LogActividad> findByTipoPregunta(String tipoPregunta);

    
    List<LogActividad> findAllByOrderByFechaDesc();
}