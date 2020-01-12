package com.ynu.sei.auth.repositories;

import com.ynu.sei.auth.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    static final String password = "ynu1234";

    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword(password);
        userRepository.save(user);
    }

    @Test
    public void updateUser() {
        User user = userRepository.getByUserName("王幸");
        user.setUsername("周凌志");
        userRepository.save(user);
    }
}
