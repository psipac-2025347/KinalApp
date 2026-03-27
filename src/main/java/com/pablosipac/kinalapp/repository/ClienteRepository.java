package com.pablosipac.kinalapp.repository;

import com.pablosipac.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
    List<Cliente> findByEstado (Long Estado);
}
