import java.util.*;

class AutocompleteSystem {

    HashMap<String, Integer> queryFrequency = new HashMap<>();

    public void addQuery(String query) {

        queryFrequency.put(
                query,
                queryFrequency.getOrDefault(query, 0) + 1
        );
    }

    public void search(String prefix) {

        List<Map.Entry<String, Integer>> results = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                results.add(entry);
            }
        }

        results.sort((a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(10, results.size());

        System.out.println("\nSuggestions for \"" + prefix + "\"");

        for (int i = 0; i < limit; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    results.get(i).getKey() +
                    " (" + results.get(i).getValue() + " searches)"
            );
        }
    }

    public void updateFrequency(String query) {

        queryFrequency.put(
                query,
                queryFrequency.getOrDefault(query, 0) + 1
        );

        System.out.println(query + " → Frequency: " + queryFrequency.get(query));
    }
}

public class SearchAutocomplete {

    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        system.addQuery("java tutorial");
        system.addQuery("javascript");
        system.addQuery("java download");
        system.addQuery("java tutorial");
        system.addQuery("java interview questions");

        system.search("jav");

        system.updateFrequency("java 21 features");
        system.updateFrequency("java 21 features");
    }
}