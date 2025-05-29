package com.devweb2025a.actividad3.services;

import com.devweb2025a.actividad3.entities.Computador;
import com.devweb2025a.actividad3.repositories.ComputadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputadorService {

    private final ComputadorRepository computadorRepository;

    @Autowired
    public ComputadorService(ComputadorRepository computadorRepository) {
        this.computadorRepository = computadorRepository;
    }

    public Computador obtenerComputadorPorId(int id) {
        return computadorRepository.findById(id).orElse(null);
    }

    public List<Computador> obtenerTodosLosComputadores() {
        return computadorRepository.findAll();
    }

    public void crearComputador(Computador computador) {
        computadorRepository.save(computador);
    }

    public void actualizarComputador(Computador computador) {
        computadorRepository.save(computador); // Igual que en Usuario, `save` sirve para crear o actualizar
    }

    public void eliminarComputador(int id) {
        computadorRepository.deleteById(id);
    }

    public List<Computador> buscarComputadoresPorCriterio(String criterio) {
        return computadorRepository.findByMarcaContainingIgnoreCaseOrCategoriaContainingIgnoreCaseOrMarcaCpuContainingIgnoreCaseOrTecnologiaRamContainingIgnoreCaseOrTecnologiaDiscoContainingIgnoreCaseOrMarcaMonitorContainingIgnoreCase(
                criterio, criterio, criterio, criterio, criterio, criterio);
    }
}
