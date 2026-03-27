package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Venta;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVentaService {

    List<Venta> listarTodos();

    Optional<Venta> buscarPorCodigoVenta(Long codigoVenta);

    Venta guardar(Venta venta);

    Venta actualizar(Long codigoVenta, Venta venta);

    void eliminar(Long codigoVenta);

    boolean existePorCodigoVenta(Long codigoVenta);

    List<Venta> listarPorEstado(Long estado);

    List<Venta> buscarPorCliente(String dpiCliente);

    List<Venta> buscarPorUsuario(Long codigoUsuario);

    List<Venta> buscarPorFecha(LocalDate fechaVenta);
}