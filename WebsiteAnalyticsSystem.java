import java.util.*;

class AnalyticsDashboard {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        System.out.println("\nTop Pages:");

        List<Map.Entry<String, Integer>> pages =
                new ArrayList<>(pageViews.entrySet());

        pages.sort((a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(10, pages.size());

        for (int i = 0; i < limit; i++) {

            String url = pages.get(i).getKey();
            int views = pages.get(i).getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(
                    (i + 1) + ". " + url +
                    " - " + views +
                    " views (" + unique + " unique)"
            );
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int c : trafficSources.values()) {
            total += c;
        }

        for (String src : trafficSources.keySet()) {

            int count = trafficSources.get(src);
            double percent = (count * 100.0) / total;

            System.out.println(
                    src + ": " + String.format("%.1f", percent) + "%"
            );
        }
    }
}

public class WebsiteAnalyticsSystem {

    public static void main(String[] args) {

        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        dashboard.processEvent("/article/breaking-news", "user_123", "google");
        dashboard.processEvent("/article/breaking-news", "user_456", "facebook");
        dashboard.processEvent("/sports/championship", "user_111", "direct");
        dashboard.processEvent("/sports/championship", "user_222", "google");
        dashboard.processEvent("/sports/championship", "user_111", "google");
        dashboard.processEvent("/article/breaking-news", "user_789", "google");

        dashboard.getDashboard();
    }
}