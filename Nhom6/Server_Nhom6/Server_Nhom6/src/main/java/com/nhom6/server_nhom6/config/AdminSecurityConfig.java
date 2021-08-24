package com.nhom6.server_nhom6.config;


import com.nhom6.server_nhom6.common.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        private UserDetailsService userDetailsService;

    public AdminSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors();

       http.csrf().disable()
               .authorizeRequests()
               .anyRequest()
               .permitAll();

//        http.csrf().disable()
//                .antMatcher("/api/**")
//                .authorizeRequests()
//                .antMatchers("/api/v1/nhom6/chitiethoadon")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/chitietphitrehen")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/ctpdt")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/dia")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/hoadon")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/khachhang")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/loaidia")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/phieudattruoc")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/phitrehen")
//                .hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/nhom6/tieude")
//                .hasAnyRole("ADMIN")
//                .anyRequest()
//                .authenticated();



      http.addFilter(new AuthFilter(authenticationManager(), userDetailsService));

      // No use session
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
