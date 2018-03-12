package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(@Param("name") String name);

    List<User> findByNameContaining(@Param("name") String name);

    Optional<User> findByEmail(@Param("email") String email);

    List<User> findByEmailContaining(@Param("email") String email);

}
