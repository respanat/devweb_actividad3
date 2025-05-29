package com.devweb2025a.actividad3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirigirAlLogin() {
        return "redirect:/usuario/login";
    }
}
