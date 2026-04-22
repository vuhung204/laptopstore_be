package com.laptopshop.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String requestPath = request.getRequestURI();

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                if (jwtUtils.validateToken(token)) {
                    String username = jwtUtils.getUsernameFromToken(token);
                    String roles = jwtUtils.getRolesFromToken(token);

                    List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("[JwtAuthFilter] Authenticated " + username
                            + " for " + request.getMethod() + " " + requestPath
                            + " with roles=" + roles);
                }
            } catch (Exception e) {
                System.out.println("[JwtAuthFilter] Invalid token for "
                        + request.getMethod() + " " + requestPath
                        + ": " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else if (requestPath.startsWith("/api/")) {
            System.out.println("[JwtAuthFilter] Missing Authorization header for "
                    + request.getMethod() + " " + requestPath);
        }

        filterChain.doFilter(request, response);
    }
}
