// src/main/java/com/devweb2025a/actividad3/controllers/PasswordRecoveryController.java
    package com.devweb2025a.actividad3.controllers;

    import com.devweb2025a.actividad3.Models.entities.Usuario;
    import com.devweb2025a.actividad3.Models.services.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    @Controller
    @RequestMapping("/usuario") // Cambiado a /usuario para que coincida con th:action en la plantilla
    public class PasswordRecoveryController {

        @Autowired
        private UsuarioService usuarioService;

        // Muestra el formulario para solicitar la recuperación de contraseña
        @GetMapping("/recordar_password") // Coincide con th:href en la plantilla
        public String showRequestForm() {
            return "forms/usuarios/recordar_password"; // Nombre de tu plantilla
        }

        // Procesa la solicitud para enviar la contraseña por correo
        @PostMapping("/recordar_password") // Coincide con th:action en la plantilla
        public String processRequestForm(@RequestParam("identifier") String identifier, Model model) { // Changed from @RequestParam("email") to @RequestParam("identifier")
            Usuario usuario = usuarioService.iniciarRecordarPassword(identifier);

            if (usuario != null) {
                usuarioService.sendPasswordRecoveryEmail(usuario);
                model.addAttribute("message", "Si el correo o nombre de usuario existe, se ha enviado un correo con la contraseña."); // Usando el modelo para mostrar el mensaje
            } else {
                // Mensaje genérico por seguridad
                model.addAttribute("message", "Si el correo o nombre de usuario existe, se ha enviado un correo con la contraseña.");
            }
            return "forms/usuarios/recordar_password"; // Regresa a la misma plantilla para mostrar el mensaje
        }
    }
