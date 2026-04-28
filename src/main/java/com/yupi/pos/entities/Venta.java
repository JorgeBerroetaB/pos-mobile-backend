package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Double total;

    // Relación: Una venta tiene muchos detalles (productos vendidos)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "venta_id")
    private List<DetalleVenta> detalles;

    @PrePersist
    protected void onCreate() {
        fecha = LocalDateTime.now(); // Se pone la hora automática al vender
    }
}