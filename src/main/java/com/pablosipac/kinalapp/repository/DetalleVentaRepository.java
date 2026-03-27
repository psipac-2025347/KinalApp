package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {


    List<DetalleVenta> findByVentaCodigoVenta(Long codigoVenta);


    List<DetalleVenta> findByProductoCodigoProducto(Long codigoProducto);

    List<DetalleVenta> findByEstado(Long estado);
}