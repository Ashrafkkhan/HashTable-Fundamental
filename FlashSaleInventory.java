import java.util.*;

class FlashSaleInventory {

    HashMap<String, Integer> stock = new HashMap<>();
    HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public FlashSaleInventory() {
        stock.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    public void checkStock(String productId) {
        int available = stock.getOrDefault(productId, 0);
        System.out.println(productId + " → " + available + " units available");
    }

    public synchronized void purchaseItem(String productId, int userId) {

        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {

            stock.put(productId, available - 1);

            System.out.println("User " + userId +
                    " → Purchase Success, " +
                    (available - 1) + " units remaining");

        } else {

            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);

            System.out.println("User " + userId +
                    " → Added to waiting list, position #" +
                    queue.size());
        }
    }
}

public class Main {

    public static void main(String[] args) {

        FlashSaleInventory system = new FlashSaleInventory();

        system.checkStock("IPHONE15_256GB");

        system.purchaseItem("IPHONE15_256GB", 12345);
        system.purchaseItem("IPHONE15_256GB", 67890);

        for (int i = 1; i <= 100; i++) {
            system.purchaseItem("IPHONE15_256GB", 10000 + i);
        }

        system.purchaseItem("IPHONE15_256GB", 99999);
    }
}