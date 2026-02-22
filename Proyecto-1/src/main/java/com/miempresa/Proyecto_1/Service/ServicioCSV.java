package com.miempresa.Proyecto_1.Service;

import com.miempresa.Proyecto_1.Model.PreguntaVerdaderoFalso;
import com.miempresa.Proyecto_1.Model.PreguntaSeleccionUnica;
import com.miempresa.Proyecto_1.Model.PreguntaSeleccionMultiple;
import com.miempresa.Proyecto_1.Model.Pregunta;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioCSV {

    @Autowired
    private ServicioPregunta servicioPregunta;
    
    public ResultadoImportacion importarDesdeCSV(MultipartFile archivo) {
        
        ResultadoImportacion resultado = new ResultadoImportacion();
        List<String> errores = new ArrayList<>();
        int linea = 1;

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

            String[] fila;
            boolean primeraFila = true;

            while ((fila = reader.readNext()) != null) {
                
                if (primeraFila) {
                    primeraFila = false;
                    if (fila[0].trim().equalsIgnoreCase("tipo")) {
                        linea++;
                        continue;
                    }
                }

                try {
                    if (fila.length < 2) {
                        errores.add("Línea " + linea + ": Formato incorrecto (mínimo 2 columnas)");
                        linea++;
                        continue;
                    }

                    String tipo = fila[0].trim().toLowerCase();
                    String textoPregunta = fila[1].trim();

                    if (textoPregunta.isEmpty()) {
                        errores.add("Línea " + linea + ": El texto de la pregunta está vacío");
                        linea++;
                        continue;
                    }

                    Pregunta pregunta = null;

                    switch (tipo) {
                        case "verdadero_falso":
                        case "vf":
                        case "v/f":
                            PreguntaVerdaderoFalso vf = new PreguntaVerdaderoFalso();
                            vf.setTextoPregunta(textoPregunta);
                            if (fila.length >= 3 && !fila[2].trim().isEmpty()) {
                                String respuesta = fila[2].trim().toLowerCase();
                                vf.setRespuestaCorrecta(
                                    respuesta.equals("true") || 
                                    respuesta.equals("verdadero") || 
                                    respuesta.equals("si") ||
                                    respuesta.equals("sí") ||
                                    respuesta.equals("1")
                                );
                            } else {
                                vf.setRespuestaCorrecta(false); 
                            }
                            pregunta = vf;
                            break;

                        case "seleccion_unica":
                        case "su":
                        case "unica":
                            PreguntaSeleccionUnica su = new PreguntaSeleccionUnica();
                            su.setTextoPregunta(textoPregunta);
                            pregunta = su;
                            break;

                        case "seleccion_multiple":
                        case "sm":
                        case "multiple":
                            PreguntaSeleccionMultiple sm = new PreguntaSeleccionMultiple();
                            sm.setTextoPregunta(textoPregunta);
                            pregunta = sm;
                            break;

                        default:
                            errores.add("Línea " + linea + ": Tipo desconocido '" + tipo + 
                                "'. Use: verdadero_falso, seleccion_unica, seleccion_multiple");
                            linea++;
                            continue;
                    }

                    servicioPregunta.guardarPregunta(pregunta);
                    resultado.incrementarImportadas();

                } catch (Exception e) {
                    errores.add("Línea " + linea + ": Error al procesar - " + e.getMessage());
                }

                linea++;
            }

        } catch (IOException | CsvValidationException e) {
            errores.add("Error al leer el archivo: " + e.getMessage());
        }

        resultado.setErrores(errores);
        return resultado;
    }

    // ========================================
    // CLASE INTERNA: Resultado 
    // ========================================
    
    public static class ResultadoImportacion {
        private int preguntasImportadas = 0;
        private List<String> errores = new ArrayList<>();

        public void incrementarImportadas() {
            this.preguntasImportadas++;
        }

        public int getPreguntasImportadas() {
            return preguntasImportadas;
        }

        public List<String> getErrores() {
            return errores;
        }

        public void setErrores(List<String> errores) {
            this.errores = errores;
        }

        public boolean tieneErrores() {
            return !errores.isEmpty();
        }
    }
}