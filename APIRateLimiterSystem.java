import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate;

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public void refill() {

        long now = System.currentTimeMillis();
        long seconds = (now - lastRefillTime) / 1000;

        int tokensToAdd = (int) (seconds * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    public boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    public int getRemainingTokens() {
        return tokens;
    }
}

class RateLimiter {

    HashMap<String, TokenBucket> clients = new HashMap<>();

    int MAX_REQUESTS = 1000;
    int REFILL_RATE = 1000 / 3600;

    public void checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(MAX_REQUESTS, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {

            System.out.println(
                    "Allowed (" +
                    bucket.getRemainingTokens() +
                    " requests remaining)"
            );

        } else {

            System.out.println(
                    "Denied (Rate limit exceeded)"
            );
        }
    }
}

public class APIRateLimiterSystem {

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        limiter.checkRateLimit("abc123");
        limiter.checkRateLimit("abc123");
        limiter.checkRateLimit("abc123");
    }
}