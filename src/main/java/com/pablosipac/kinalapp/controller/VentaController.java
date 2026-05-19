package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Venta;
import com.pablosipac.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    private final IVentaService ventaService;

    public VentaController(IVentaService VentaService) {
        this.ventaService = VentaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorDPI(@PathVariable Long codigoVenta){
        return ventaService.buscarPorCodigoVenta(codigoVenta)
                //Si opcional tiene valor devuelve  200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Opcional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoVenta){
        //ResponseEntity<void>
        try{
            if (!ventaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar (@PathVariable Long codigoVenta, @RequestBody Venta venta){
        try{
            if (!ventaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            Venta VentaActualizado = ventaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(VentaActualizado);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarEstado() {
        List<Venta> ventas = ventaService.listarPorEstado(1L);

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/clientes/{dpiCliente}")
    public ResponseEntity<List<Venta>> buscarPorDPICliente(@PathVariable String dpiCliente){
        List<Venta> ventas = ventaService.buscarPorCliente(dpiCliente);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);

    }
    @GetMapping("/usuario/{codigoUsuario}")
    public ResponseEntity<List<Venta>> buscarPorUsuario(@PathVariable Long codigoUsuario){
        List<Venta> ventas = ventaService.buscarPorUsuario(codigoUsuario);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/fecha/{fechaVenta}")
    public ResponseEntity<List<Venta>> buscarPorFecha(@PathVariable LocalDate fechaVenta){
        List<Venta> ventas = ventaService.buscarPorFecha(fechaVenta);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

}
