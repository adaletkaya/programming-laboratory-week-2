import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Güzergahtaki durak sayısını girin: ");
        int numStops = scanner.nextInt();

        System.out.print("Otobüsün koltuk kapasitesini girin: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        List<String> stopNames = new ArrayList<>();
        List<Integer> boardingList = new ArrayList<>();
        List<Integer> alightingList = new ArrayList<>();

        System.out.println("\n--- Durak Bilgilerini Girin ---");
        for (int i = 0; i < numStops; i++) {
            System.out.println("\n" + (i + 1) + ". Durak:");
            System.out.print("  Durak adı: ");
            String name = scanner.nextLine();

            System.out.print("  Binen yolcu sayısı: ");
            int boarding = scanner.nextInt();

            System.out.print("  İnen yolcu sayısı: ");
            int alighting = scanner.nextInt();
            scanner.nextLine();

            stopNames.add(name);
            boardingList.add(boarding);
            alightingList.add(alighting);
        }

        int currentOccupancy = 0;
        List<Integer> occupancies = new ArrayList<>();
        int overCapacityCount = 0;

        System.out.println("\n--- Durak Durak İşlem Raporu ---");
        for (int i = 0; i < numStops; i++) {
            String stopName = stopNames.get(i);
            int boarding = boardingList.get(i);
            int alighting = alightingList.get(i);

            if (alighting > currentOccupancy + boarding) {
                System.out.printf("Data error at [%s]: cannot have more passengers alighting than are currently on the bus. Occupancy set to 0.%n", stopName);
                currentOccupancy = 0;
            } else {
                currentOccupancy = currentOccupancy + boarding - alighting;
            }

            occupancies.add(currentOccupancy);

            if (currentOccupancy > capacity) {
                System.out.printf("Warning: Bus is over capacity at [%s]!%n", stopName);
                overCapacityCount++;
            }
        }

        System.out.println("\n==========================================");
        System.out.println("           TÜM DURAKLARIN LİSTESİ         ");
        System.out.println("==========================================");
        System.out.printf("%-20s | %-8s | %-8s | %-12s%n", "Durak Adı", "Binen", "İnen", "Mevcut Yolcu");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < numStops; i++) {
            System.out.printf("%-20s | %-8d | %-8d | %-12d%n",
                    stopNames.get(i), boardingList.get(i), alightingList.get(i), occupancies.get(i));
        }

        System.out.println("\n==========================================");
        System.out.println("                İSTATİSTİKLER             ");
        System.out.println("==========================================");

        int maxBoarding = -1;
        int busiestIndex = 0;
        for (int i = 0; i < boardingList.size(); i++) {
            if (boardingList.get(i) > maxBoarding) {
                maxBoarding = boardingList.get(i);
                busiestIndex = i;
            }
        }
        System.out.printf("En yoğun durak (En çok binen): %s (%d kişi)%n", stopNames.get(busiestIndex), maxBoarding);

        double totalOccupancy = 0;
        for (int occ : occupancies) {
            totalOccupancy += occ;
        }
        double avgOccupancy = numStops > 0 ? totalOccupancy / numStops : 0;
        System.out.printf("Tüm duraklardaki ortalama yolcu sayısı: %.2f%n", avgOccupancy);

        System.out.printf("Kapasitenin aşıldığı durak sayısı: %d%n", overCapacityCount);

        int finalOccupancy = occupancies.isEmpty() ? 0 : occupancies.get(occupancies.size() - 1);
        if (finalOccupancy != 0) {
            System.out.printf("Warning: %d passengers still on the bus after the final stop — please check your data.%n", finalOccupancy);
        }

        scanner.close();
    }
}