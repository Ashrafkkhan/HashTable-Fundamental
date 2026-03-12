import java.util.*;

class UsernameChecker {

    HashMap<String, Integer> users = new HashMap<>();
    HashMap<String, Integer> attempts = new HashMap<>();

    public UsernameChecker() {

        users.put("john_doe", 1);
        users.put("admin", 2);
        users.put("alex", 3);
    }

    public boolean checkAvailability(String username) {

        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        if (users.containsKey(username)) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    public String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String name : attempts.keySet()) {

            if (attempts.get(name) > max) {
                max = attempts.get(name);
                most = name;
            }
        }

        return most + " (" + max + " attempts)";
    }
}

public class Main {

    public static void main(String[] args) {

        UsernameChecker checker = new UsernameChecker();

        System.out.println("Check username john_doe: " + checker.checkAvailability("john_doe"));
        System.out.println("Check username jane_smith: " + checker.checkAvailability("jane_smith"));

        System.out.println("\nSuggestions for john_doe:");
        System.out.println(checker.suggestAlternatives("john_doe"));

        checker.checkAvailability("admin");
        checker.checkAvailability("admin");
        checker.checkAvailability("admin");

        System.out.println("\nMost attempted username:");
        System.out.println(checker.getMostAttempted());
    }
}