package com.salonSpa.controller;

import com.salonSpa.domain.Cita;
import com.salonSpa.service.CitaService;
import com.salonSpa.service.ServicioService;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cita")
public class CitaController {

    private final CitaService citaService;
    private final ServicioService servicioService;
    private final MessageSource messageSource;

    public CitaController(CitaService citaService, ServicioService servicioService,
            MessageSource messageSource) {
        this.citaService = citaService;
        this.servicioService = servicioService;
        this.messageSource = messageSource;
    }

    @GetMapping("/reservar")
    public String reservar(@RequestParam(required = false) Integer idServicio, Model modelo) {
        modelo.addAttribute("servicios", servicioService.getServicios());
        modelo.addAttribute("idServicioSeleccionado", idServicio);
        return "/cita/reservar";
    }

    @GetMapping("/horarios")
    public String horarios(@RequestParam Integer idServicio,
            @RequestParam String nombre,
            @RequestParam String telefono,
            @RequestParam(required = false, defaultValue = "") String correo,
            @RequestParam String fechaCita, Model modelo) {
        List<String> horas = citaService.getHorasDisponibles(fechaCita, idServicio);
        modelo.addAttribute("horas", horas);
        modelo.addAttribute("idServicio", idServicio);
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("telefono", telefono);
        modelo.addAttribute("correo", correo);
        modelo.addAttribute("fechaCita", fechaCita);
        modelo.addAttribute("servicio", servicioService.getServicio(idServicio).orElse(null));
        return "/cita/horarios";
    }

    @PostMapping("/confirmar")
    public String confirmar(@RequestParam Integer idServicio,
            @RequestParam String nombre,
            @RequestParam String telefono,
            @RequestParam(required = false, defaultValue = "") String correo,
            @RequestParam String fechaCita,
            @RequestParam String horaCita, Model modelo) {
        Cita cita = citaService.guardarCita(nombre, telefono, correo, fechaCita, horaCita, idServicio);
        modelo.addAttribute("cita", cita);
        return "/cita/confirmacion";
    }

    @GetMapping("/mis-citas")
    public String misCitas(@RequestParam(required = false) String busqueda, Model modelo) {
        if (busqueda != null && !busqueda.isBlank()) {
            modelo.addAttribute("citas", citaService.buscarCitas(busqueda));
            modelo.addAttribute("busqueda", busqueda);
        }
        return "/cita/mis-citas";
    }

    @PostMapping("/cancelar")
    public String cancelar(@RequestParam Integer idCita,
            @RequestParam String busqueda, RedirectAttributes redirectAttributes) {
        citaService.cancelarCita(idCita);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.cancelado", null, Locale.getDefault()));
        return "redirect:/cita/mis-citas?busqueda=" + busqueda;
    }
}