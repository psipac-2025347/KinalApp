package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.DetalleVenta;
import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    List<DetalleVenta> listarTodos();

    Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta);

    DetalleVenta guardar(DetalleVenta detalleVenta);

    DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta);

    void eliminar(Long codigoDetalleVenta);

    boolean existePorCodigo(Long codigoDetalleVenta);


    List<DetalleVenta> buscarPorVenta(Long codigoVenta);

    List<DetalleVenta> buscarPorProducto(Long codigoProducto);
}