package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario>listarTodos();

    Optional<Usuario> buscarPorcodigoUsuario(Long codigoUsuario);

    Usuario guardar (Usuario usuario);
    
    Usuario actualizar(Long codigoUsuario, Usuario usuario);
    
    void eliminar(Long codigoUsuario );
    
    boolean existePorcodigoUsuario (Long codigoUsuario);

    List<Usuario> listarPorEstado(int estado);

}
