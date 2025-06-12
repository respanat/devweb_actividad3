package com.devweb2025a.actividad3.Models.services;

import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    //@Autowired
    //private EmailService emailService; // Inyecta el EmailService

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }
	
    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario); // `save` sirve tanto para crear como actualizar
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public Usuario autenticarUsuario(String usernameOrEmail, String password) {
        Usuario usuario = usuarioRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> usuarioRepository.findByEmail(usernameOrEmail).orElse(null));

        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null;
    }

    public Usuario iniciarRecordarPassword(String identifier) {
        return usuarioRepository.findByUsername(identifier)
                .orElseGet(() -> usuarioRepository.findByEmail(identifier).orElse(null));
    }

    // Método para enviar el correo de recuperación con la contraseña
    public void sendPasswordRecoveryEmail(Usuario usuario) {
        String subject = "Recuperación de Contraseña";
        String body = "Hola " + usuario.getNombre() + ",\n\n"
                    + "Tu contraseña es: " + usuario.getPassword() + "\n\n" // Mostrando la contraseña directamente
                    + "Por favor, no compartas esta información con nadie.\n\n"
                    + "Saludos,\nTu Equipo de Soporte";

        emailService.sendSimpleMail(usuario.getEmail(), subject, body);
    }
}



