package com.security.myapp.security_config;

import com.security.myapp.service.CustomUserDetailsService;
import com.security.myapp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final JwtService jwtService;
    final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService){
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {


            //read the token from the header
            String bearer_token = request.getHeader("Authorization");

//            extract the token by removing the word `bearer`
            String token = bearer_token.substring(7);

//            extracting the username from the token
            String username = jwtService.getuserName(token);
            //get the user detail from the db by username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //valiadate the token against the username
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

            //set the details if valid is true
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

//                after all validation set the userdetails in securitycontext
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//            if all validation done.move the request to the controller
            filterChain.doFilter(request, response);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
