package com.example.Crypto_Scanner.rate_limit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    private Bucket createBucket() {

    	Refill refill = Refill.intervally(
    	        100,
    	        Duration.ofMinutes(1)
    	);

    	Bandwidth limit = Bandwidth.classic(
    	        100,
    	        refill
    	);

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean isAllowed(String userEmail) {

        Bucket bucket = buckets.computeIfAbsent(
                userEmail,
                key -> createBucket()
        );

        return bucket.tryConsume(1);
    }
}