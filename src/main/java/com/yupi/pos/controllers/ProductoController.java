package com.yupi.pos.controllers;

import com.yupi.pos.entities.Producto;
import com.yupi.pos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Para que Flutter no tenga problemas de permisos
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. Listar todos los productos
    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // 2. Buscar producto por código de barras
    @GetMapping("/{codigo}")
    public Producto obtenerPorCodigo(@PathVariable String codigo) { // <--- Así debe quedar, sin el símbolo raro
        return productoRepository.findByCodigoBarras(codigo)
                .orElse(null);
    }

    // 3. Guardar o actualizar producto
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }
}