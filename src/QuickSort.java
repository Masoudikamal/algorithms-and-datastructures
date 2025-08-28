import java.io.*;
import java.util.*;

public class QuickSort {

    public static void main(String[] args) {
        // Load latitudes from worldcities
        List<Double> latitudes = loadLatitudes("data/worldcities.csv");
        System.out.println("Number of unique latitudes loaded: " + latitudes.size());

        // QuickSort using the first element as pivot
        List<Double> firstPivotList = new ArrayList<>(latitudes);
        Runtime runtimeFirst = Runtime.getRuntime();
        runtimeFirst.gc();
        long beforeMemoryFirst = runtimeFirst.totalMemory() - runtimeFirst.freeMemory();
        long startFirst = System.nanoTime();
        int comparisonsFirst = quickSort(firstPivotList, 0, firstPivotList.size() - 1, "first");
        long afterMemoryFirst = runtimeFirst.totalMemory() - runtimeFirst.freeMemory();
        long usedMemoryBytesFirst = afterMemoryFirst - beforeMemoryFirst;
        long durationFirst = System.nanoTime() - startFirst;
        double durationFirstSec = durationFirst / 1_000_000_000.0;
        System.out.println("\nQuickSort using first element as pivot:");
        System.out.println("Number of comparisons: " + comparisonsFirst);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesFirst);
        System.out.printf("Time taken (seconds): %.4f%n", durationFirstSec);
        printFirstAndLastFive(firstPivotList);

        // QuickSort using the last element as pivot
        List<Double> lastPivotList = new ArrayList<>(latitudes);
        Runtime runtimeLast = Runtime.getRuntime();
        runtimeLast.gc();
        long beforeMemoryLast = runtimeLast.totalMemory() - runtimeLast.freeMemory();
        long startLast = System.nanoTime();
        int comparisonsLast = quickSort(lastPivotList, 0, lastPivotList.size() - 1, "last");
        long afterMemoryLast = runtimeLast.totalMemory() - runtimeLast.freeMemory();
        long usedMemoryBytesLast = afterMemoryLast - beforeMemoryLast;
        long durationLast = System.nanoTime() - startLast;
        double durationLastSec = durationLast / 1_000_000_000.0;
        System.out.println("\nQuickSort using last element as pivot:");
        System.out.println("Number of comparisons: " + comparisonsLast);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesLast);
        System.out.printf("Time taken (seconds): %.4f%n", durationLastSec);
        printFirstAndLastFive(lastPivotList);

        // QuickSort using a random element as pivot
        List<Double> randomPivotList = new ArrayList<>(latitudes);
        Runtime runtimeRandom = Runtime.getRuntime();
        runtimeRandom.gc();
        long beforeMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long startRandom = System.nanoTime();
        int comparisonsRandom = quickSort(randomPivotList, 0, randomPivotList.size() - 1, "random");
        long afterMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long usedMemoryBytesRandom = afterMemoryRandom - beforeMemoryRandom;
        long durationRandom = System.nanoTime() - startRandom;
        double durationRandomSec = durationRandom / 1_000_000_000.0;
        System.out.println("\nQuickSort using random element as pivot:");
        System.out.println("Number of comparisons: " + comparisonsRandom);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesRandom);
        System.out.printf("Time taken (seconds): %.4f%n", durationRandomSec);
        printFirstAndLastFive(randomPivotList);
    }

    // Reads and extracts unique latitude values from the CSV file, filtering out invalid entries.
    private static List<Double> loadLatitudes(String filePath) {
        List<Double> latitudes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    // Remove quotes from the latitude field and trim spaces
                    String latStr = parts[2].replace("\"", "").trim();
                    try {
                        double latitude = Double.parseDouble(latStr);
                        // Only add if this latitude is not already in the list
                        if (!latitudes.contains(latitude)) {
                            latitudes.add(latitude);
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid numbers
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return latitudes;
    }

    // QuickSort Algorithm
    private static int quickSort(List<Double> list, int low, int high, String pivotType) {
        if (low >= high) return 0;
        int[] partitionData = partition(list, low, high, pivotType);
        int pivotIndex = partitionData[0];
        int count = partitionData[1];
        count += quickSort(list, low, pivotIndex - 1, pivotType);
        count += quickSort(list, pivotIndex + 1, high, pivotType);
        return count;
    }

    // Partition method
    private static int[] partition(List<Double> list, int low, int high, String pivotType) {
        double pivot;
        if (pivotType.equals("first")) {
            pivot = list.get(low);
            // Move the chosen pivot to the end for partitioning
            Collections.swap(list, low, high);
        } else if (pivotType.equals("random")) {
            int randomIndex = low + new Random().nextInt(high - low + 1);
            pivot = list.get(randomIndex);
            Collections.swap(list, randomIndex, high);
        } else { // "last"
            pivot = list.get(high);
        }

        int i = low - 1;
        int comparisons = 0;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (list.get(j) < pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return new int[]{i + 1, comparisons};
    }
    private static void printFirstAndLastFive(List<Double> sortedList) {
        System.out.println("First 5 latitudes:");
        for (int i = 0; i < Math.min(5, sortedList.size()); i++) {
            System.out.println(sortedList.get(i));
        }

        System.out.println("Last 5 latitudes:");
        for (int i = Math.max(0, sortedList.size() - 5); i < sortedList.size(); i++) {
            System.out.println(sortedList.get(i));
        }
    }
}
