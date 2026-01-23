package com.terrasystem.user_shop_service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RateLimitInterceptor(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Limit na konkretne endpointy
        boolean isLogin = method.equals("POST") && path.equals("/auth/login");
        boolean isRegister = method.equals("POST") && path.equals("/auth/register");

        if (!isLogin && !isRegister) return true;

        String ip = extractClientIp(request);

        
        String body = (String) request.getAttribute("CACHED_REQUEST_BODY");
        String username = null;

        if (body != null && !body.isBlank()) {
            try {
                Map<?, ?> json = objectMapper.readValue(body, Map.class);
                Object u = json.get("username");
                if (u != null) username = String.valueOf(u);
            } catch (Exception ignored) {
                
            }
        }

        
        rateLimitService.consumeLoginAttempt(ip, username);

        return true;
    }

    private String extractClientIp(HttpServletRequest request) {
       
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
