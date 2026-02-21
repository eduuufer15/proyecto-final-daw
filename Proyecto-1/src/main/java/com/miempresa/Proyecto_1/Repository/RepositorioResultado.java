package com.miempresa.Proyecto_1.Repository;

import com.miempresa.Proyecto_1.Model.ResultadoTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositorioResultado extends JpaRepository<ResultadoTest, Long> {
    
    // Buscar por usuario
    List<ResultadoTest> findByUsuarioNombre(String usuarioNombre);
    
    // Obtener todos ordenados por fecha descendente (más recientes primero)
    List<ResultadoTest> findAllByOrderByFechaDesc();
}