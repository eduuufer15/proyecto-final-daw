package com.miempresa.Proyecto_1.Repository;

import com.miempresa.Proyecto_1.Model.ResultadoTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositorioResultado extends JpaRepository<ResultadoTest, Long> {
    
    
    List<ResultadoTest> findByUsuarioNombre(String usuarioNombre);
    
    List<ResultadoTest> findAllByOrderByFechaDesc();
}