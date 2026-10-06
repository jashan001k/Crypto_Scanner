package com.example.Crypto_Scanner.rate_limit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            filterChain.doFilter(request, response);
            return;
        }

        String userEmail = authentication.getName();

        boolean allowed =
                rateLimitService.isAllowed(userEmail);

        if (!allowed) {

            response.setStatus(429);

            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Too many requests. Please try again later.\"}"
            );

            return;
        }

        filterChain.doFilter(request, response);
    }
}