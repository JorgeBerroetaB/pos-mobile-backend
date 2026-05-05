package com.yupi.pos.controllers;

import com.yupi.pos.entities.MetodoPago;
import com.yupi.pos.entities.Venta;
import com.yupi.pos.entities.Producto;
import com.yupi.pos.repositories.MetodoPagoRepository;
import com.yupi.pos.repositories.VentaRepository;
import com.yupi.pos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        // 1. Vincular detalles y actualizar stock (permitiendo negativos)
        venta.getDetalles().forEach(detalle -> {
            Producto p = productoRepository.findByCodigoBarras(detalle.getProducto().getCodigoBarras())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getCodigoBarras()));

            // Se resta la cantidad sin bloquear por stock insuficiente
            p.setStock(p.getStock() - detalle.getCantidad());
            productoRepository.save(p);
        });

        // 2. Procesar pagos vinculando con la DB
        venta.getPagos().forEach(pago -> {
            MetodoPago mp = metodoPagoRepository.findByNombre(pago.getMetodoPago().getNombre())
                    .orElseThrow(() -> new RuntimeException("Método de pago no válido: " + pago.getMetodoPago().getNombre()));
            pago.setMetodoPago(mp);
        });

        return ventaRepository.save(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        // Retorna todas las ventas para el historial
        return ventaRepository.findAll();
    }

    @PutMapping("/{id}/cancelar")
    @Transactional
    public ResponseEntity<?> cancelarVenta(@PathVariable Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if (venta.isCancelada()) {
            return ResponseEntity.badRequest().body("La venta ya está cancelada");
        }

        // Revertir el stock de cada producto en la venta
        venta.getDetalles().forEach(detalle -> {
            Producto p = productoRepository.findByCodigoBarras(detalle.getProducto().getCodigoBarras())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado para devolver stock"));

            p.setStock(p.getStock() + detalle.getCantidad());
            productoRepository.save(p);
        });

        // Marcar venta como cancelada
        venta.setCancelada(true);
        ventaRepository.save(venta);

        return ResponseEntity.ok("Venta #" + id + " cancelada exitosamente");
    }
}