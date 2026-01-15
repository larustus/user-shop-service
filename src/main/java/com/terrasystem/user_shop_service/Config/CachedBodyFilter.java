package com.terrasystem.user_shop_service.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CachedBodyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrapped = new ContentCachingRequestWrapper(request);

        // Przepuszczamy dalej — ale interceptor wykona się przed kontrolerem,
        // więc body musi być dostępne już teraz. W ContentCachingRequestWrapper
        // bufor zapełni się po odczycie, więc interceptor nadal może nie zobaczyć.
        // Dlatego w interceptorze, jeśli brak atrybutu, zrobimy fallback: nic, tylko IP.
        filterChain.doFilter(wrapped, response);
    }
}
