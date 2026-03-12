import java.util.*;

class ParkingSpot {

    String licensePlate;
    long entryTime;

    ParkingSpot(String plate) {
        licensePlate = plate;
        entryTime = System.currentTimeMillis();
    }
}

class ParkingLot {

    ParkingSpot[] table;
    int capacity;
    int occupied = 0;
    int totalProbes = 0;

    ParkingLot(int size) {
        capacity = size;
        table = new ParkingSpot[size];
    }

    int hash(String plate) {
        return Math.abs(plate.hashCode()) % capacity;
    }

    void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {

            index = (index + 1) % capacity;
            probes++;

            if (probes >= capacity) {
                System.out.println("Parking Full");
                return;
            }
        }

        table[index] = new ParkingSpot(plate);
        occupied++;
        totalProbes += probes;

        System.out.println(
                "parkVehicle(\"" + plate + "\") → Assigned spot #" +
                index + " (" + probes + " probes)"
        );
    }

    void exitVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {

            if (table[index].licensePlate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - table[index].entryTime) / 1000;

                table[index] = null;
                occupied--;

                double fee = duration * 0.01;

                System.out.println(
                        "exitVehicle(\"" + plate + "\") → Spot #" +
                        index + " freed, Duration: " +
                        duration + " sec, Fee: $" +
                        String.format("%.2f", fee)
                );

                return;
            }

            index = (index + 1) % capacity;
            probes++;

            if (probes >= capacity) break;
        }

        System.out.println("Vehicle not found");
    }

    void getStatistics() {

        double occupancy = (occupied * 100.0) / capacity;
        double avgProbes = occupied == 0 ? 0 : (double) totalProbes / occupied;

        System.out.println("\nParking Statistics:");
        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%");
        System.out.println("Average Probes: " + String.format("%.2f", avgProbes));
    }
}

public class ParkingSystem {

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot(500);

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}