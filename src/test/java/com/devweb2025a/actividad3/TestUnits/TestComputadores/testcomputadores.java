package com.devweb2025a.actividad3.TestUnits.TestComputadores;

import com.devweb2025a.actividad3.Models.entities.Computador;
import com.devweb2025a.actividad3.Models.entities.Usuario;
import com.devweb2025a.actividad3.Models.repositories.ComputadorRepository;
import com.devweb2025a.actividad3.Models.services.ComputadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class testcomputadores {

    @Mock
    private ComputadorRepository computadorRepository;

    @InjectMocks
    private ComputadorService computadorService;

    private Usuario testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new Usuario(1, "testuser", "password123", "Usuario Test", "test@example.com");
    }

    @Test
    void testObtenerTodosLosComputadores() {
        Computador comp1 = new Computador(1, "HP", "Laptop", "Intel", 2.5, "DDR4", 16, "SSD", 512, 3, 1, "Dell", 15.6, 950.00, testUser);
        Computador comp2 = new Computador(2, "Dell", "Desktop", "AMD", 3.0, "DDR5", 32, "HDD", 1000, 5, 2, "HP", 24.0, 1200.00, testUser);
        List<Computador> computadoresEsperados = Arrays.asList(comp1, comp2);

        when(computadorRepository.findAll()).thenReturn(computadoresEsperados);

        List<Computador> computadoresObtenidos = computadorService.obtenerTodosLosComputadores();

        assertNotNull(computadoresObtenidos);
        assertEquals(2, computadoresObtenidos.size());
        assertEquals("HP", computadoresObtenidos.get(0).getMarca());
        assertEquals("Dell", computadoresObtenidos.get(1).getMarca());
        assertEquals(16, computadoresObtenidos.get(0).getCapacidadRam());

        verify(computadorRepository, times(1)).findAll();
    }

    @Test
    void testObtenerComputadorPorIdExistente() {
        Computador computador = new Computador(1, "Lenovo", "Laptop", "Intel", 2.0, "DDR4", 8, "SSD", 256, 2, 1, "Lenovo", 14.0, 700.00, testUser);
        when(computadorRepository.findById(1)).thenReturn(Optional.of(computador));

        Computador computadorEncontrado = computadorService.obtenerComputadorPorId(1);

        assertNotNull(computadorEncontrado);
        assertEquals("Lenovo", computadorEncontrado.getMarca());
        verify(computadorRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerComputadorPorIdNoExistente() {
        when(computadorRepository.findById(99)).thenReturn(Optional.empty());

        Computador computadorNoEncontrado = computadorService.obtenerComputadorPorId(99);

        assertNull(computadorNoEncontrado);
        verify(computadorRepository, times(1)).findById(99);
    }

    @Test
    void testCrearComputador() {
        Computador nuevoComputador = new Computador(0, "Acer", "Chromebook", "MediaTek", 1.8, "LPDDR4", 4, "eMMC", 64, 2, 0, "Acer", 11.6, 300.00, testUser);
        
        // Mock del método save de JpaRepository (no devuelve nada para void)
        doNothing().when(computadorRepository).save(any(Computador.class));

        // Llama al método del servicio (que ahora es void)
        computadorService.crearComputador(nuevoComputador);

        // Verifica que el método save fue llamado con el objeto correcto
        verify(computadorRepository, times(1)).save(nuevoComputador);
    }

    @Test
    void testActualizarComputador() {
        Computador computadorExistente = new Computador(1, "HP", "Laptop", "Intel", 2.5, "DDR4", 16, "SSD", 512, 3, 1, "Dell", 15.6, 950.00, testUser);
        computadorExistente.setPrecio(1000.00); // Modificación para la actualización

        doNothing().when(computadorRepository).save(any(Computador.class));

        computadorService.actualizarComputador(computadorExistente);

        verify(computadorRepository, times(1)).save(computadorExistente);
    }

    @Test
    void testEliminarComputador() {
        doNothing().when(computadorRepository).deleteById(1);

        computadorService.eliminarComputador(1);

        verify(computadorRepository, times(1)).deleteById(1);
    }

    @Test
    void testBuscarComputadoresPorCriterio() {
        Computador comp1 = new Computador(1, "HP", "Laptop", "Intel", 2.5, "DDR4", 16, "SSD", 512, 3, 1, "Dell", 15.6, 950.00, testUser);
        Computador comp2 = new Computador(2, "Dell", "Desktop", "AMD", 3.0, "DDR5", 32, "HDD", 1000, 5, 2, "HP", 24.0, 1200.00, testUser);
        List<Computador> resultadosEsperados = Arrays.asList(comp1, comp2);

         when(computadorRepository.findByMarcaContainingIgnoreCaseOrCategoriaContainingIgnoreCaseOrMarcaCpuContainingIgnoreCaseOrTecnologiaRamContainingIgnoreCaseOrTecnologiaDiscoContainingIgnoreCaseOrMarcaMonitorContainingIgnoreCase(
                eq("intel"), eq("intel"), eq("intel"), eq("intel"), eq("intel"), eq("intel")))
                .thenReturn(Arrays.asList(comp1)); // Devuelve solo comp1 para este criterio

        when(computadorRepository.findByMarcaContainingIgnoreCaseOrCategoriaContainingIgnoreCaseOrMarcaCpuContainingIgnoreCaseOrTecnologiaRamContainingIgnoreCaseOrTecnologiaDiscoContainingIgnoreCaseOrMarcaMonitorContainingIgnoreCase(
                eq("laptop"), eq("laptop"), eq("laptop"), eq("laptop"), eq("laptop"), eq("laptop")))
                .thenReturn(Arrays.asList(comp1)); // Devuelve comp1 para "laptop"

        when(computadorRepository.findByMarcaContainingIgnoreCaseOrCategoriaContainingIgnoreCaseOrMarcaCpuContainingIgnoreCaseOrTecnologiaRamContainingIgnoreCaseOrTecnologiaDiscoContainingIgnoreCaseOrMarcaMonitorContainingIgnoreCase(
                eq("inexistente"), eq("inexistente"), eq("inexistente"), eq("inexistente"), eq("inexistente"), eq("inexistente")))
                .thenReturn(Collections.emptyList()); // Nada para un criterio no existente


        List<Computador> resultados1 = computadorService.buscarComputadoresPorCriterio("intel");
        assertNotNull(resultados1);
        assertFalse(resultados1.isEmpty());
        assertEquals(1, resultados1.size());
        assertEquals("Intel", resultados1.get(0).getMarcaCpu());

        List<Computador> resultados2 = computadorService.buscarComputadoresPorCriterio("laptop");
        assertNotNull(resultados2);
        assertFalse(resultados2.isEmpty());
        assertEquals(1, resultados2.size());
        assertEquals("Laptop", resultados2.get(0).getCategoria());

        List<Computador> resultados3 = computadorService.buscarComputadoresPorCriterio("inexistente");
        assertNotNull(resultados3);
        assertTrue(resultados3.isEmpty());

    }
}
