package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
    UserRepository userRepository;

	@Test
	public void findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        logger.info("Users={}", users);
        Assert.assertTrue(users.iterator().hasNext());
	}

}
