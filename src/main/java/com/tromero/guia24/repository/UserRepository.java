package com.tromero.guia24.repository;

import com.tromero.guia24.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
