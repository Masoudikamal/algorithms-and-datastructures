import java.io.*;
import java.util.*;

public class InsertionSort {

    public static void main(String[] args) {
        // Load latitudes from file
        List<Double> latitudes = loadLatitudes("data/worldcities.csv");
        System.out.println("Number of unique latitudes loaded: " + latitudes.size());

        // Insertion Sort of the list
        List<Double> originalList = new ArrayList<>(latitudes);
        Runtime runtimeOriginal = Runtime.getRuntime();
        runtimeOriginal.gc();
        long beforeMemoryOriginal = runtimeOriginal.totalMemory() - runtimeOriginal.freeMemory();
        long startOriginal = System.nanoTime();
        int comparisonsOriginal = insertionSort(originalList);
        long afterMemoryOriginal = runtimeOriginal.totalMemory() - runtimeOriginal.freeMemory();
        long usedMemoryBytesOriginal = afterMemoryOriginal - beforeMemoryOriginal;
        long durationOriginal = System.nanoTime() - startOriginal;
        double durationOriginalSec = durationOriginal / 1_000_000_000.0;
        System.out.println("\nInsertion Sort on original list:");
        System.out.println("Number of comparisons: " + comparisonsOriginal);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesOriginal);
        System.out.printf("Time taken (seconds): %.2f%n", durationOriginalSec);
        printFirstAndLastFive(originalList);


        // Insertion Sort on the randomized list
        Collections.shuffle(latitudes);
        System.out.println("\nThe list has been shuffled (randomized).");
        List<Double> randomizedList = new ArrayList<>(latitudes);
        Runtime runtimeRandom = Runtime.getRuntime();
        runtimeRandom.gc();
        long beforeMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long startRandom = System.nanoTime();
        int comparisonsRandom = insertionSort(randomizedList);
        long afterMemoryRandom = runtimeRandom.totalMemory() - runtimeRandom.freeMemory();
        long usedMemoryBytesRandom = afterMemoryRandom - beforeMemoryRandom;
        long durationRandom = System.nanoTime() - startRandom;
        double durationRandomSec = durationRandom / 1_000_000_000.0;
        System.out.println("\nInsertion Sort on randomized list:");
        System.out.println("Number of comparisons: " + comparisonsRandom);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesRandom);
        System.out.printf("Time taken (seconds): %.2f%n", durationRandomSec);
        printFirstAndLastFive(randomizedList);
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

    // InsertionSort Algorithm
    private static int insertionSort(List<Double> list) {
        int comparisons = 0;
        int n = list.size();
        for (int i = 1; i < n; i++) {
            double key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                comparisons++;
                list.set(j + 1, list.get(j));
                j--;
            }
            if (j >= 0) {
                comparisons++;
            }
            list.set(j + 1, key);
        }
        return comparisons;
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