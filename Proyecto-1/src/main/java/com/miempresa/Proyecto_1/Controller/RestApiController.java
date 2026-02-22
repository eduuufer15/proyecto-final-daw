package com.miempresa.Proyecto_1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.miempresa.Proyecto_1.Model.*;
import com.miempresa.Proyecto_1.Service.ServicioPregunta;
import com.miempresa.Proyecto_1.Repository.RepositorioOpcion;
import com.miempresa.Proyecto_1.Repository.RepositorioUsuario;
import com.miempresa.Proyecto_1.Model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.miempresa.Proyecto_1.Repository.RepositorioResultado;
import com.miempresa.Proyecto_1.Model.ResultadoTest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Quiz API", description = "API REST para la gestión de preguntas y cuestionarios")
public class RestApiController {
    
    @Autowired
    private ServicioPregunta servicioPregunta;
    
    @Autowired
    private RepositorioOpcion repositorioOpcion;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ========================================
    // ENDPOINTS DE PREGUNTAS
    // ========================================
    
    @Operation(
        summary = "Obtener todas las preguntas",
        description = "Devuelve un listado completo de todas las preguntas almacenadas en la base de datos, incluyendo todos los tipos: Verdadero/Falso, Selección Única y Selección Múltiple."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de preguntas obtenida correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pregunta.class))
        ),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/preguntas")
    public ResponseEntity<List<Pregunta>> obtenerTodas() {
        List<Pregunta> preguntas = servicioPregunta.dameTodasLasPreguntas();
        return ResponseEntity.ok(preguntas);
    }

    @Operation(
        summary = "Crear una nueva pregunta",
        description = "Crea una nueva pregunta en la base de datos. El tipo de pregunta se determina automáticamente según el objeto JSON enviado."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Pregunta creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pregunta.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/preguntas")
    public ResponseEntity<Pregunta> crear(
            @Parameter(description = "Objeto pregunta a crear", required = true)
            @RequestBody Pregunta pregunta) {
        Pregunta nuevaPregunta = servicioPregunta.guardarPregunta(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPregunta);
    }

    @Operation(
        summary = "Actualizar una pregunta existente",
        description = "Actualiza los datos de una pregunta específica identificada por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pregunta actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pregunta.class))
        ),
        @ApiResponse(responseCode = "404", description = "Pregunta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/preguntas/{id}")
    public ResponseEntity<Pregunta> editar(
            @Parameter(description = "ID de la pregunta a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Datos actualizados de la pregunta", required = true)
            @RequestBody Pregunta pregunta) {
        pregunta.setId(id);
        Pregunta preguntaActualizada = servicioPregunta.guardarPregunta(pregunta);
        return ResponseEntity.ok(preguntaActualizada);
    }

    @Operation(
        summary = "Eliminar una pregunta",
        description = "Elimina permanentemente una pregunta de la base de datos según su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pregunta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    })
    @DeleteMapping("/preguntas/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la pregunta a eliminar", required = true)
            @PathVariable Long id) {
        servicioPregunta.borraPreguntaPorId(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================
    // ENDPOINTS DE OPCIONES
    // ========================================
    
    @Operation(
        summary = "Obtener opciones de una pregunta",
        description = "Devuelve todas las opciones de respuesta asociadas a una pregunta específica (para preguntas de Selección Única o Múltiple)."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de opciones obtenida correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Opcion.class))
        ),
        @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    })
    @GetMapping("/opciones/por-pregunta/{idPregunta}")
    public ResponseEntity<List<Opcion>> obtenerOpciones(
            @Parameter(description = "ID de la pregunta", required = true)
            @PathVariable Long idPregunta) {
        List<Opcion> opciones = repositorioOpcion.obtenerOpcionesPorPregunta(idPregunta);
        return ResponseEntity.ok(opciones);
    }

    // ========================================
    // API EXTERNA
    // ========================================
    
    @Operation(
        summary = "Obtener dato aleatorio de API externa",
        description = "Integración con Chuck Norris API. Devuelve un chiste aleatorio como ejemplo de consumo de API externa."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Dato externo obtenido correctamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @GetMapping("/externa/info")
    public ResponseEntity<Object> obtenerDatoExterno() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.chucknorris.io/jokes/random";
            Object respuesta = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Error al conectar con el servicio externo");
        }
    }

    // ========================================
    // ENDPOINTS DE USUARIOS (para el React)
    // ========================================

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        usuarios.forEach(u -> u.setPassword("***"));
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = repositorioUsuario.findById(id);
        if (usuario.isEmpty()) return ResponseEntity.notFound().build();
        usuario.get().setPassword("***");
        return ResponseEntity.ok(usuario.get());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        if (repositorioUsuario.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("El usuario '" + usuario.getUsername() + "' ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEnabled(true);
        if (usuario.getRol() == null) usuario.setRol(Usuario.Rol.USER);
        Usuario guardado = repositorioUsuario.save(usuario);
        guardado.setPassword("***");
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
        Optional<Usuario> optional = repositorioUsuario.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Usuario usuario = optional.get();
        usuario.setUsername(datos.getUsername());
        usuario.setEmail(datos.getEmail());
        usuario.setRol(datos.getRol());
        usuario.setEnabled(datos.isEnabled());
        if (datos.getPassword() != null && !datos.getPassword().isEmpty()
                && !datos.getPassword().equals("***")) {
            usuario.setPassword(passwordEncoder.encode(datos.getPassword()));
        }
        Usuario guardado = repositorioUsuario.save(usuario);
        guardado.setPassword("***");
        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!repositorioUsuario.existsById(id)) return ResponseEntity.notFound().build();
        repositorioUsuario.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================
    // API EXTERNA - NEWS API
    // ========================================

    @GetMapping("/noticias")
    public ResponseEntity<Object> obtenerNoticias() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "367fe42b09624ab8805c5498d562c7af";
            String url = "https://newsapi.org/v2/top-headlines?category=technology&language=es&pageSize=10&apiKey=" + apiKey;
            Object respuesta = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Error al conectar con News API: " + e.getMessage());
        }
    }

    // ========================================
    // RESULTADOS DE TESTS (desde el React Native)
    // ========================================

    @Autowired
    private RepositorioResultado repositorioResultado;

    @PostMapping("/resultados")
    public ResponseEntity<ResultadoTest> guardarResultado(@RequestBody ResultadoTest resultado) {
        ResultadoTest guardado = repositorioResultado.save(resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/resultados")
    public ResponseEntity<List<ResultadoTest>> obtenerResultados() {
        List<ResultadoTest> resultados = repositorioResultado.findAllByOrderByFechaDesc();
        return ResponseEntity.ok(resultados);
    }
}