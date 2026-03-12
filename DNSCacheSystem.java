import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    public DNSEntry(String ip, int ttlSeconds) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    private int capacity = 5;

    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<>(capacity, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > capacity;
                }
            };

    private int hits = 0;
    private int misses = 0;

    public String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                System.out.println("Cache HIT → " + entry.ip);
                return entry.ip;
            } else {
                cache.remove(domain);
                System.out.println("Cache EXPIRED → querying upstream...");
            }
        }

        misses++;

        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(ip, 10));

        System.out.println("Cache MISS → Upstream returned: " + ip);

        return ip;
    }

    private String queryUpstreamDNS(String domain) {

        Random r = new Random();
        return "172.217.14." + (200 + r.nextInt(50));
    }

    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);

        System.out.println("\nCache Stats");
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }
}

public class DNSCacheSystem {

    public static void main(String[] args) throws Exception {

        DNSCache dns = new DNSCache();

        dns.resolve("google.com");
        dns.resolve("google.com");

        Thread.sleep(11000);

        dns.resolve("google.com");

        dns.getCacheStats();
    }
}