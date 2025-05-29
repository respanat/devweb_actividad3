package com.devweb2025a.actividad3.controllers;

import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.services.UsuarioService;
import com.devweb2025a.actividad3.Models.services.ComputadorService;
import com.devweb2025a.actividad3.Models.entities.Computador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ComputadorService computadorService;

    @GetMapping({"", "/", "/listar_todo"})
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<Computador> computadores = computadorService.obtenerTodosLosComputadores();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("computadores", computadores);
        return "usuarios/listar_todo";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/agregar";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.crearUsuario(usuario);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEditar(@RequestParam("id") int id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/eliminar")
    public String eliminarUsuario(@RequestParam("id") int id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBuscar() {
        return "usuarios/buscar";
    }

    @PostMapping("/buscar")
    public String buscarUsuario(@RequestParam("criterio") String criterio, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(criterio);
        model.addAttribute("usuarioEncontrado", usuario);
        return "usuarios/buscar_resultado";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String adminLogin,
                                 HttpSession session,
                                 Model model) {
        if ("true".equals(adminLogin) && "admin".equals(username) && "admin".equals(password)) {
            return "redirect:/usuario/listar_todo";
        }

        Usuario usuario = usuarioService.autenticarUsuario(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/usuario/listar";
        } else {
            model.addAttribute("errorMessage", "Credenciales inválidas");
            return "usuarios/login";
        }
    }

    @GetMapping("/recordar_password")
    public String mostrarFormularioRecordarPassword() {
        return "usuarios/recordar_password";
    }

    @PostMapping("/recordar_password")
    public String procesarRecordarPassword(@RequestParam("email") String email, Model model) {
        // Aquí implementas la lógica de envío de correo
        model.addAttribute("mensaje", "Correo enviado si el usuario existe.");
        return "usuarios/recordar_password";
    }

    @GetMapping("/listar")
    public String mostrarDetallesUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/listar";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
}


