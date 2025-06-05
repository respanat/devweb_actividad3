package com.devweb2025a.actividad3.TestUnits.TestUsuarios;

import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.repositories.UsuarioRepository;
import com.devweb2025a.actividad3.Models.services.UsuarioService;
import com.devweb2025a.actividad3.Models.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class testusuarios {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        Usuario nuevoUsuario = new Usuario(0, "testusu0", "testusu0", "Nuevo Usuario", "testusu@test.com");

        doNothing().when(usuarioRepository).save(any(Usuario.class));

        usuarioService.crearUsuario(nuevoUsuario);

        verify(usuarioRepository, times(1)).save(nuevoUsuario);
    }

    @Test
    void testObtenerUsuarioPorIdExistente() {
        Usuario usuario = new Usuario(1, "user1", "pass1", "Usuario Uno", "user1@user1.com");
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.obtenerUsuarioPorId(1);

        assertNotNull(usuarioEncontrado);
        assertEquals("user1", usuarioEncontrado.getUsername());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerUsuarioPorIdNoExistente() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Usuario usuarioNoEncontrado = usuarioService.obtenerUsuarioPorId(99);

        assertNull(usuarioNoEncontrado);
        verify(usuarioRepository, times(1)).findById(99);
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        Usuario user1 = new Usuario(1, "user1", "pass1", "Usuario Uno", "user1@udc.com");
        Usuario user2 = new Usuario(2, "user2", "pass2", "Usuario Dos", "user2@udc.com");
        List<Usuario> usuariosEsperados = Arrays.asList(user1, user2);

        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        List<Usuario> usuariosObtenidos = usuarioService.obtenerTodosLosUsuarios();

        assertNotNull(usuariosObtenidos);
        assertEquals(2, usuariosObtenidos.size());
        assertEquals("user1", usuariosObtenidos.get(0).getUsername());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testActualizarUsuario() {
        Usuario usuarioAActualizar = new Usuario(1, "user1", "pass1", "Usuario Uno", "user1@udc.com");
        usuarioAActualizar.setNombre("Usuario Uno Actualizado");

        doNothing().when(usuarioRepository).save(any(Usuario.class));

        usuarioService.actualizarUsuario(usuarioAActualizar);

        verify(usuarioRepository, times(1)).save(usuarioAActualizar);
    }

    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioService.eliminarUsuario(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void testObtenerUsuarioPorUsernameExistente() {
        Usuario usuario = new Usuario(1, "user_test", "pass_test", "Usuario Test", "test@udc.com");
        when(usuarioRepository.findByUsername("user_test")).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.obtenerUsuarioPorUsername("user_test");

        assertNotNull(usuarioEncontrado);
        assertEquals("user_test", usuarioEncontrado.getUsername());
        verify(usuarioRepository, times(1)).findByUsername("user_test");
    }

    @Test
    void testObtenerUsuarioPorUsernameNoExistente() {
        when(usuarioRepository.findByUsername("non_existent")).thenReturn(Optional.empty());

        Usuario usuarioNoEncontrado = usuarioService.obtenerUsuarioPorUsername("non_existent");

        assertNull(usuarioNoEncontrado);
        verify(usuarioRepository, times(1)).findByUsername("non_existent");
    }

    @Test
    void testAutenticarUsuarioExitosoPorUsername() {
        Usuario usuario = new Usuario(1, "testuser", "password123", "Test User", "test@udc.com");
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario("testuser", "password123");

        assertNotNull(usuarioAutenticado);
        assertEquals("testuser", usuarioAutenticado.getUsername());
        verify(usuarioRepository, times(1)).findByUsername("testuser");
        verify(usuarioRepository, never()).findByEmail(anyString());
    }

    @Test
    void testAutenticarUsuarioExitosoPorEmail() {
        Usuario usuario = new Usuario(1, "testuser", "password123", "Test User", "test@udc.com");
        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario("test@udc.com", "password123");

        assertNotNull(usuarioAutenticado);
        assertEquals("test@example.com", usuarioAutenticado.getEmail());
        verify(usuarioRepository, times(1)).findByUsername("test@udc.com");
        verify(usuarioRepository, times(1)).findByEmail("test@udc.com");
    }

    @Test
    void testAutenticarUsuarioFallidoPorCredencialesInvalidas() {
        Usuario usuario = new Usuario(1, "testuser", "password123", "Test User", "test@example.com");
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario("testuser", "wrongpassword");

        assertNull(usuarioAutenticado);
        verify(usuarioRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testAutenticarUsuarioNoEncontrado() {
        when(usuarioRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("nonexistent")).thenReturn(Optional.empty());

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario("nonexistent", "anypass");

        assertNull(usuarioAutenticado);
        verify(usuarioRepository, times(1)).findByUsername("nonexistent");
        verify(usuarioRepository, times(1)).findByEmail("nonexistent");
    }


    @Test
    void testIniciarRecordarPasswordEncontradoPorUsername() {
        Usuario usuario = new Usuario(1, "testuser", "password", "Test User", "test@udc.com");
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Usuario encontrado = usuarioService.iniciarRecordarPassword("testuser");

        assertNotNull(encontrado);
        assertEquals("testuser", encontrado.getUsername());
        verify(usuarioRepository, times(1)).findByUsername("testuser");
        verify(usuarioRepository, never()).findByEmail(anyString());
    }

    @Test
    void testIniciarRecordarPasswordEncontradoPorEmail() {
        Usuario usuario = new Usuario(1, "testuser", "password", "Test User", "test@udc.com");
        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.iniciarRecordarPassword("test@udc.com");

        assertNotNull(encontrado);
        assertEquals("test@example.com", encontrado.getEmail());
        verify(usuarioRepository, times(1)).findByUsername("test@udc.com");
        verify(usuarioRepository, times(1)).findByEmail("test@udc.com");
    }

    @Test
    void testIniciarRecordarPasswordNoEncontrado() {
        when(usuarioRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("nonexistent")).thenReturn(Optional.empty());

        Usuario encontrado = usuarioService.iniciarRecordarPassword("nonexistent");

        assertNull(encontrado);
        verify(usuarioRepository, times(1)).findByUsername("nonexistent");
        verify(usuarioRepository, times(1)).findByEmail("nonexistent");
    }

    @Test
    void testSendPasswordRecoveryEmail() {
        Usuario usuario = new Usuario(1, "testuser", "testpass", "Test User", "test@udc.com");

        doNothing().when(emailService).sendSimpleMail(anyString(), anyString(), anyString());

        usuarioService.sendPasswordRecoveryEmail(usuario);

        verify(emailService, times(1)).sendSimpleMail(
            eq("test@udc.com"),
            eq("Recuperación de Contraseña"),
            contains("Tu contraseña es: testpass")
        );
    }
}
