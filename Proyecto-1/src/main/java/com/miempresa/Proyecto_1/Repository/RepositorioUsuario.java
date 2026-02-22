package com.miempresa.Proyecto_1.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miempresa.Proyecto_1.Model.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por nombre de usuario (para el login)
    Optional<Usuario> findByUsername(String username);
    
    // Verificar si existe un usuario con ese user name
    
    boolean existsByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
}
