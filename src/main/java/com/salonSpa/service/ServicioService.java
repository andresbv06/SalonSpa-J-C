package com.salonSpa.service;

import com.salonSpa.domain.Servicio;
import com.salonSpa.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final FirebaseStorageService firebaseStorageService;

    public ServicioService(ServicioRepository servicioRepository, FirebaseStorageService firebaseStorageService) {
        this.servicioRepository = servicioRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Servicio> getServicios() {
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> getServicio(Integer idServicio) {
        return servicioRepository.findById(idServicio);
    }

    @Transactional
    public void guardar(Servicio servicio, MultipartFile archivoImagen) {
        servicio = servicioRepository.save(servicio);
        if (archivoImagen != null && !archivoImagen.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.cargaImagen(
                        archivoImagen, "servicio",
                        servicio.getIdServicio().longValue()
                );
                servicio.setImagenServicio(rutaImagen);
                servicioRepository.save(servicio);
            } catch (Exception ex) {
                throw new RuntimeException("Error subiendo imagen", ex);
            }
        }
    }

    @Transactional
    public void eliminar(Integer idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            throw new IllegalArgumentException("El servicio no existe.");
        }
        try {
            servicioRepository.deleteById(idServicio);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("No se puede eliminar el servicio.", ex);
        }
    }
}