package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Cliente;
import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.entity.Venta;
import com.pablosipac.kinalapp.repository.ClienteRepository;
import com.pablosipac.kinalapp.repository.UsuarioRepository;
import com.pablosipac.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository, UsuarioRepository usuarioRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
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
        // Validaciones
        if (venta.getUsuario() == null || venta.getUsuario().getCodigoUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if (venta.getCliente() == null || venta.getCliente().getDPICliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
        if (venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        // Buscar entidades reales en BD para resolver las llaves foraneas
        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getCodigoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        venta.setUsuario(usuario);

        Cliente cliente = clienteRepository.findById(venta.getCliente().getDPICliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        venta.setCliente(cliente);

        return ventaRepository.save(venta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada: " + codigoVenta);
        }

        // Validaciones
        if (venta.getUsuario() == null || venta.getUsuario().getCodigoUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if (venta.getCliente() == null || venta.getCliente().getDPICliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
        if (venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        // Buscar entidades reales en BD para resolver las llaves foraneas
        Usuario usuario = usuarioRepository.findById(venta.getUsuario().getCodigoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        venta.setUsuario(usuario);

        Cliente cliente = clienteRepository.findById(venta.getCliente().getDPICliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        venta.setCliente(cliente);

        venta.setCodigoVenta(codigoVenta);
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

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFecha(LocalDate fechaVenta) {
        return ventaRepository.findByFechaVenta(fechaVenta);
    }
}