package com.miempresa.Proyecto_1.Model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;

    @Enumerated(EnumType.STRING) // Indica a JPA que guarde "ADMIN" o "USER" en la columna
    private Rol rol;

    // El Enum vive dentro de la clase para reducir archivos
    public enum Rol { ADMIN, USER }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}