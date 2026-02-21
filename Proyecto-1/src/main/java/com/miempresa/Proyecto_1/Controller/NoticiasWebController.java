package com.miempresa.Proyecto_1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Controller
public class NoticiasWebController {

    private final String API_KEY = "367fe42b09624ab8805c5498d562c7af";
    private final String NewUrl = "https://newsapi.org/v2/top-headlines?category=technology&country=us&pageSize=10&apiKey=";

    @GetMapping("/noticias")
    public String verNoticias(Model model) {
        try {
        	
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(NewUrl + API_KEY, Map.class);
            
            model.addAttribute("titulo", "Tech News");
            model.addAttribute("cabecera", "📰 Latest Technology News");
            model.addAttribute("noticias", response.get("articles"));
            model.addAttribute("status", response.get("status"));
            
            System.out.println("✅ Noticias cargadas: " + response.get("totalResults"));
            System.out.println("📊 Status: " + response.get("status"));
            
        } catch (Exception e) {
            model.addAttribute("error", "Could not load news: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "noticias/lista";
    }
}