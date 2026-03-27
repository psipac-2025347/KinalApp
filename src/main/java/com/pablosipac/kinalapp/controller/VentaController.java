package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Venta;
import com.pablosipac.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

public class VentaController {
    private final IVentaService VentaService;

    public VentaController(IVentaService VentaService) {
        this.VentaService = VentaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = VentaService.listarTodos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorDPI(@PathVariable Long codigoVenta){
        return VentaService.buscarPorCodigoVenta(codigoVenta)
                //Si opcional tiene valor devuelve  200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Opcional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        try {
            Venta nuevaVenta = VentaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoVenta){
        //ResponseEntity<void>
        try{
            if (!VentaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            VentaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar (@PathVariable Long codigoVenta, @RequestBody Venta venta){
        try{
            if (!VentaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            Venta VentaActualizado = VentaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(VentaActualizado);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarEstado() {
        List<Venta> ventas = VentaService.listarPorEstado(1L);

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/clientes/{dpiCliente}")
    public ResponseEntity<List<Venta>> buscarPorDPICliente(@PathVariable String dpiCliente){
        List<Venta> ventas = VentaService.buscarPorCliente(dpiCliente);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);

    }
    @GetMapping("/usuario/{codigoUsuario}")
    public ResponseEntity<List<Venta>> buscarPorUsuario(@PathVariable Long codigoUsuario){
        List<Venta> ventas = VentaService.buscarPorUsuario(codigoUsuario);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/fecha/{fechaVenta}")
    public ResponseEntity<List<Venta>> buscarPorFecha(@PathVariable LocalDate fechaVenta){
        List<Venta> ventas = VentaService.buscarPorFecha(fechaVenta);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

}
