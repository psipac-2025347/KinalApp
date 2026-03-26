package com.pablosipac.kinalapp.service;

import org.springframework.transaction.annotation.Transactional;
import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.repository.UsuarioRepository;
import com.pablosipac.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
        public List<Usuario> listarTodos() {

        return usuarioRepository.findAll();
    }

    @Override
        public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

        @Override
        @Transactional(readOnly = true)
        public Optional<Usuario> buscarPorcodigoUsuario(String codigoUsuario) {
        return usuarioRepository.findById(Long.parseLong(codigoUsuario));
    }

    @Override
    public Usuario actualizar(String codigoUsuario, Usuario usuario) {
        Long id = Long.parseLong(codigoUsuario);

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado: " + id);
        }

        usuario.setCodigoUsuario(id);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(String codigoUsuario) {
        Long id = Long.parseLong(codigoUsuario);
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado: ");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existePorcodigoUsuario(String codigoUsuario) {
        return usuarioRepository.existsById(Long.parseLong(codigoUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorEstado(int estado) {
        return usuarioRepository.findByEstado(estado);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getCodigoUsuario() == null) {
            throw new IllegalArgumentException("El codigoUsuario es obligatorio");
        }
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }
}