package com.pablosipac.kinalapp.service;

import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class UsuarioService implements IUsuarioService  {
    private final UsuarioRepository UsuarioRepository;

    public UsuarioService(UsuarioRepository UsuarioRepository) {
        this.UsuarioRepository = UsuarioRepository;

    }


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return UsuarioRepository.findAll();
    }



    @Override
    public Usuario guardar(Usuario Usuario) {
        return UsuarioRepository.save(Usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorcodigoUsuario(String codigoUsuario) {
        //Busca un Usuario por codigoUsuario
        return UsuarioRepository.findById(codigoUsuario);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Usuario actualizar(String codigoUsuario, Usuario Usuario) {

        if(!UsuarioRepository.existsById((codigoUsuario))){
            throw new RuntimeException("El Usuario no se encontro con el codigoUsuario"+codigoUsuario);

        }
        Usuario.setcodigoUsuario(codigoUsuario);
        //Aseguramos que el codigoUsuario del objeto coincida con el de la URL
        //Por seguridad usamos el codigoUsuario de la URL y no el que viene en el JSON
        validarUsuario(Usuario);
        return UsuarioRepository.save(Usuario);
        /*
         * save() este no solo sirve para guardar sino para actualizar tambien
         * si el dato existe (codigoUsuario) entonces hace UPDATE pero si no existe hace un
         * INSERT pero antes verificamos si existe o no el registro
         */

    }

    @Override
    public void eliminar(String codigoUsuario) {
        //Eliminar un Usuario
        if (!UsuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El Usuario no se encontro con el codigoUsuario"+ codigoUsuario);
        }
        UsuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    public boolean existePorcodigoUsuario(String codigoUsuario) {
        //Verifica si existe un Usuario
        return UsuarioRepository.existsById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorEstado(int estado) {
        return UsuarioRepository.findByEstado(1);
    }


    //Metodo privado( Solo se puede usar dentro de la clase)
    private void validarUsuario (Usuario Usuario){
        /*
         *   Validaciones del negocio: este metodo se usara
         * es algo interno del servicio
         */
        if(Usuario.getcodigoUsuario()== null || Usuario.getcodigoUsuario().trim().isEmpty()){
            //si el codigoUsuario es null o vacio despues de quitar espacios
            //lanza una excepcion con un mensaje
            throw new IllegalArgumentException("El codigoUsuario es un dato obligatorio");
        }

        if(Usuario.getUsername()== null || Usuario.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("El Usuario es un dato obligatorio");
        }

        if(Usuario.getPassword()== null || Usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es un dato obligatorio");
        }

        if(Usuario.getEmail()== null || Usuario.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El Email es un dato obligatorio");
        }

        if(Usuario.getRol()== null || Usuario.getRol().trim().isEmpty()){
            throw new IllegalArgumentException("El Rol es un dato obligatorio");
        }
    }
}
