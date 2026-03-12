import java.util.*;

class Transaction {

    int id;
    int amount;
    String merchant;
    String account;
    int time;

    Transaction(int id, int amount, String merchant, String account, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

class TransactionAnalyzer {

    List<Transaction> transactions = new ArrayList<>();

    void addTransaction(Transaction t) {
        transactions.add(t);
    }

    void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        System.out.println("Two-Sum pairs:");

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println(
                        "(" + other.id + ", " + t.id + ") → " +
                        other.amount + " + " + t.amount
                );
            }

            map.put(t.amount, t);
        }
    }

    void findTwoSumWithTimeWindow(int target, int window) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        System.out.println("\nTwo-Sum within time window:");

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                if (Math.abs(t.time - other.time) <= window) {

                    System.out.println(
                            "(" + other.id + ", " + t.id + ") within window"
                    );
                }
            }

            map.put(t.amount, t);
        }
    }

    void detectDuplicates() {

        HashMap<String, List<String>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.account);
        }

        System.out.println("\nDuplicate transactions:");

        for (String key : map.keySet()) {

            List<String> accounts = map.get(key);

            if (accounts.size() > 1) {

                System.out.println(
                        key + " → Accounts: " + accounts
                );
            }
        }
    }
}

public class TransactionFraudSystem {

    public static void main(String[] args) {

        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        analyzer.addTransaction(new Transaction(1, 500, "StoreA", "acc1", 1000));
        analyzer.addTransaction(new Transaction(2, 300, "StoreB", "acc2", 1015));
        analyzer.addTransaction(new Transaction(3, 200, "StoreC", "acc3", 1030));
        analyzer.addTransaction(new Transaction(4, 500, "StoreA", "acc4", 1100));

        analyzer.findTwoSum(500);

        analyzer.findTwoSumWithTimeWindow(500, 60);

        analyzer.detectDuplicates();
    }
}