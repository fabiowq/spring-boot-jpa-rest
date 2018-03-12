package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return getUserOrElseThrow(userId);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody  User user) {
        userRepository.save(user);
        return ResponseEntity.created(location(user.getId())).body(user);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        User user = getUserOrElseThrow(userId);
        orderRepository.deleteAllByUser(user);
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/order")
    public Set<Order> getOrders(@PathVariable long userId) {
        User user = getUserOrElseThrow(userId);
        return orderRepository.findAllByUser(user);
    }

    @GetMapping("/{userId}/order/{orderId}")
    public Order getOrder(@PathVariable long userId, @PathVariable long orderId) {
        getUserOrElseThrow(userId);
        return getOrderOrElseThrow(orderId);
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<Order> saveOrder(@PathVariable long userId, @RequestBody Order order) {
        User user = getUserOrElseThrow(userId);
        order.setUser(user);
        orderRepository.save(order);
        return ResponseEntity.created(location(order.getId())).body(order);
    }

    @DeleteMapping("/{userId}/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable long userId, @PathVariable long orderId) {
        getUserOrElseThrow(userId);
        Order order = getOrderOrElseThrow(orderId);
        orderRepository.delete(order);
        return ResponseEntity.noContent().build();
    }

    private User getUserOrElseThrow(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    }

    private Order getOrderOrElseThrow(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
    }

    private URI location(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

}
