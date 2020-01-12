package com.ynu.sei.auth.repositories;

import com.ynu.sei.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getByUserName(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.password = :password")
    User getByPassword(@Param("password") String password);
}

