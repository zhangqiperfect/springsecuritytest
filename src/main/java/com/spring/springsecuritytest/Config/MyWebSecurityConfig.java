package com.spring.springsecuritytest.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.springsecuritytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZQ
 * @create 2020-05-14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/user/**")
                .access("hasAnyRole('ADMIN','USER','DBA')")
                .antMatchers("/dba/**")
                .access("hasAnyRole('ADMIN','DBA')")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login_page")
                .loginProcessingUrl("/login")
                .usernameParameter("name")
                .passwordParameter("passwd")
                 .successHandler(new AuthenticationSuccessHandler() {
                     @Override
                     public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                         resp.sendRedirect("/index");
                         Object principal = auth.getPrincipal();
                         resp.setContentType("application/json;charset=utf-8");
                         PrintWriter out = resp.getWriter();
                         resp.setStatus(200);
                         Map<String, Object> map = new HashMap<>();
                         map.put("status", 200);
                         map.put("msg", principal);
                         ObjectMapper om = new ObjectMapper();
                         out.write(om.writeValueAsString(map));
                         out.flush();
                         out.close();


                     }
                 })
                 .failureHandler(new AuthenticationFailureHandler() {
                                     @Override
                                     public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                                         resp.setContentType("application/json;charset=utf-8");
                                         PrintWriter out = resp.getWriter();
                                         resp.setStatus(401);
                                         Map<String, Object> map = new HashMap<>();
                                         map.put("status", 401);
                                         if (e instanceof LockedException) {
                                             map.put("msg", "账户被锁定，登录失败");
                                         } else if (e instanceof AccountExpiredException) {
                                             map.put("msg", "账户过期，登录失败");
                                         }
                                         ObjectMapper om = new ObjectMapper();
                                         out.write(om.writeValueAsString(map));
                                         out.flush();
                                         out.close();
                                     }
                                 }
                 )
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                    }
                }).logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                resp.sendRedirect("/login_page");
            }
        })
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("li4").password("123456").roles("ADMIN")
//                .and()
//                .withUser("wang2").password("123456").roles("USER")
//                .and()
//                .withUser("z3").password("123456").roles("DBA");
        auth.userDetailsService(userService);
    }

}
