package com.devweb2025a.actividad3.repositories;

import com.devweb2025a.actividad3.entities.Computador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputadorRepository extends JpaRepository<Computador, Integer> {
    List<Computador> findByMarcaContainingIgnoreCase(String criterio);
    List<Computador> findByCategoriaContainingIgnoreCase(String criterio);
}

