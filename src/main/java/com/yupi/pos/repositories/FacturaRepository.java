package com.yupi.pos.repositories;

import com.yupi.pos.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Para buscar facturas por el RUT del cliente
    java.util.List<Factura> findByRutCliente(String rutCliente);
}