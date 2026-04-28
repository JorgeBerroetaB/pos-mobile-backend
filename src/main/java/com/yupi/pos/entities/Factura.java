package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroFactura;
    private String rutCliente;
    private String nombreCliente;
    private LocalDateTime fechaEmision;
    private Double totalFacturado;

    @OneToOne
    @JoinColumn(name = "venta_id")
    private Venta venta; // Cada factura pertenece a una venta específica

    @PrePersist
    protected void onCreate() {
        fechaEmision = LocalDateTime.now();
    }
}