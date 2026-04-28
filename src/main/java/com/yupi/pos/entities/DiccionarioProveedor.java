package com.yupi.pos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "diccionario_proveedores")
public class DiccionarioProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreEmpresa;

    private String contacto;
    private String telefono;
}