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
@RequestMapping("/usuarios")
public class PasswordRecoveryController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el formulario para solicitar la recuperación de contraseña
    @GetMapping
    public String showRequestForm(Model model) {
        return "forms/recordar_password/request"; // Ruta a tu plantilla request.html
    }
    @PostMapping("/send")
    public String processRequestForm(@RequestParam("identifier") String identifier, RedirectAttributes redirectAttributes) {
        // Usa el método existente para encontrar al usuario por username o email
        Usuario usuario = usuarioService.iniciarRecordarPassword(identifier);

        if (usuario != null) {
            // Si el usuario existe, envía el correo
            usuarioService.sendPasswordRecoveryEmail(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Si el correo o nombre de usuario existe en nuestra base de datos, recibirás un mensaje con tu contraseña.");
        } else {
            // Para mantener la seguridad mínima (no revelar si el usuario existe o no)
            redirectAttributes.addFlashAttribute("errorMessage", "Si el correo o nombre de usuario existe en nuestra base de datos, recibirás un mensaje con tu contraseña.");
        }
        return "redirect:/recordar_password";
    }
}
