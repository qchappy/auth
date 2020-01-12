package com.ynu.sei.auth.controller;

import com.ynu.sei.auth.domain.User;
import com.ynu.sei.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RestController相当于@Responsebody和@Controller一起使用
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 开启权限验证
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    protected TokenStore tokenStore;

    @Autowired
    private UserRepository userRepository;

    // 返回用户信息
    @RequestMapping(method = RequestMethod.POST)
    public User users(@RequestParam("username") String username) {
        return userRepository.getByUserName(username);
    }

    // 返回所有用户信息
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/revoke")
    public String revokeToken(@RequestParam("refresh_token") String refreshToken) {
        OAuth2RefreshToken oAuth2RefreshToken = tokenStore.readRefreshToken(refreshToken);
        if (oAuth2RefreshToken != null) {
            tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
            tokenStore.removeRefreshToken(oAuth2RefreshToken);
            return "OK, refresh token was removed";
        } else {
            return "Refresh token does not exist";
        }
    }

    // @RequestBody注解可以接收json格式的数据，并将其转换成对应的数据类型
    @PostMapping(value = "/register")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "OK, user is added";
    }
}
