package com.oleh.chui.config;

import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int PASSWORD_ENCODER_STRENGTH = 8;
    private static final String ADMIN_PATTERN = "/admin/**";
    private static final String MANAGER_PATTERN = "/manager/**";
    private static final String USER_PATTERN = "/user/**";
    private static final String ALL_PATTERN = "/**";
    private static final String URI_ERROR_PARAM = "?error=true";
    private static final String SESSION_COOKIE_NAME = "JSESSIONID";

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ADMIN_PATTERN).hasAuthority(Role.RoleEnum.ADMIN.name())
                .antMatchers(MANAGER_PATTERN).hasAnyAuthority(Role.RoleEnum.MANAGER.name(), Role.RoleEnum.ADMIN.name())
                .antMatchers(USER_PATTERN).hasAuthority(Role.RoleEnum.USER.name())
                .antMatchers(ALL_PATTERN).permitAll()
                .and()
                .formLogin()
                    .loginPage(UriPath.LOGIN)
                    .defaultSuccessUrl(UriPath.CATALOG)
                    .failureUrl(UriPath.LOGIN + URI_ERROR_PARAM)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(UriPath.LOGOUT))
                    .logoutSuccessUrl(UriPath.LOGIN).deleteCookies(SESSION_COOKIE_NAME)
                    .invalidateHttpSession(true);
    }
}
