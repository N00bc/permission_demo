package com.cyn.permission_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

/**
 * @author Godc
 * @description:
 * @date 2023/1/29 10:13
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

//    @Bean
//    PermissionEvaluator permissionEvaluator() {
//        return new PermissionEvaluator() {
//            @Override
//            public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
//                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//                for (GrantedAuthority authority : authorities) {
//                    if (antPathMatcher.match(authority.getAuthority(), (String) permission)) return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
//                return false;
//            }
//        };
//    }

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("zs")
                .password("{noop}123")
                .roles("admin")
//                .authorities("sys:user:add", "sys:user:delete")
                .authorities("sys:user:*")
                .build());
        return manager;
    }

    @Bean
    CustomMethodSecurityExpressionHandler customMethodSecurityExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
        return httpSecurity.build();
    }
}
