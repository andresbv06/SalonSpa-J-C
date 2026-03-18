package com.salonSpa.repository;

import com.salonSpa.domain.Cita;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByTelefono(String telefono);
    Optional<Cita> findByCodigo(String codigo);
}