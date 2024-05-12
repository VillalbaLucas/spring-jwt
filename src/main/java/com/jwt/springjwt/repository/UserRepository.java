package com.jwt.springjwt.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.springjwt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Serializable> {
    Optional<UserEntity> findByUsername(String username);
}
