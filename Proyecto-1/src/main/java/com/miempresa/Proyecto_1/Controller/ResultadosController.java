package com.miempresa.Proyecto_1.Controller;

import com.miempresa.Proyecto_1.Model.ResultadoTest;
import com.miempresa.Proyecto_1.Repository.RepositorioResultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ResultadosController {

    @Autowired
    private RepositorioResultado repositorioResultado;

    @GetMapping("/resultados")
    public String verResultados(Model model) {
        model.addAttribute("titulo", "Resultados de Tests");
        model.addAttribute("cabecera", "📊 Resultados de Tests de Usuarios");
        
        List<ResultadoTest> resultados = repositorioResultado.findAllByOrderByFechaDesc();
        model.addAttribute("resultados", resultados);
        
        return "resultados/lista";
    }
}