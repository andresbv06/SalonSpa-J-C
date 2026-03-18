CREATE DATABASE IF NOT EXISTS salonSpa;

CREATE USER IF NOT EXISTS 'pedro23'@'localhost' IDENTIFIED BY '119080139';

GRANT ALL PRIVILEGES ON salonSpa.* TO 'pedro23'@'localhost';
FLUSH PRIVILEGES;

USE salonSpa;

CREATE TABLE IF NOT EXISTS servicio (
    id_servicio   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(50),
    precio        DOUBLE,
    duracion      INT,
    categoria     VARCHAR(30),
    imagen_servicio VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS cita (
    id_cita       INT AUTO_INCREMENT PRIMARY KEY,
    codigo        VARCHAR(20),
    nombre        VARCHAR(50),
    telefono      VARCHAR(15),
    correo        VARCHAR(50),
    fecha_cita    VARCHAR(10),
    hora_cita     VARCHAR(10),
    estado        VARCHAR(20),
    id_servicio   INT,
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);

INSERT INTO servicio (nombre, precio, duracion, categoria) VALUES
    ('Tratamientos Faciales', 15000, 60, 'Facial'),
    ('Corte & Estilo', 7000, 60, 'Corte'),
    ('Uñas', 8000, 45, 'Uñas'),
    ('Masaje Relajante', 20000, 60, 'Spa');