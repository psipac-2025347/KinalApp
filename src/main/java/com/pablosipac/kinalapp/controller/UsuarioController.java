package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Cliente;
import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorDPI(@PathVariable Long codigoUsuario){
        return usuarioService.buscarPorcodigoUsuario(codigoUsuario)
                //Si opcional tiene valor devuelve  200 OK con el cliente
                .map(ResponseEntity::ok)
                //Si Opcional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario){
        try {
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoUsuario){
        //ResponseEntity<void>
        try{
            if (!usuarioService.existePorcodigoUsuario(codigoUsuario)){
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminar(codigoUsuario);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizar (@PathVariable Long codigoUsuario, @RequestBody Usuario usuario){
        try{
            if (!usuarioService.existePorcodigoUsuario(codigoUsuario)){
                //Verificar si existe antes de actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            //actualizamos el cliente pero esto puede causar una excepcion
            Usuario usuarioActualizado = usuarioService.actualizar(codigoUsuario, usuario);
            return ResponseEntity.ok(usuarioActualizado);
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
    public ResponseEntity<List<Usuario>> listarEstado() {
        List<Usuario> usuarios = usuarioService.listarPorEstado(1);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

}
