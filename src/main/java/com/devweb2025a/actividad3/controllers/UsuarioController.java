package com.devweb2025a.actividad3.controllers;

import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.entities.Computador;
import com.devweb2025a.actividad3.Models.services.UsuarioService;
import com.devweb2025a.actividad3.Models.services.ComputadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ComputadorService computadorService;

    @GetMapping({"/", "/listar_todo"})
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("computadores", computadorService.obtenerTodosLosComputadores());
        return "forms/usuarios/listar_todo";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar() {
        return "forms/usuarios/agregar";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@RequestParam String username, @RequestParam String password,
                                  @RequestParam String nombre, @RequestParam String email) {
        Usuario nuevoUsuario = new Usuario(0, username, password, nombre, email);
        usuarioService.crearUsuario(nuevoUsuario);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEditar(@RequestParam int id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerUsuarioPorId(id));
        return "forms/usuarios/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(@RequestParam int id, @RequestParam String username,
                                    @RequestParam String password, @RequestParam String nombre,
                                    @RequestParam String email) {
        Usuario usuario = new Usuario(id, username, password, nombre, email);
        usuarioService.actualizarUsuario(usuario);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/eliminar")
    public String eliminarUsuario(@RequestParam int id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBuscar() {
        return "forms/usuarios/buscar";
    }

    @PostMapping("/buscar")
    public String buscarUsuario(@RequestParam String criterio, Model model) {
        model.addAttribute("usuarioEncontrado", usuarioService.obtenerUsuarioPorUsername(criterio));
        model.addAttribute("criterio", criterio);
        return "forms/usuarios/buscar_resultado";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "forms/usuarios/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password,
                                 @RequestParam(required = false) String adminLogin, HttpSession session, Model model) {

        if ("true".equals(adminLogin) && "admin".equals(username) && "admin".equals(password)) {
            return "redirect:/usuario/listar_todo";
        }

        Usuario usuario = usuarioService.autenticarUsuario(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/usuario/listar";
        } else {
            model.addAttribute("errorMessage", "Credenciales inválidas");
            return "forms/usuarios/login";
        }
    }

    @GetMapping("/listar")
    public String mostrarDetallesUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/usuario/login";
        model.addAttribute("usuario", usuario);
        return "forms/usuarios/listar"; // Asegúrate que esta vista exista
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }

    @GetMapping("/recordar_password")
    public String mostrarFormularioRecordarPassword() {
        return "forms/usuarios/recordar_password";
    }

    @PostMapping("/recordar_password")
    public String procesarRecordarPassword(@RequestParam String email, Model model) {
        // Lógica pendiente
        model.addAttribute("mensaje", "Correo enviado a " + email);
        return "forms/usuarios/recordar_password";
    }
}

