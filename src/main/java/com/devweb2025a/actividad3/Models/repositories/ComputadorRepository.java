package com.devweb2025a.actividad3.Models.repositories;

import com.devweb2025a.actividad3.Models.entities.Computador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputadorRepository extends JpaRepository<Computador, Integer> {
    List<Computador> findByMarcaContainingIgnoreCase(String criterio);
    List<Computador> findByCategoriaContainingIgnoreCase(String criterio);

	public List<Computador> findByMarcaContainingIgnoreCaseOrCategoriaContainingIgnoreCaseOrMarcaCpuContainingIgnoreCaseOrTecnologiaRamContainingIgnoreCaseOrTecnologiaDiscoContainingIgnoreCaseOrMarcaMonitorContainingIgnoreCase(String criterio, String criterio0, String criterio1, String criterio2, String criterio3, String criterio4);
}

