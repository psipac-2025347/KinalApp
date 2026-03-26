package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarTodos();

    Optional<Producto> buscarPorcodigoProducto(Long codigoProducto);

    Producto guardar (Producto producto);

    Producto actualizar(Long codigoUsuario, Producto producto);

    void eliminar(Long codigoProducto );

    boolean existePorcodigoProducto (Long codigoProducto);

    List<Producto> listarPorEstado(int estado);

}
