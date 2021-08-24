package com.nhom6.server_nhom6.common.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    public AuthFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null || tokenHeader.isEmpty() || !tokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }


        String token = tokenHeader.replace("Bearer ", "");
        String email = null;
        try {
            email = Jwts.parser().setSigningKey("ABC_EGH").parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                response.sendError(401, "Hết hạn token");
            }
            return;
        }

        // Get user information (UserDetailDto)
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            log.info("****************************");
            log.info("Error: " + e.getMessage());
            response.sendError(401, e.getMessage());
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        // Add user information to context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);

    }
}
