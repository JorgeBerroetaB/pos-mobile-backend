package com.yupi.pos.controllers;

import com.yupi.pos.entities.Producto;
import com.yupi.pos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public Producto obtenerPorCodigo(@PathVariable String codigo) {
        return productoRepository.findByCodigoBarras(codigo).orElse(null);
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.findByCodigoBarras(producto.getCodigoBarras())
                .map(productoExistente -> {
                    // Actualizamos con los nombres exactos de tu entidad
                    productoExistente.setNombre(producto.getNombre());

                    // Aquí usamos precioVenta que es el que tienes en el @Data
                    productoExistente.setPrecioVenta(producto.getPrecioVenta());

                    // Si quieres que el precio del cel también actualice el costo,
                    // o puedes dejarlo como estaba. Por ahora pongamos venta:
                    productoExistente.setStock(producto.getStock());

                    return productoRepository.save(productoExistente);
                })
                .orElseGet(() -> {
                    return productoRepository.save(producto);
                });
    }
}