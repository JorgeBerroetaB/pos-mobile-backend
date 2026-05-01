package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "venta_pagos")
public class VentaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago;
}