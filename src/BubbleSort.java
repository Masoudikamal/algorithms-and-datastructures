import java.io.*;
import java.util.*;

public class BubbleSort {

    public static void main(String[] args) {
        // Load latitudes from file
        List<Double> latitudes = loadLatitudes("data/worldcities.csv");
        System.out.println("Number of unique latitudes loaded: " + latitudes.size());

        // Non-optimized Bubble Sort of the original list
        List<Double> nonOptimizedList = new ArrayList<>(latitudes);
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        long startNonOpt = System.nanoTime();
        int nonOptSwaps = bubbleSortNonOptimized(nonOptimizedList);
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        long usedMemoryBytes = afterMemory - beforeMemory;
        long durationNonOpt = System.nanoTime() - startNonOpt;
        double durationNonOptSec = durationNonOpt / 1_000_000_000.0;
        System.out.println("\nNon-optimized Bubble Sort on original list:");
        System.out.println("Number of swaps: " + nonOptSwaps);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytes);
        System.out.printf("Time taken (seconds): %.2f%n", durationNonOptSec);
        printFirstAndLastFive(nonOptimizedList);


        // Optimized Bubble Sort on the original list
        List<Double> optimizedList = new ArrayList<>(latitudes);
        Runtime runtimeOpt = Runtime.getRuntime();
        runtimeOpt.gc();
        long beforeMemoryOpt = runtimeOpt.totalMemory() - runtimeOpt.freeMemory();
        long startOpt = System.nanoTime();
        int optSwaps = bubbleSortOptimized(optimizedList);
        long afterMemoryOpt = runtimeOpt.totalMemory() - runtimeOpt.freeMemory();
        long usedMemoryBytesOpt = afterMemoryOpt - beforeMemoryOpt;
        long durationOpt = System.nanoTime() - startOpt;
        double durationOptSec = durationOpt / 1_000_000_000.0;
        System.out.println("\nOptimized Bubble Sort on original list:");
        System.out.println("Number of swaps: " + optSwaps);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesOpt);
        System.out.printf("Time taken (seconds): %.2f%n", durationOptSec);
        printFirstAndLastFive(optimizedList);


        // Randomize the list
        Collections.shuffle(latitudes);
        System.out.println("\nThe list has been randomized.");

        // Non-optimized Bubble Sort on the randomized list
        List<Double> nonOptRandom = new ArrayList<>(latitudes);
        Runtime runtimeNonOptRandom = Runtime.getRuntime();
        runtimeNonOptRandom.gc();
        long beforeMemoryNonOptRandom = runtimeNonOptRandom.totalMemory() - runtimeNonOptRandom.freeMemory();
        long startNonOptRandom = System.nanoTime();
        int nonOptSwapsRandom = bubbleSortNonOptimized(nonOptRandom);
        long afterMemoryNonOptRandom = runtimeNonOptRandom.totalMemory() - runtimeNonOptRandom.freeMemory();
        long usedMemoryBytesNonOptRandom = afterMemoryNonOptRandom - beforeMemoryNonOptRandom;
        long durationNonOptRandom = System.nanoTime() - startNonOptRandom;
        double durationNonOptRandomSec = durationNonOptRandom / 1_000_000_000.0;
        System.out.println("\nNon-optimized Bubble Sort on randomized list:");
        System.out.println("Number of swaps: " + nonOptSwapsRandom);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesNonOptRandom);
        System.out.printf("Time taken (seconds): %.2f%n", durationNonOptRandomSec);
        printFirstAndLastFive(nonOptRandom);


        // Optimized Bubble Sort on the randomized list
        List<Double> optRandom = new ArrayList<>(latitudes);
        Runtime runtimeOptRandom = Runtime.getRuntime();
        runtimeOptRandom.gc();
        long beforeMemoryOptRandom = runtimeOptRandom.totalMemory() - runtimeOptRandom.freeMemory();
        long startOptRandom = System.nanoTime();
        int optSwapsRandom = bubbleSortOptimized(optRandom);
        long afterMemoryOptRandom = runtimeOptRandom.totalMemory() - runtimeOptRandom.freeMemory();
        long usedMemoryBytesOptRandom = afterMemoryOptRandom - beforeMemoryOptRandom;
        long durationOptRandom = System.nanoTime() - startOptRandom;
        double durationOptRandomSec = durationOptRandom / 1_000_000_000.0;
        System.out.println("\nOptimized Bubble Sort on randomized list:");
        System.out.println("Number of swaps: " + optSwapsRandom);
        System.out.println("Approx. memory used (bytes): " + usedMemoryBytesOptRandom);
        System.out.printf("Time taken (seconds): %.2f%n", durationOptRandomSec);
        printFirstAndLastFive(optRandom);
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
                        // Only add if this latitude is not already present
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

    // Non-optimized BubbleSort Algorithm
    private static int bubbleSortNonOptimized(List<Double> list) {
        int swaps = 0;
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                }
            }
        }
        return swaps;
    }

    // Optimized BubbleSort Algorithm
    private static int bubbleSortOptimized(List<Double> list) {
        int swaps = 0;
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return swaps;
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