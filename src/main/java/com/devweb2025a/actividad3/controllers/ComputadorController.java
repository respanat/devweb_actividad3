package com.devweb2025a.actividad3.controllers;

import com.devweb2025a.actividad3.Models.entities.Computador;
import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.services.ComputadorService;
import com.devweb2025a.actividad3.Models.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/computadores")
public class ComputadorController {

    private final ComputadorService computadorService;
    private final UsuarioService usuarioService;

    @Autowired
    public ComputadorController(ComputadorService computadorService, UsuarioService usuarioService) {
        this.computadorService = computadorService;
        this.usuarioService = usuarioService;
    }

    @GetMapping({"", "/", "/listar_todo"})
    public String listarComputadores(Model model) {
        List<Computador> computadores = computadorService.obtenerTodosLosComputadores();
        model.addAttribute("computadores", computadores);
        return "forms/computadores/listar_todo";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("computador", new Computador());
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        return "forms/computadores/agregar";
    }

    @PostMapping("/guardar")
    public String guardarComputador(@ModelAttribute Computador computador, RedirectAttributes redirectAttributes) {
        computadorService.crearComputador(computador);
        redirectAttributes.addFlashAttribute("mensaje", "Computador agregado exitosamente.");
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEditar(@RequestParam("id") int id, Model model) {
        Computador computador = computadorService.obtenerComputadorPorId(id);
        model.addAttribute("computador", computador);
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        return "forms/computadores/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarComputador(@ModelAttribute Computador computador, RedirectAttributes redirectAttributes) {
        computadorService.actualizarComputador(computador);
        redirectAttributes.addFlashAttribute("mensaje", "Computador actualizado correctamente.");
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/eliminar")
    public String eliminarComputador(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        computadorService.eliminarComputador(id);
        redirectAttributes.addFlashAttribute("mensaje", "Computador eliminado.");
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBuscar() {
        return "forms/computadores/buscar";
    }

    @PostMapping("/buscar")
    public String buscarComputador(@RequestParam("criterio") String criterio, Model model) {
        List<Computador> resultados = computadorService.buscarComputadoresPorCriterio(criterio);
        model.addAttribute("computadoresEncontrados", resultados);
        model.addAttribute("criterio", criterio);
        return "forms/computadores/buscar_resultado";
    }
}

