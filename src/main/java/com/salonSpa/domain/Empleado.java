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
@Table(name = "empleado")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(length = 50)
    @Size(max = 50)
    private String nombre;

    @Column(length = 30)
    @Size(max = 30)
    private String puesto;

    @Column(length = 15)
    @Size(max = 15)
    private String telefono;

    @Column(length = 50)
    @Size(max = 50)
    private String correo;
}