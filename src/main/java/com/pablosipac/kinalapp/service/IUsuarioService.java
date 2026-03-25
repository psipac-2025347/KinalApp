package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario>listarTodos();

    Optional<Usuario> buscarPorcodigoUsuario(String codigoUsuario);

    Usuario guardar (Usuario usuario);
    
    Usuario actualizar(String codigoUsuario, Usuario usuario);
    
    void eliminar(String codigoUsuario );
    
    boolean existePorcodigoUsuario (String codigoUsuario);

    List<Usuario> listarPorEstado(int estado);

}
