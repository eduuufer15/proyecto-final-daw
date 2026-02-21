package com.miempresa.Proyecto_1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        
        // Obtener el código de estado del error
    	
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        // Obtener información adicional del error
        
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        
        // Agregar información al modelo
        
        model.addAttribute("timestamp", new java.util.Date());
        model.addAttribute("path", requestUri != null ? requestUri : "Desconocido");
        model.addAttribute("errorMessage", errorMessage != null ? errorMessage : "Ha ocurrido un error");
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            // Error 404 - Página no encontrada
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorTitle", "Página No Encontrada");
                model.addAttribute("errorDescription", 
                    "Lo sentimos, la página que estás buscando no existe o ha sido movida.");
                return "error/error-404";
            }
                        
            // Error 500 - Error interno del servidor
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorTitle", "Error Interno del Servidor");
                model.addAttribute("errorDescription", 
                    "Ha ocurrido un error inesperado. Nuestro equipo ha sido notificado.");
                
                // Agregar información de la excepción si existe
                
                if (exception != null) {
                    model.addAttribute("exceptionType", exception.getClass().getSimpleName());
                    model.addAttribute("exceptionMessage", exception.getMessage());
                }
                
                return "error/error-500";
            }
            
            // Otros errores
            
            else {
                model.addAttribute("errorCode", statusCode);
                model.addAttribute("errorTitle", "Error " + statusCode);
                model.addAttribute("errorDescription", "Ha ocurrido un error inesperado.");
                return "error/error-500";
            }
        }
        
        // Error genérico si no hay código de estado
        
        model.addAttribute("errorCode", "ERROR");
        model.addAttribute("errorTitle", "Error Desconocido");
        model.addAttribute("errorDescription", "Ha ocurrido un error inesperado.");
        return "error/error-500";
    }
}