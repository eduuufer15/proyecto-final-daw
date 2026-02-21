package com.miempresa.Proyecto_1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.Proyecto_1.Model.Categoria;
import com.miempresa.Proyecto_1.Repository.RepositorioCategoria;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private RepositorioCategoria repositorioCategoria;

    // ── LISTAR TODAS ─────────────────────────────────
    @GetMapping("/todas")
    public String listarCategorias(Model model) {
        model.addAttribute("titulo", "Categorías");
        model.addAttribute("cabecera", "Listado de Categorías");
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "categoria/lista";
    }

    // ── FORMULARIO CATEGORÍA ────────────────────
    
    @GetMapping("/form")
    public String formularioNueva(Model model) {
        model.addAttribute("titulo", "Nueva Categoría");
        model.addAttribute("cabecera", "Nueva Categoría");
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    // ── FORMULARIO EDITAR CATEGORÍA ───────────────────
    
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Categoria categoria = repositorioCategoria.findById(id).orElse(null);
        if (categoria == null) {
            flash.addFlashAttribute("error", "❌ Categoría no encontrada");
            return "redirect:/categorias/todas";
        }
        model.addAttribute("titulo", "Editar Categoría");
        model.addAttribute("cabecera", "Editar Categoría");
        model.addAttribute("categoria", categoria);
        return "categoria/form";
    }

    // ── GUARDAR ─────────────────────
    
    @PostMapping("/guardar")
    public String guardarCategoria(
            @ModelAttribute Categoria categoria,
            RedirectAttributes flash) {

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            flash.addFlashAttribute("error", "❌ El nombre de la categoría no puede estar vacío");
            return "redirect:/categorias/form";
        }

        boolean esNueva = (categoria.getId() == null);
        repositorioCategoria.save(categoria);

        if (esNueva) {
            flash.addFlashAttribute("success", "✅ Categoría creada correctamente");
        } else {
            flash.addFlashAttribute("success", "✅ Categoría actualizada correctamente");
        }

        return "redirect:/categorias/todas";
    }

    // ── BORRAR ────────────────────────────────────────
    
    @GetMapping("/borrar/{id}")
    public String borrarCategoria(@PathVariable Long id, RedirectAttributes flash) {
        try {
            repositorioCategoria.deleteById(id);
            flash.addFlashAttribute("warning", "⚠️ Categoría eliminada correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error",
                "❌ No se puede borrar esta categoría porque tiene preguntas asociadas");
        }
        return "redirect:/categorias/todas";
    }
}