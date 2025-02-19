package com.c3.weebnet_backend.security;

import com.c3.weebnet_backend.config.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain)
        throws ServletException, IOException {

    
    String path = request.getRequestURI();
    if (path.equals("/api/users/register") || path.startsWith("/api/auth")) {
        chain.doFilter(request, response);
        return;
    }

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        chain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);
    String username = null;

    try {
        username = jwtUtil.extractUsername(token);
    } catch (ExpiredJwtException e) {
        logger.error("Token expired", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
        return;
    } catch (MalformedJwtException e) {
        logger.error("Invalid token format", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token format");
        return;
    } catch (JwtException e) {
        logger.error("Invalid token", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        return;
    } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return;
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = new User(username, "", new ArrayList<>());

        if (jwtUtil.validateToken(token, username)) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    chain.doFilter(request, response);
}

}
