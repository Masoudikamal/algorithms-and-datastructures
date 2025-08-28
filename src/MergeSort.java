import java.io.*;
import java.util.*;

public class MergeSort {

    public static void main(String[] args) {
        // Load latitudes from file
        List<Double> latitudes = loadLatitudes("data/worldcities.csv");
        System.out.println("Number of unique latitudes loaded: " + latitudes.size());

        // Merge Sort of the list
        List<Double> originalList = new ArrayList<>(latitudes);
        Runtime runtimeOriginal = Runtime.getRuntime();
        runtimeOriginal.gc();
        long beforeMemoryOriginal = runtimeOriginal.totalMemory() - runtimeOriginal.freeMemory();
        long startOriginal = System.nanoTime();
        int mergeCountOriginal = mergeSort(originalList, 0, originalList.size() - 1);
        long afterMemoryOriginal = runtimeOriginal.totalMemory() - runtimeOriginal.freeMemory();
        long usedMemoryBytesOriginal = afterMemoryOriginal - beforeMemoryOriginal;
        long durationOriginal = System.nanoTime() - startOriginal;
        double durationOriginalSec = durationOriginal / 1_000_000_000.0;
        System.out.println("\nMerge Sort on original list:");
        System.out.println("Number of merge operations: " + mergeCountOriginal);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesOriginal);
        System.out.printf("Time taken (seconds): %.2f%n", durationOriginalSec);
        printFirstAndLastFive(originalList);

        // Merge Sort of the randomized list
        Collections.shuffle(latitudes);
        System.out.println("\nThe list has been shuffled (randomized).");
        List<Double> randomList = new ArrayList<>(latitudes);
        Runtime runtimeRandom = Runtime.getRuntime();
        runtimeRandom.gc();
        long beforeMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long startRandom = System.nanoTime();
        int mergeCountRandom = mergeSort(randomList, 0, randomList.size() - 1);
        long afterMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long usedMemoryBytesRandom = afterMemoryRandom - beforeMemoryRandom;
        long durationRandom = System.nanoTime() - startRandom;
        double durationRandomSec = durationRandom / 1_000_000_000.0;
        System.out.println("\nMerge Sort on randomized list:");
        System.out.println("Number of merge operations: " + mergeCountRandom);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesRandom);
        System.out.printf("Time taken (seconds): %.2f%n", durationRandomSec);
        printFirstAndLastFive(randomList);
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

    // MergeSort Algorithm
    private static int mergeSort(List<Double> list, int left, int right) {
        if (left >= right) {
            return 0; // No merge needed for a single element.
        }
        int mid = left + (right - left) / 2;
        // Recursively sort the left and right halves.
        int mergeCount = mergeSort(list, left, mid) + mergeSort(list, mid + 1, right);
        // Merge the two sorted halves and count this merge operation.
        merge(list, left, mid, right);
        mergeCount++; // Count this merge operation.
        return mergeCount;
    }

    // Merge method
    private static void merge(List<Double> list, int left, int mid, int right) {
        List<Double> temp = new ArrayList<>();
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (list.get(i) <= list.get(j)) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }
        while (i <= mid) {
            temp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(list.get(j));
            j++;
        }
        // Copy the sorted sublist back into the original list.
        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
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