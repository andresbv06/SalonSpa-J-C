package com.salonSpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(length = 50)
    @Size(max = 50)
    private String nombre;

    private Double precio;

    private Integer duracion;

    @Column(length = 30)
    @Size(max = 30)
    private String categoria;

    @Column(name = "imagen_servicio", length = 1024)
    @Size(max = 1024)
    private String imagenServicio;
}
