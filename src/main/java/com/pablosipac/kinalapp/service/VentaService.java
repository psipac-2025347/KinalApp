package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Venta;
import com.pablosipac.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVentaNueva(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada: " + codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada: " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    public boolean existePorCodigoVenta(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorEstado(Long estado) {
        return ventaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorCliente(String dpiCliente) {
        return ventaRepository.findByClienteDPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorUsuario(Long codigoUsuario) {
        return ventaRepository.findByUsuarioCodigoUsuario(codigoUsuario);
    }

    //Se buscar por la llave foranea
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFecha(LocalDate fechaVenta) {
        return ventaRepository.findByFechaVenta(fechaVenta);
    }

    // Validación para POST
    private void validarVentaNueva(Venta venta) {
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
        if (venta.getTotal() == null) {
            throw new IllegalArgumentException("El total es obligatorio");
        }
        if (venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
    }

    // Validación para PUT
    private void validarVenta(Venta venta) {
        if (venta.getCodigoVenta() == null) {
            throw new IllegalArgumentException("El codigoVenta es obligatorio");
        }
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
        if (venta.getTotal() == null) {
            throw new IllegalArgumentException("El total es obligatorio");
        }
        if (venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
    }
}