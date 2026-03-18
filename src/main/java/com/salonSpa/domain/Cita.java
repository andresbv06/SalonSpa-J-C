package com.salonSpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(length = 20)
    @Size(max = 20)
    private String codigo;

    @Column(length = 50)
    @Size(max = 50)
    private String nombre;

    @Column(length = 15)
    @Size(max = 15)
    private String telefono;

    @Column(length = 50)
    @Size(max = 50)
    private String correo;

    @Column(name = "fecha_cita", length = 10)
    private String fechaCita;

    @Column(name = "hora_cita", length = 10)
    private String horaCita;

    @Column(length = 20)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;
}