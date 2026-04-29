package com.salonSpa.service;

import com.salonSpa.domain.Cita;
import com.salonSpa.domain.Servicio;
import com.salonSpa.repository.CitaRepository;
import com.salonSpa.repository.ServicioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ServicioRepository servicioRepository;

    public CitaService(CitaRepository citaRepository, ServicioRepository servicioRepository) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getHorasDisponibles(String fecha, Integer idServicio) {
        List<String> todasHoras = List.of(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00"
        );
        List<Cita> citasDelDia = citaRepository.findAll().stream()
            .filter(c -> fecha.equals(c.getFechaCita())
                && idServicio.equals(c.getServicio().getIdServicio())
                && !"Cancelada".equals(c.getEstado()))
            .toList();
        List<String> ocupadas = citasDelDia.stream().map(Cita::getHoraCita).toList();
        List<String> disponibles = new ArrayList<>();
        for (String hora : todasHoras) {
            if (!ocupadas.contains(hora)) disponibles.add(hora);
        }
        return disponibles;
    }

    @Transactional
    public Cita guardarCita(String nombre, String telefono, String correo,
            String fechaCita, String horaCita, Integer idServicio) {
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));
        Cita cita = new Cita();
        cita.setNombre(nombre);
        cita.setTelefono(telefono);
        cita.setCorreo(correo);
        cita.setFechaCita(fechaCita);
        cita.setHoraCita(horaCita);
        cita.setServicio(servicio);
        cita.setEstado("Pendiente");
        cita = citaRepository.save(cita);
        String codigo = "JC-" + String.format("%05d", cita.getIdCita());
        cita.setCodigo(codigo);
        return citaRepository.save(cita);
    }

    @Transactional(readOnly = true)
    public List<Cita> buscarCitas(String busqueda) {
        try {
            Optional<Cita> porCodigo = citaRepository.findByCodigo(busqueda.toUpperCase());
            if (porCodigo.isPresent()) return List.of(porCodigo.get());
        } catch (Exception ignored) {}
        return citaRepository.findByTelefono(busqueda);
    }

    @Transactional
    public void cancelarCita(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new IllegalArgumentException("Cita no existe"));
        if ("Pendiente".equals(cita.getEstado())) {
            cita.setEstado("Cancelada");
            citaRepository.save(cita);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Cita> getCita(Integer idCita) {
        return citaRepository.findById(idCita);
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitasPorFecha(String fecha) {
        return citaRepository.findAll().stream()
            .filter(c -> fecha.equals(c.getFechaCita()))
            .sorted((a, b) -> a.getHoraCita().compareTo(b.getHoraCita()))
            .toList();
    }

    @Transactional
    public void cambiarEstado(Integer idCita, String estado) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new IllegalArgumentException("Cita no existe"));
        cita.setEstado(estado);
        citaRepository.save(cita);
    }
}