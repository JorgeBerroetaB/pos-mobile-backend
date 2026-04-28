package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "metodos_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // Ejemplo: "Efectivo", "Tarjeta", "Transferencia"
}