package com.terrasystem.user_shop_service.Entity;

import jakarta.persistence.*;
import java.time.Instant;
import org.hibernate.type.SqlTypes.*;

@Entity
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(nullable = false, length = 100)
    private String outcome;

    @Column(name = "user_id")
    private Integer userId;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String ip;

    @Column(name = "user_agent", length = 100)
    private String userAgent;

    @Column(nullable = false, length = 100)
    private String path;

    @Lob
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "TEXT")
    private String details;


    @PrePersist
    public void prePersist() {
        if (timestamp == null) timestamp = Instant.now();
    }

    // --- getters/setters ---

    public Integer getId() { return id; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
