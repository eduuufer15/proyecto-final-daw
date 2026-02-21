package com.miempresa.Proyecto_1.Repository;

import com.miempresa.Proyecto_1.Model.LogActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioLog extends MongoRepository<LogActividad, String> {

    // Buscar logs por usuario
    List<LogActividad> findByUsuario(String usuario);

    // Buscar logs por acción (CREAR, EDITAR, BORRAR...)
    List<LogActividad> findByAccion(String accion);

    // Buscar logs por tipo de pregunta
    List<LogActividad> findByTipoPregunta(String tipoPregunta);

    // Los más recientes primero
    List<LogActividad> findAllByOrderByFechaDesc();
}