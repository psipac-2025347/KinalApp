package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    List<Usuario> findByEstado (Long Estado);

    Optional<Usuario> findByUsername(String username);
}
