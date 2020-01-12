package com.ynu.sei.auth.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@EnableOAuth2Client
@EnableAuthorizationServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(AuthorizationServerConfiguration.RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                // 防止CSRF(跨站请求伪造)配置
                .csrf().disable()
                // httpBasic().disable()
                // .anonymous().disable() // 取消匿名访问

                // .and()
                .antMatcher("/api/**")
                .authorizeRequests()
                // .anyRequest().authenticated()

                // 不用身份认证可以访问
                .antMatchers("oauth/token", "/", "/login", "/webjars/**", "/api/images/**", "/api/user/register", "/api/avatar", "/api/notes").permitAll()
                // 其它的请求要求必须有身份认证
                .anyRequest().authenticated()

                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}