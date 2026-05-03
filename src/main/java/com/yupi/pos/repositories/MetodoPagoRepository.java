package com.yupi.pos.repositories;

import com.yupi.pos.entities.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Importante añadir esto

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {

    // Este método permite buscar el objeto MetodoPago usando el nombre ("Efectivo", etc.)
    Optional<MetodoPago> findByNombre(String nombre);

}