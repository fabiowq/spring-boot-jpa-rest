package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findAllByUser(User user);
    void deleteAllByUser(User user);

}
