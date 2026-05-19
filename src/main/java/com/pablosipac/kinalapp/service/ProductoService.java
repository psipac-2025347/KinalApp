package com.pablosipac.kinalapp.service;

import org.springframework.transaction.annotation.Transactional;
import com.pablosipac.kinalapp.entity.Producto;
import com.pablosipac.kinalapp.repository.ProductoRepository;
import com.pablosipac.kinalapp.service.IProductoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService {


    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Se listan todos los productos creados existentes y en estado 0
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {

        return productoRepository.findAll();
    }

    //Se guarda Producto
    @Override
    public Producto guardar(Producto producto) {
        validarProductoNuevo(producto);
        return productoRepository.save(producto);
    }
    //Se busca por Id en este caso por CodigoProducto
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorcodigoProducto(Long codigoProducto) {
        return productoRepository.findById((codigoProducto));
    }

    //Metodo para actualizar CodigoProducto
    @Override
    public Producto actualizar(Long codigoProducto, Producto producto) {

        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("Producto no encontrado: ");
        }

        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    //Metodo par eliminar CodigoProducto
    @Override
    public void eliminar(Long codigoProducto) {
        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("Producto no encontrado: ");
        }
        productoRepository.deleteById(codigoProducto);
    }

    //Se revisa si existe CodigoProducto
    @Override
    public boolean existePorcodigoProducto(Long codigoProducto) {
        return productoRepository.existsById((codigoProducto));
    }

    //Se listan los estados de producto
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarPorEstado(Long estado) {
        return productoRepository.findByEstado(estado);
    }

    //Validacion para post
    private void validarProductoNuevo(Producto producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (producto.getStock() == null) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }

    //Validacion para put
    private void validarProducto(Producto producto) {
        if (producto.getCodigoProducto() == null) {
            throw new IllegalArgumentException("El codigoProducto es obligatorio");
        }
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (producto.getStock() == null) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}