package com.miempresa.Proyecto_1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.miempresa.Proyecto_1.Model.Opcion;

@Repository
public interface RepositorioOpcion extends JpaRepository<Opcion, Long> {
	
    @Query("SELECT o FROM Opcion o WHERE o.pregunta.id = ?1")
    List<Opcion> obtenerOpcionesPorPregunta(Long idPregunta);
    
}
