package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta,Long> {
    List<Venta> findByEstado(Long estado);


    List<Venta> findByClienteDPICliente(String dpiCliente);


    List<Venta> findByUsuarioCodigoUsuario(Long codigoUsuario);


    List<Venta> findByFechaVenta(LocalDate fechaVenta);
}
