package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    CommandLineRunner init(UserRepository userRepository, OrderRepository orderRepository) {
        return args -> {
            Optional<User> userOp = userRepository.findByEmail("fabio.me@gmail.com");
            User user = userOp.orElseGet(() -> {
                User u = User.builder()
                        .name("Fabio")
                        .email("fabio.me@gmail.com")
                        .build();
                userRepository.save(u);
                LOGGER.info("Saved user: {}", u);
                return u;
            });
            if (user.getOrders() == null || user.getOrders().isEmpty()) {
                Order order = Order.builder()
                    .description("My first order")
                    .dateTime(LocalDateTime.now())
                    .user(user).build();
                orderRepository.save(order);
                LOGGER.info("Saved order: {}", order);
            }
        };
    }

}
