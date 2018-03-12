package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "orders")
@Builder
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "user")
/** Unfortunately JPA needs a no args constructor :( */
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private LocalDateTime dateTime;
    @JsonIgnore
    @ManyToOne
    private User user;

}
