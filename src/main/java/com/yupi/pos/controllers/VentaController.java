package com.yupi.pos.controllers;

import com.yupi.pos.entities.MetodoPago;
import com.yupi.pos.entities.Venta;
import com.yupi.pos.entities.Producto;
import com.yupi.pos.repositories.MetodoPagoRepository;
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

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @PostMapping
    @Transactional
    public Venta registrarVenta(@RequestBody Venta venta) {
        // 1. Vincular detalles y descontar stock
        venta.getDetalles().forEach(detalle -> {
            Producto p = productoRepository.findByCodigoBarras(detalle.getProducto().getCodigoBarras())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getCodigoBarras()));

            if (p.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + p.getNombre());
            }

            p.setStock(p.getStock() - detalle.getCantidad());
            productoRepository.save(p);
        });

        // 2. BUSCAR Y ASIGNAR METODOS DE PAGO REALES (Esto arregla el error 500)
        venta.getPagos().forEach(pago -> {
            MetodoPago mp = metodoPagoRepository.findByNombre(pago.getMetodoPago().getNombre())
                    .orElseThrow(() -> new RuntimeException("Método de pago no válido: " + pago.getMetodoPago().getNombre()));
            pago.setMetodoPago(mp);
        });

        return ventaRepository.save(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}