package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.DetalleVenta;
import com.pablosipac.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByClienteDPICliente(String dpiCliente);


    List<DetalleVenta> findByProductosCodigoProducto(Long codigoUsuario);


    List<DetalleVenta> findByVentasCodigoVenta(LocalDate fechaVenta);
}
