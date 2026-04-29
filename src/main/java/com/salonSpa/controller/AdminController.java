package com.salonSpa.controller;

import com.salonSpa.service.CitaService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CitaService citaService;

    public AdminController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/agenda")
    public String agenda(@RequestParam(required = false) String fecha, Model modelo) {
        if (fecha == null || fecha.isBlank()) {
            fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        modelo.addAttribute("citas", citaService.getCitasPorFecha(fecha));
        modelo.addAttribute("fecha", fecha);
        return "/admin/agenda";
    }

    @PostMapping("/cambiarEstado")
    public String cambiarEstado(@RequestParam Integer idCita,
            @RequestParam String estado,
            @RequestParam String fecha,
            RedirectAttributes redirectAttributes) {
        citaService.cambiarEstado(idCita, estado);
        redirectAttributes.addFlashAttribute("todoOk", "Estado actualizado correctamente.");
        return "redirect:/admin/agenda?fecha=" + fecha;
    }
}
