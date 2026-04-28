package com.yupi.pos.controllers;

import com.yupi.pos.entities.Venta;
import com.yupi.pos.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @PostMapping
    public Venta registrarVenta(@RequestBody Venta venta) {
        // Aquí Flutter manda la lista de productos y el total, y nosotros guardamos
        return ventaRepository.save(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}