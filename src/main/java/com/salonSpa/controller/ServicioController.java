package com.salonSpa.controller;

import com.salonSpa.domain.Servicio;
import com.salonSpa.service.ServicioService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    private final ServicioService servicioService;
    private final MessageSource messageSource;

    public ServicioController(ServicioService servicioService, MessageSource messageSource) {
        this.servicioService = servicioService;
        this.messageSource = messageSource;
    }

    @GetMapping("/catalogo")
    public String catalogo(Model modelo) {
        modelo.addAttribute("servicios", servicioService.getServicios());
        return "/servicio/catalogo";
    }

    @GetMapping("/listado")
    public String listado(Model modelo) {
        modelo.addAttribute("servicios", servicioService.getServicios());
        return "/servicio/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Servicio servicio, @RequestParam MultipartFile archivoImagen,
            RedirectAttributes redirectAttributes) {
        servicioService.guardar(servicio, archivoImagen);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.guardado", null, Locale.getDefault()));
        return "redirect:/servicio/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idServicio, RedirectAttributes redirectAttributes) {
        String claveMensaje = "todoOk";
        String claveDetalle = "mensaje.eliminado";
        try {
            servicioService.eliminar(idServicio);
        } catch (IllegalArgumentException ex) {
            claveMensaje = "error"; claveDetalle = "servicio.error01";
        } catch (IllegalStateException ex) {
            claveMensaje = "error"; claveDetalle = "servicio.error02";
        } catch (Exception ex) {
            claveMensaje = "error"; claveDetalle = "servicio.error03";
        }
        redirectAttributes.addFlashAttribute(claveMensaje,
                messageSource.getMessage(claveDetalle, null, Locale.getDefault()));
        return "redirect:/servicio/listado";
    }

    @GetMapping("/modificar/{idServicio}")
    public String modificar(@PathVariable Integer idServicio, Model modelo,
            RedirectAttributes redirectAttributes) {
        Optional<Servicio> opt = servicioService.getServicio(idServicio);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("servicio.error01", null, Locale.getDefault()));
            return "redirect:/servicio/listado";
        }
        modelo.addAttribute("servicio", opt.get());
        return "/servicio/modifica";
    }
}