// ======================================
// Custom JavaScript para Quiz App
// ======================================

// Confirmación al iniciar
console.log('✅ Quiz App - JavaScript cargado correctamente');

// Función para confirmar eliminación de preguntas
function confirmarEliminacion(nombrePregunta) {
    return confirm('¿Estás seguro de que deseas eliminar la pregunta: "' + nombrePregunta + '"?');
}

// Función para mostrar alertas que se auto-ocultan
document.addEventListener('DOMContentLoaded', function() {
    // Auto-ocultar alertas después de 5 segundos
    const alertas = document.querySelectorAll('.alert');
    alertas.forEach(function(alerta) {
        setTimeout(function() {
            alerta.style.transition = 'opacity 0.5s';
            alerta.style.opacity = '0';
            setTimeout(function() {
                alerta.remove();
            }, 500);
        }, 5000);
    });
});

// Función para validar formularios antes de enviar
function validarFormulario(formulario) {
    const camposRequeridos = formulario.querySelectorAll('[required]');
    let valido = true;
    
    camposRequeridos.forEach(function(campo) {
        if (!campo.value.trim()) {
            campo.classList.add('is-invalid');
            valido = false;
        } else {
            campo.classList.remove('is-invalid');
        }
    });
    
    return valido;
}
