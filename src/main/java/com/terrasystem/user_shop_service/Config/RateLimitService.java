package com.terrasystem.user_shop_service.Config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitService {

    // Buckety per IP oraz per username
    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();
    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    // Ustawienia (proste i sensowne na start)
    // IP: 20 prób / minuta
    // USER: 10 prób / minuta (na konkretny username)
    private Bucket newIpBucket() {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket newUserBucket() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    public void consumeLoginAttempt(String ip, String usernameOrNull) {
        Bucket ipBucket = ipBuckets.computeIfAbsent(ip, k -> newIpBucket());
        if (!ipBucket.tryConsume(1)) {
            throw new TooManyRequestsException("Too many login attempts from this IP. Try again later.");
        }

        if (usernameOrNull != null && !usernameOrNull.isBlank()) {
            String key = usernameOrNull.trim().toLowerCase();
            Bucket userBucket = userBuckets.computeIfAbsent(key, k -> newUserBucket());
            if (!userBucket.tryConsume(1)) {
                throw new TooManyRequestsException("Too many login attempts for this user. Try again later.");
            }
        }
    }

    public void consumeLoginAttemptForUser(String username) {
        if (username == null || username.isBlank()) return;
        String key = username.trim().toLowerCase();
        Bucket userBucket = userBuckets.computeIfAbsent(key, k -> newUserBucket());
        if (!userBucket.tryConsume(1)) {
            throw new TooManyRequestsException("Too many login attempts for this user. Try again later.");
        }
    }

}
