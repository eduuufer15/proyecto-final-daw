package com.miempresa.Proyecto_1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miempresa.Proyecto_1.Model.Categoria;

@Repository
public interface RepositorioCategoria extends JpaRepository<Categoria, Long> {


}