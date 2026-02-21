package com.miempresa.Proyecto_1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.Proyecto_1.Model.*;
import com.miempresa.Proyecto_1.Service.ServicioPregunta;
import com.miempresa.Proyecto_1.Service.ServicioCSV;
import com.miempresa.Proyecto_1.Service.ServicioLog;
import com.miempresa.Proyecto_1.Repository.RepositorioCategoria;
import com.miempresa.Proyecto_1.Repository.RepositorioOpcion;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainWebController {

    @Autowired
    private ServicioPregunta servicioPregunta;

    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @Autowired
    private ServicioCSV servicioCSV;

    @Autowired
    private RepositorioOpcion repositorioOpcion;

    @Autowired
    private ServicioLog servicioLog;  // ← NUEVO: servicio de logs MongoDB

    // Método helper para obtener el usuario actual
    private String getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "desconocido";
    }

    // ========================================
    // HOME Y LOGIN
    // ========================================

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Bienvenido al Quiz");
        model.addAttribute("cabecera", "Sistema de Gestión de Preguntas");
        return "pregunta/home";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) model.addAttribute("error", "Usuario o contraseña incorrectos");
        if (logout != null) model.addAttribute("mensaje", "Has cerrado sesión correctamente");
        return "security/login";
    }

    // ========================================
    // PREGUNTAS - LISTADO CON PAGINACIÓN
    // ========================================

    @GetMapping("/preguntas/todas")
    public String listarPreguntas(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "buscar", required = false) String buscar,
            @RequestParam(value = "tipo", required = false) String tipo,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Pregunta> paginaPreguntas;

        if (buscar != null && !buscar.trim().isEmpty()) {
            paginaPreguntas = servicioPregunta.buscarPreguntasPorTexto(buscar, pageable);
            model.addAttribute("buscar", buscar);
        } else if (tipo != null && !tipo.trim().isEmpty()) {
            paginaPreguntas = servicioPregunta.buscarPorTipo(tipo, pageable);
            model.addAttribute("tipo", tipo);
        } else {
            paginaPreguntas = servicioPregunta.damePreguntasPaginadas(pageable);
        }

        String cabecera = "Listado de Preguntas";
        if ("verdadero_falso".equals(tipo))    cabecera = "Preguntas de Verdadero/Falso";
        if ("seleccion_unica".equals(tipo))    cabecera = "Preguntas de Selección Única";
        if ("seleccion_multiple".equals(tipo)) cabecera = "Preguntas de Selección Múltiple";

        model.addAttribute("cabecera", cabecera);
        model.addAttribute("preguntas", paginaPreguntas.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaPreguntas.getTotalPages());
        model.addAttribute("totalElementos", paginaPreguntas.getTotalElements());
        model.addAttribute("tamanioPagina", size);
        model.addAttribute("hayAnterior", paginaPreguntas.hasPrevious());
        model.addAttribute("haySiguiente", paginaPreguntas.hasNext());

        return "pregunta/lista";
    }

    // ========================================
    // PREGUNTAS - VER / BORRAR
    // ========================================

    @GetMapping("/preguntas/ver/{id}")
    public String verPregunta(@PathVariable Long id, Model model) {
        Pregunta pregunta = servicioPregunta.damePreguntaPorId(id);
        model.addAttribute("titulo", "Ver Pregunta");
        model.addAttribute("cabecera", "Detalle de la Pregunta");
        model.addAttribute("pregunta", pregunta);
        return "pregunta/ver";
    }

    @GetMapping("/preguntas/borrar/{id}")
    public String borrarPregunta(@PathVariable Long id, RedirectAttributes flash) {
        Pregunta pregunta = servicioPregunta.damePreguntaPorId(id);

        // ← LOG MongoDB: registrar borrado
        if (pregunta != null) {
            servicioLog.registrar("BORRAR", pregunta.getTipo(), pregunta.getId(),
                    pregunta.getTextoPregunta(), getUsuarioActual());
        }

        servicioPregunta.borraPreguntaPorId(id);
        flash.addFlashAttribute("warning", "⚠️ Pregunta borrada con éxito");
        return "redirect:/preguntas/todas";
    }

    // ========================================
    // PREGUNTAS - FORMULARIO NUEVA
    // ========================================

    @GetMapping("/preguntas/form")
    public String formularioPregunta(Model model) {
        model.addAttribute("titulo", "Nueva Pregunta");
        model.addAttribute("cabecera", "Formulario de Pregunta");
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "pregunta/form";
    }

    @PostMapping("/preguntas/guardar")
    public String guardarPregunta(
            @RequestParam("tipo") String tipo,
            @RequestParam("textoPregunta") String textoPregunta,
            @RequestParam(value = "categoriaId", required = false) Long categoriaId,
            @RequestParam(value = "respuestaCorrecta", required = false) Boolean respuestaCorrecta,
            @RequestParam(value = "textoOpcionSU", required = false) List<String> textosOpcionesSU,
            @RequestParam(value = "opcionCorrectaSU", required = false) Integer opcionCorrectaSU,
            @RequestParam(value = "textoOpcionSM", required = false) List<String> textosOpcionesSM,
            @RequestParam(value = "opcionCorrectaSM", required = false) List<Integer> opcionesCorrectasSM,
            RedirectAttributes flash) {

        // Buscar categoría si se seleccionó una
        Categoria categoria = null;
        if (categoriaId != null) {
            categoria = repositorioCategoria.findById(categoriaId).orElse(null);
        }

        Pregunta preguntaGuardada = null;

        if ("verdadero_falso".equals(tipo)) {
            PreguntaVerdaderoFalso vf = new PreguntaVerdaderoFalso();
            vf.setTextoPregunta(textoPregunta);
            vf.setCategoria(categoria);
            vf.setRespuestaCorrecta(respuestaCorrecta != null && respuestaCorrecta);
            preguntaGuardada = servicioPregunta.guardarPregunta(vf);

        } else if ("seleccion_unica".equals(tipo)) {
            PreguntaSeleccionUnica su = new PreguntaSeleccionUnica();
            su.setTextoPregunta(textoPregunta);
            su.setCategoria(categoria);
            PreguntaSeleccionUnica suGuardada = (PreguntaSeleccionUnica) servicioPregunta.guardarPregunta(su);
            if (textosOpcionesSU != null) {
                List<Opcion> opciones = new ArrayList<>();
                for (int i = 0; i < textosOpcionesSU.size(); i++) {
                    String texto = textosOpcionesSU.get(i).trim();
                    if (!texto.isEmpty()) {
                        Opcion opcion = new Opcion();
                        opcion.setTextoOpcion(texto);
                        opcion.setEsCorrecta(opcionCorrectaSU != null && opcionCorrectaSU == (i + 1));
                        opcion.setPregunta(suGuardada);
                        opciones.add(opcion);
                    }
                }
                suGuardada.setOpciones(opciones);
                preguntaGuardada = servicioPregunta.guardarPregunta(suGuardada);
            } else {
                preguntaGuardada = suGuardada;
            }

        } else if ("seleccion_multiple".equals(tipo)) {
            PreguntaSeleccionMultiple sm = new PreguntaSeleccionMultiple();
            sm.setTextoPregunta(textoPregunta);
            sm.setCategoria(categoria);
            PreguntaSeleccionMultiple smGuardada = (PreguntaSeleccionMultiple) servicioPregunta.guardarPregunta(sm);
            if (textosOpcionesSM != null) {
                List<Opcion> opciones = new ArrayList<>();
                for (int i = 0; i < textosOpcionesSM.size(); i++) {
                    String texto = textosOpcionesSM.get(i).trim();
                    if (!texto.isEmpty()) {
                        Opcion opcion = new Opcion();
                        opcion.setTextoOpcion(texto);
                        opcion.setEsCorrecta(opcionesCorrectasSM != null && opcionesCorrectasSM.contains(i + 1));
                        opcion.setPregunta(smGuardada);
                        opciones.add(opcion);
                    }
                }
                smGuardada.setOpciones(opciones);
                preguntaGuardada = servicioPregunta.guardarPregunta(smGuardada);
            } else {
                preguntaGuardada = smGuardada;
            }
        }

        // ← LOG MongoDB: registrar creación
        if (preguntaGuardada != null) {
            servicioLog.registrar("CREAR", tipo, preguntaGuardada.getId(),
                    textoPregunta, getUsuarioActual());
        }

        flash.addFlashAttribute("success", "✅ Pregunta guardada correctamente");
        return "redirect:/preguntas/todas";
    }

    // ========================================
    // PREGUNTAS - EDITAR
    // ========================================

    @GetMapping("/preguntas/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Pregunta pregunta = servicioPregunta.damePreguntaPorId(id);

        if (pregunta == null) {
            flash.addFlashAttribute("error", "❌ Pregunta no encontrada");
            return "redirect:/preguntas/todas";
        }

        String tipo = "";
        if (pregunta instanceof PreguntaVerdaderoFalso)    tipo = "verdadero_falso";
        else if (pregunta instanceof PreguntaSeleccionUnica)    tipo = "seleccion_unica";
        else if (pregunta instanceof PreguntaSeleccionMultiple) tipo = "seleccion_multiple";

        model.addAttribute("titulo", "Editar Pregunta");
        model.addAttribute("cabecera", "Editar Pregunta #" + id);
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("tipo", tipo);
        model.addAttribute("categorias", repositorioCategoria.findAll());

        return "pregunta/editar";
    }

    @PostMapping("/preguntas/actualizar")
    public String actualizarPregunta(
            @RequestParam("id") Long id,
            @RequestParam("tipo") String tipo,
            @RequestParam("textoPregunta") String textoPregunta,
            @RequestParam(value = "categoriaId", required = false) Long categoriaId,
            @RequestParam(value = "respuestaCorrecta", required = false) Boolean respuestaCorrecta,
            @RequestParam(value = "textoOpcionSU", required = false) List<String> textosOpcionesSU,
            @RequestParam(value = "opcionCorrectaSU", required = false) Integer opcionCorrectaSU,
            @RequestParam(value = "idOpcionSU", required = false) List<Long> idsOpcionesSU,
            @RequestParam(value = "textoOpcionSM", required = false) List<String> textosOpcionesSM,
            @RequestParam(value = "opcionCorrectaSM", required = false) List<Integer> opcionesCorrectasSM,
            @RequestParam(value = "idOpcionSM", required = false) List<Long> idsOpcionesSM,
            RedirectAttributes flash) {

        Pregunta pregunta = servicioPregunta.damePreguntaPorId(id);
        if (pregunta == null) {
            flash.addFlashAttribute("error", "❌ Pregunta no encontrada");
            return "redirect:/preguntas/todas";
        }

        // Buscar categoría si se seleccionó una
        Categoria categoria = null;
        if (categoriaId != null) {
            categoria = repositorioCategoria.findById(categoriaId).orElse(null);
        }

        if ("verdadero_falso".equals(tipo)) {
            PreguntaVerdaderoFalso vf = (PreguntaVerdaderoFalso) pregunta;
            vf.setTextoPregunta(textoPregunta);
            vf.setCategoria(categoria);
            vf.setRespuestaCorrecta(respuestaCorrecta != null && respuestaCorrecta);
            servicioPregunta.guardarPregunta(vf);

        } else if ("seleccion_unica".equals(tipo)) {
            PreguntaSeleccionUnica su = (PreguntaSeleccionUnica) pregunta;
            su.setTextoPregunta(textoPregunta);
            su.setCategoria(categoria);
            repositorioOpcion.deleteAll(su.getOpciones());
            if (textosOpcionesSU != null) {
                List<Opcion> nuevasOpciones = new ArrayList<>();
                for (int i = 0; i < textosOpcionesSU.size(); i++) {
                    String texto = textosOpcionesSU.get(i).trim();
                    if (!texto.isEmpty()) {
                        Opcion opcion = new Opcion();
                        opcion.setTextoOpcion(texto);
                        opcion.setEsCorrecta(opcionCorrectaSU != null && opcionCorrectaSU == (i + 1));
                        opcion.setPregunta(su);
                        nuevasOpciones.add(opcion);
                    }
                }
                su.setOpciones(nuevasOpciones);
            }
            servicioPregunta.guardarPregunta(su);

        } else if ("seleccion_multiple".equals(tipo)) {
            PreguntaSeleccionMultiple sm = (PreguntaSeleccionMultiple) pregunta;
            sm.setTextoPregunta(textoPregunta);
            sm.setCategoria(categoria);
            repositorioOpcion.deleteAll(sm.getOpciones());
            if (textosOpcionesSM != null) {
                List<Opcion> nuevasOpciones = new ArrayList<>();
                for (int i = 0; i < textosOpcionesSM.size(); i++) {
                    String texto = textosOpcionesSM.get(i).trim();
                    if (!texto.isEmpty()) {
                        Opcion opcion = new Opcion();
                        opcion.setTextoOpcion(texto);
                        opcion.setEsCorrecta(opcionesCorrectasSM != null && opcionesCorrectasSM.contains(i + 1));
                        opcion.setPregunta(sm);
                        nuevasOpciones.add(opcion);
                    }
                }
                sm.setOpciones(nuevasOpciones);
            }
            servicioPregunta.guardarPregunta(sm);
        }

       
        servicioLog.registrar("EDITAR", tipo, id, textoPregunta, getUsuarioActual());

        flash.addFlashAttribute("success", "✅ Pregunta actualizada correctamente");
        return "redirect:/preguntas/todas";
    }

    // ========================================
    // LOGS DE ACTIVIDAD (MongoDB)
    // ========================================

    @GetMapping("/logs")
    public String verLogs(Model model) {
        model.addAttribute("titulo", "Logs de Actividad");
        model.addAttribute("cabecera", "Historial de Actividad");
        model.addAttribute("logs", servicioLog.obtenerTodos());
        return "logs/lista";
    }

    // ========================================
    // UPLOAD CSV
    // ========================================

    @GetMapping("/preguntas/importar")
    public String mostrarFormularioImportar(Model model) {
        model.addAttribute("titulo", "Importar Preguntas CSV");
        model.addAttribute("cabecera", "Importar Preguntas desde CSV");
        return "pregunta/importar";
    }

    @PostMapping("/preguntas/importar")
    public String procesarImportacionCSV(
            @RequestParam("archivo") MultipartFile archivo,
            RedirectAttributes flash) {

        if (archivo.isEmpty()) {
            flash.addFlashAttribute("error", "❌ Por favor selecciona un archivo CSV");
            return "redirect:/preguntas/importar";
        }

        String nombreArchivo = archivo.getOriginalFilename();
        if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
            flash.addFlashAttribute("error", "❌ El archivo debe ser de tipo CSV (.csv)");
            return "redirect:/preguntas/importar";
        }

        ServicioCSV.ResultadoImportacion resultado = servicioCSV.importarDesdeCSV(archivo);

        // ← LOG MongoDB: registrar importación CSV
        servicioLog.registrar("IMPORTAR_CSV", "varios", null,
                "Importadas: " + resultado.getPreguntasImportadas() + " preguntas",
                getUsuarioActual());

        if (resultado.getPreguntasImportadas() > 0) {
            flash.addFlashAttribute("success",
                "✅ Se importaron " + resultado.getPreguntasImportadas() + " preguntas correctamente");
        }
        if (resultado.tieneErrores()) {
            StringBuilder errores = new StringBuilder("⚠️ Errores: ");
            resultado.getErrores().forEach(e -> errores.append(e).append(" | "));
            flash.addFlashAttribute("warning", errores.toString());
        }
        if (resultado.getPreguntasImportadas() == 0 && !resultado.tieneErrores()) {
            flash.addFlashAttribute("warning", "⚠️ El archivo CSV está vacío");
        }

        return "redirect:/preguntas/todas";
    }
}