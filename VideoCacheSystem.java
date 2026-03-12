import java.util.*;

class MultiLevelCache {

    LinkedHashMap<String, String> L1;
    LinkedHashMap<String, String> L2;
    HashMap<String, String> database;

    int l1Hits = 0;
    int l2Hits = 0;
    int l3Hits = 0;

    int L1_SIZE = 10000;
    int L2_SIZE = 100000;

    MultiLevelCache() {

        L1 = new LinkedHashMap<String, String>(L1_SIZE, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                return size() > L1_SIZE;
            }
        };

        L2 = new LinkedHashMap<String, String>(L2_SIZE, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                return size() > L2_SIZE;
            }
        };

        database = new HashMap<>();

        database.put("video_123", "VideoData123");
        database.put("video_456", "VideoData456");
        database.put("video_999", "VideoData999");
    }

    String getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1.get(videoId);
        }

        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("L1 MISS → L2 HIT (5ms)");

            String data = L2.get(videoId);
            L1.put(videoId, data);

            System.out.println("Promoted to L1");
            return data;
        }

        if (database.containsKey(videoId)) {

            l3Hits++;
            System.out.println("L1 MISS → L2 MISS → L3 Database HIT (150ms)");

            String data = database.get(videoId);

            L2.put(videoId, data);

            return data;
        }

        System.out.println("Video not found");
        return null;
    }

    void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        if (total == 0) total = 1;

        System.out.println("\nCache Statistics");

        System.out.println("L1 Hit Rate: " + (l1Hits * 100 / total) + "%");
        System.out.println("L2 Hit Rate: " + (l2Hits * 100 / total) + "%");
        System.out.println("L3 Hit Rate: " + (l3Hits * 100 / total) + "%");

        System.out.println("Overall Requests: " + total);
    }
}

public class VideoCacheSystem {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123");
        cache.getVideo("video_123");
        cache.getVideo("video_999");

        cache.getStatistics();
    }
}