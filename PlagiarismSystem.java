import java.util.*;

class PlagiarismDetector {

    HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    int N = 3;

    public List<String> getNgrams(String text) {

        List<String> grams = new ArrayList<>();
        String[] words = text.toLowerCase().split(" ");

        for (int i = 0; i <= words.length - N; i++) {

            String gram = words[i];

            for (int j = 1; j < N; j++) {
                gram += " " + words[i + j];
            }

            grams.add(gram);
        }

        return grams;
    }

    public void addDocument(String docId, String text) {

        List<String> grams = getNgrams(text);

        for (String g : grams) {

            ngramIndex.putIfAbsent(g, new HashSet<>());
            ngramIndex.get(g).add(docId);
        }

        System.out.println(docId + " → Stored " + grams.size() + " n-grams");
    }

    public void analyzeDocument(String docId, String text) {

        List<String> grams = getNgrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String g : grams) {

            if (ngramIndex.containsKey(g)) {

                for (String existingDoc : ngramIndex.get(g)) {

                    matchCount.put(
                            existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1
                    );
                }
            }
        }

        System.out.println("\nAnalyzing " + docId);

        for (String d : matchCount.keySet()) {

            int matches = matchCount.get(d);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Matches with " + d +
                    " → " + matches +
                    " n-grams | Similarity: " +
                    String.format("%.2f", similarity) + "%");
        }
    }
}

public class PlagiarismSystem {

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String essay1 = "machine learning is a powerful tool for data analysis";
        String essay2 = "machine learning is widely used for data science";
        String essay3 = "history of ancient civilizations and world culture";

        detector.addDocument("essay_089.txt", essay1);
        detector.addDocument("essay_092.txt", essay2);

        detector.analyzeDocument("essay_123.txt", essay1);
    }
}