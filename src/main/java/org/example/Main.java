import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Main {

    // Algorithm A: Brute Force (Double loop)
    public static long algorithmA(int[] data) {
        long ops = 0;
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                ops++;
                if (data[i] == data[j]) return ops;
            }
        }
        return ops;
    }

    // Algorithm B: Auxiliary Structure (Boolean array)
    public static long algorithmB(int[] data) {
        long ops = 0;
        boolean[] seen = new boolean[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            ops++;
            int value = data[i];
            if (seen[value] == true) return ops;
            seen[value] = true;
        }
        return ops;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};

        try {
            // 1. Prepare the CSV file for Excel
            FileWriter file = new FileWriter("resultados_comparativos.csv");
            PrintWriter writer = new PrintWriter(file);

            // 2. Write side-by-side headers for CSV
            writer.println("Size (N),Ops Algorithm A,Ops Algorithm B,Time A (ms),Time B (ms)");

            // 3. Print side-by-side headers for Console
            System.out.println("================================================================================");
            System.out.println("                        COMPARATIVE BENCHMARKING TABLE");
            System.out.println("================================================================================");
            System.out.printf("%-10s | %-16s | %-16s | %-12s | %-12s%n",
                    "Size (N)", "Ops Algo A", "Ops Algo B", "Time A (ms)", "Time B (ms)");
            System.out.println("--------------------------------------------------------------------------------");

            for (int i = 0; i < sizes.length; i++) {
                int n = sizes[i];

                // Generate worst-case data (no duplicates)
                int[] data = new int[n];
                for (int j = 0; j < n; j++) {
                    data[j] = j;
                }

                // Measure Algorithm A
                long totalTimeA = 0, opsA = 0;
                for (int rep = 0; rep < 3; rep++) {
                    long start = System.nanoTime();
                    opsA = algorithmA(data);
                    long end = System.nanoTime();
                    totalTimeA += (end - start);
                }
                long avgTimeA = (totalTimeA / 3) / 1000000;

                // Measure Algorithm B
                long totalTimeB = 0, opsB = 0;
                for (int rep = 0; rep < 3; rep++) {
                    long start = System.nanoTime();
                    opsB = algorithmB(data);
                    long end = System.nanoTime();
                    totalTimeB += (end - start);
                }
                long avgTimeB = (totalTimeB / 3) / 1000000;

                // Print side-by-side row to CSV
                writer.println(n + "," + opsA + "," + opsB + "," + avgTimeA + "," + avgTimeB);

                // Print side-by-side row to Console
                System.out.printf("%-10d | %-16d | %-16d | %-12d | %-12d%n",
                        n, opsA, opsB, avgTimeA, avgTimeB);
            }

            System.out.println("================================================================================");
            writer.close();
            System.out.println("\nSuccess! The comparative file 'resultados_comparativos.csv' has been generated.");

        } catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
        }
    }
}