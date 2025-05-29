package com.devweb2025a.actividad3.controllers;

import com.devweb2025a.actividad3.Models.entities.Computador;
import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.services.ComputadorService;
import com.devweb2025a.actividad3.Models.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/computadores")
public class ComputadorController {

    @Autowired
    private ComputadorService computadorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"/", "/listar_todo"})
    public String listarComputadores(Model model) {
        List<Computador> computadores = computadorService.obtenerTodosLosComputadores();
        model.addAttribute("computadores", computadores);
        return "forms/computadores/listar_todo";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        return "forms/computadores/agregar";
    }

    @PostMapping("/guardar")
    public String guardarComputador(@ModelAttribute Computador computador,
                                    @RequestParam(required = false) Integer usuario_id) {
        computador.setUsuario_id(usuario_id);
        computadorService.crearComputador(computador);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEditar(@RequestParam int id, Model model) {
        model.addAttribute("computador", computadorService.obtenerComputadorPorId(id));
        return "forms/computadores/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarComputador(@ModelAttribute Computador computador,
                                       @RequestParam(required = false) Integer usuario_id) {
        computador.setUsuario_id(usuario_id);
        computadorService.actualizarComputador(computador);
        return "redirect:/usuario/listar_todo";
    }

    @GetMapping("/eliminar")
    public String eliminarComputador(@RequestParam int id) {
        computadorService.eliminarComputador(id);
        return "redirect:/computadores/listar_todo";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBuscar() {
        return "forms/computadores/buscar";
    }

    @PostMapping("/buscar")
    public String buscarComputador(@RequestParam String criterio, Model model) {
        List<Computador> encontrados = computadorService.buscarComputadoresPorCriterio(criterio);
        model.addAttribute("computadoresEncontrados", encontrados);
        model.addAttribute("criterio", criterio);
        return "forms/computadores/buscar_resultado";
    }
}

