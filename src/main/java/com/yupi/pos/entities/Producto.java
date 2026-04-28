package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data // Magia de Lombok para generar Getters y Setters automáticamente
@Entity // Le dice a Spring Boot que esto será una tabla en la base de datos
@Table(name = "productos")
public class Producto {

    @Id // Esta será nuestra llave principal
    @Column(name = "codigo_barras", unique = true, nullable = false)
    private String codigoBarras; // ¡Clave para escanear con el celular!

    @Column(nullable = false)
    private String nombre;

    private Double precioCosto;

    @Column(nullable = false)
    private Double precioVenta;

    @Column(nullable = false)
    private Integer stock;
}