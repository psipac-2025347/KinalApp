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


    private final ProductoRepository ProductoRepository;

    public ProductoService(ProductoRepository ProductoRepository) {
        this.ProductoRepository = ProductoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {

        return ProductoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        return ProductoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorcodigoProducto(Long codigoProducto) {
        return ProductoRepository.findById((codigoProducto));
    }

    @Override
    public Producto actualizar(Long codigoProducto, Producto producto) {

        if (!ProductoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("Producto no encontrado: ");
        }

        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return ProductoRepository.save(producto);
    }

    @Override
    public void eliminar(Long codigoProducto) {
        if (!ProductoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("Producto no encontrado: ");
        }
        ProductoRepository.deleteById(codigoProducto);
    }



    @Override
    public boolean existePorcodigoProducto(Long codigoProducto) {
        return ProductoRepository.existsById((codigoProducto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarPorEstado(Long estado) {
        return ProductoRepository.findByEstado(estado);
    }

    private void validarProducto(Producto producto) {
        if (producto.getCodigoProducto() == null) {
            throw new IllegalArgumentException("El codigoProducto es obligatorio");
        }
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (producto.getPrecio() == null) {
            throw new IllegalArgumentException("El precio  es obligatorio");
        }
        if (producto.getPrecio().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("El precio  debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El Stock no puede ser negativo");
        }
    }
}
