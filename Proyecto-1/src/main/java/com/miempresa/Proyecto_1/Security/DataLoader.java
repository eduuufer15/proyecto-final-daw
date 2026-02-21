package com.miempresa.Proyecto_1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.miempresa.Proyecto_1.Model.Usuario;
import com.miempresa.Proyecto_1.Repository.RepositorioUsuario;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!repositorioUsuario.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@quiz.com");
            admin.setRol(Usuario.Rol.ADMIN); 
            admin.setEnabled(true);
            repositorioUsuario.save(admin);
        }

        if (!repositorioUsuario.existsByUsername("user")) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@quiz.com");
            user.setRol(Usuario.Rol.USER); 
            user.setEnabled(true);
            repositorioUsuario.save(user);
        }

        if (!repositorioUsuario.existsByUsername("Eduardo")) {
            Usuario eduardo = new Usuario();
            eduardo.setUsername("Eduardo");
            eduardo.setPassword(passwordEncoder.encode("eduardo123"));
            eduardo.setEmail("eduardo@quiz.com");
            eduardo.setRol(Usuario.Rol.USER);
            eduardo.setEnabled(true);
            repositorioUsuario.save(eduardo);
        }

        if (!repositorioUsuario.existsByUsername("Javier")) {
            Usuario javier = new Usuario();
            javier.setUsername("Javier");
            javier.setPassword(passwordEncoder.encode("javier123"));
            javier.setEmail("javier@quiz.com");
            javier.setRol(Usuario.Rol.USER);
            javier.setEnabled(true);
            repositorioUsuario.save(javier);
        }
    }
}