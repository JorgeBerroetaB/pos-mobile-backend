package com.yupi.pos.repositories;

import com.yupi.pos.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    // Esto nos servirá para que cuando el celular escanee un código,
    // el backend busque si ya existe ese producto.
    Optional<Producto> findByCodigoBarras(String codigoBarras);
}