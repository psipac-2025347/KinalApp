package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.Producto;
import com.pablosipac.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByEstado (Long Estado);
}
