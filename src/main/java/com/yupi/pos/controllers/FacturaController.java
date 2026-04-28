package com.yupi.pos.controllers;

import com.yupi.pos.entities.Factura;
import com.yupi.pos.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping
    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    @PostMapping
    public Factura crearFactura(@RequestBody Factura factura) {
        return facturaRepository.save(factura);
    }
}