package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Cliente;
import com.pablosipac.kinalapp.repository.ClienteRepository;
import com.pablosipac.kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController la sumatoria de un @Controller + @RequestBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador  deben empezar por /clientes
public class ClienteController {

    //Inyectamos el SERVICIO y no el repositorio
    //El controlador debe de tener conexion con el Servicio
    private final IClienteService clienteService;
    //Como buena practica la inyeccion de dependecias debe hacerse
    //por el constructor
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }
    //Responde Peticiones GET
    @GetMapping
    //@Response entity nos permite controlar el codigo HTTp y el cuerpo
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listarTodos();
        //delegamos servicio
        return ResponseEntity.ok(clientes);

    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi){
        //@PathVariable toma el valor de la URL y lo asigna al DPI
        return clienteService.buscarPorDPI(dpi)
                //Si opcional tiene valor devuelve  200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Opcional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }
    //POST crea un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente){
        //@RequestBody: toma el JSON del cuerpo y lo convierte a un objeto de un tipo cliente
        //<?> significa "tipo generico " puede ser un cliente a un string
        try {
        Cliente nuevoCliente = clienteService.guardar(cliente);
        //Intentamos guardar el cliente pero puede lanzar una excepcion
         // de IllegalArgumentException
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        //201 CREATED(mucho mas especifico que el 200 para la creacion de un cliente)
        }catch (IllegalArgumentException e){
            //si hay un error de validacion
            //400 BAD REQUEST con el mensaje de error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi){
        //ResponseEntity<void>
        try{
        if (!clienteService.existePorDPI(dpi)){
            return ResponseEntity.notFound().build();
            //404 No existe
        }
        clienteService.eliminar(dpi);
        return ResponseEntity.noContent().build();
        //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }

    }

    //Actualizar cliente a traves del API
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar (@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if (!clienteService.existePorDPI(dpi)){
                //Verificar si existe antes de actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            //actualizamos el cliente pero esto puede causar una excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
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
    public ResponseEntity<List<Cliente>> listarEstado() {
        List<Cliente> clientes = clienteService.listarPorEstado(1);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }


}
