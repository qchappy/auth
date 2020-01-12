package com.ynu.sei.auth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import javax.annotation.Resource;

@Configuration
@EnableOAuth2Client
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private OAuth2ClientContext oauth2ClientContext;

    // 自动注入UserDetailsService
    @Autowired
    private UserDetailsServiceImpl userDetailService;

    /**
     * @Bean是一个方法级别上的注解，主要是用在@Configuration注解的类中，也可以通用在@Component注解的类中 在Spring中@Bean注解用于告诉方法，产生一个Bean对象，然后将这个Bean对象交给Spring管理。
     * 所产生该Bean的这个方法Spring只会调用一次（即：产生一个该方法的Bean对象），随后Spring会将这个Bean对象放到自己的IOC容器中。
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        return authProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 用户认证服务（用户名+密码）
     * @param auth 认证
     * @throws Exception 相关异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}

