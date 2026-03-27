package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.DetalleVenta;
import com.pablosipac.kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleNuevo(detalleVenta);
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("Detalle no encontrado: " + codigoDetalleVenta);
        }
        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalle(detalleVenta);
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(Long codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("Detalle no encontrado: " + codigoDetalleVenta);
        }
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    public boolean existePorCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorEstado(Long estado) {
        return detalleVentaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> buscarPorVenta(Long codigoVenta) {
        return detalleVentaRepository.findByVentaCodigoVenta(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> buscarPorProducto(Long codigoProducto) {
        return detalleVentaRepository.findByProductoCodigoProducto(codigoProducto);
    }

    // Validación para POST — sin codigoDetalleVenta
    private void validarDetalleNuevo(DetalleVenta detalleVenta) {
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (detalleVenta.getPrecioUnitario() == null) {
            throw new IllegalArgumentException("El precio unitario es obligatorio");
        }
        if (detalleVenta.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero");
        }
        if (detalleVenta.getProducto() == null || detalleVenta.getProducto().getCodigoProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (detalleVenta.getVenta() == null || detalleVenta.getVenta().getCodigoVenta() == null) {
            throw new IllegalArgumentException("La venta es obligatoria");
        }
    }

    // Validación para PUT — con codigoDetalleVenta
    private void validarDetalle(DetalleVenta detalleVenta) {
        if (detalleVenta.getCodigoDetalleVenta() == null) {
            throw new IllegalArgumentException("El codigo del detalle es obligatorio");
        }
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (detalleVenta.getPrecioUnitario() == null) {
            throw new IllegalArgumentException("El precio unitario es obligatorio");
        }
        if (detalleVenta.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero");
        }
        if (detalleVenta.getProducto() == null || detalleVenta.getProducto().getCodigoProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (detalleVenta.getVenta() == null || detalleVenta.getVenta().getCodigoVenta() == null) {
            throw new IllegalArgumentException("La venta es obligatoria");
        }
    }
}