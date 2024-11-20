package com.tromero.guia24.repository;

import com.tromero.guia24.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}