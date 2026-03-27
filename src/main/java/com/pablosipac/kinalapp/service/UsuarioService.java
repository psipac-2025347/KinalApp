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
        validarUsuarioNuevo(usuario);
        return usuarioRepository.save(usuario);
    }

        @Override
        @Transactional(readOnly = true)
        public Optional<Usuario> buscarPorcodigoUsuario(Long codigoUsuario) {
        return usuarioRepository.findById((codigoUsuario));
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario) {
        if (!usuarioRepository.existsById(codigoUsuario)) {
            throw new RuntimeException("Usuario no encontrado: ");
        }
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        if (!usuarioRepository.existsById(codigoUsuario)) {
            throw new RuntimeException("Usuario no encontrado: ");
        }
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    public boolean existePorcodigoUsuario(Long codigoUsuario) {

        return usuarioRepository.existsById((codigoUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorEstado(Long estado) {
        return usuarioRepository.findByEstado(estado);
    }

    private void validarUsuarioNuevo(Usuario usuario) {
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

    // Validación para PUT — sí requiere codigoUsuario
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