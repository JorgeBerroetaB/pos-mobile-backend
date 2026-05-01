package com.yupi.pos.controllers;

import com.yupi.pos.entities.Venta;
import com.yupi.pos.entities.Producto;
import com.yupi.pos.repositories.VentaRepository;
import com.yupi.pos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping
    @Transactional
    public Venta registrarVenta(@RequestBody Venta venta) {
        venta.getDetalles().forEach(detalle -> {
            // Buscamos el producto por su código de barras
            Producto p = productoRepository.findByCodigoBarras(detalle.getProducto().getCodigoBarras())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Verificamos si hay stock suficiente (Opcional pero recomendado)
            if (p.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + p.getNombre());
            }

            // Descontamos y guardamos
            p.setStock(p.getStock() - detalle.getCantidad());
            productoRepository.save(p);
        });

        return ventaRepository.save(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}