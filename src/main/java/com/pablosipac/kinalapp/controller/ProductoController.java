package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Producto;
import com.pablosipac.kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> buscarPorCodigoProducto(@PathVariable Long codigoProducto){
        return productoService.buscarPorcodigoProducto(codigoProducto)
                //Si opcional tiene valor devuelve  200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Opcional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto){
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED); //Se creo exitosamente
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());//Te manda badRequest si no se crea
        }
    }

    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoProducto){
        try{
            if (!productoService.existePorcodigoProducto(codigoProducto)){
                return ResponseEntity.notFound().build();
            }
            productoService.eliminar(codigoProducto);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{codigoProducto}")
    public ResponseEntity<?> actualizar (@PathVariable Long codigoProducto, @RequestBody Producto producto){
        try{
            if (!productoService.existePorcodigoProducto(codigoProducto)){
                return ResponseEntity.notFound().build();
            }
            //actualizamos el cliente pero esto puede causar una excepcion
            Producto productoActualizado = productoService.actualizar(codigoProducto, producto);
            return ResponseEntity.ok(productoActualizado);
            //200 ok con el cliente ya actualizado
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            //Posiblemente cualquier otro error como: cliente no encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarEstado() {
        List<Producto> productos = productoService.listarPorEstado(1L);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
}
