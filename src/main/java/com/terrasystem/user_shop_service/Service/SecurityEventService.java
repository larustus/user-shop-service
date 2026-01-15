package com.terrasystem.user_shop_service.Service;

import com.terrasystem.user_shop_service.Entity.SecurityEvent;
import com.terrasystem.user_shop_service.Repository.SecurityEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class SecurityEventService {

    private final SecurityEventRepository repo;

    public SecurityEventService(SecurityEventRepository repo) {
        this.repo = repo;
    }

    public void log(HttpServletRequest request,
                    String eventType,
                    String outcome,
                    Integer userId,
                    String username,
                    String details) {

        SecurityEvent ev = new SecurityEvent();
        ev.setEventType(eventType);
        ev.setOutcome(outcome);
        ev.setUserId(userId);
        ev.setUsername(username);
        ev.setPath(request.getRequestURI());
        ev.setIp(getClientIp(request));
        ev.setUserAgent(safeTruncate(request.getHeader("User-Agent"), 100));
        ev.setDetails(details);

        repo.save(ev);
    }

    private String getClientIp(HttpServletRequest request) {
        // na start prosto; jak kiedyś dasz reverse proxy, dołożysz X-Forwarded-For
        String ip = request.getRemoteAddr();
        return safeTruncate(ip, 100);
    }

    private String safeTruncate(String s, int max) {
        if (s == null) return null;
        s = s.trim();
        return s.length() <= max ? s : s.substring(0, max);
    }
}
