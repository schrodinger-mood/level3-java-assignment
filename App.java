import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scannerMain = new Scanner(System.in);

        getPage1();

        int selectionMain = scannerMain.nextInt();

        // Perform the appropriate action based on the user's selection.
        switch (selectionMain) {
            case 1:
                printHeader();

                try {
                    getModus();
                    System.out.println("File telah digenerate di: "+ System.getProperty("user.dir"));
                    System.out.println("Silahkan cek");
                    printFooterMenu(args);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                break;
            case 2:
                printHeader();

                try {
                    getMeanMedianModus();
                    System.out.println("File telah digenerate di: "+ System.getProperty("user.dir"));
                    System.out.println("Silahkan cek");
                    printFooterMenu(args);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    getModus();
                    getMeanMedianModus();
                    System.out.println("File telah digenerate di: "+ System.getProperty("user.dir"));
                    System.out.println("Silahkan cek");
                    printFooterMenu(args);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    private static void printFooterMenu(String[] args) {
        System.out.println("");
        System.out.println("0 Exit");
        System.out.println("1 Kembali ke menu utama");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();

        if (selection == 1) main(args);
        else System.exit(0);
    }

    private static void getModus() throws FileNotFoundException {
        // Create a BufferedReader object to read the CSV file.
        BufferedReader reader = new BufferedReader(new FileReader("data_sekolah.csv"));

        // Create an ArrayList to store the data from the CSV file.
        ArrayList<Integer> data = new ArrayList<>();

        // Read the CSV file line by line.
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
                // Split the line into an array of strings.
                String[] values = line.split(";");
                Arrays.stream(values).skip(1).forEach(x -> {
                    data.add(Integer.parseInt(x));
                });
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }

        // Create a HashMap to store the count of each integer in the ArrayList.
        HashMap<Integer, Integer> counts = new HashMap<>();

        // Count the number of times each integer appears in the ArrayList.
        for (int value : data) {
            int count = counts.getOrDefault(value, 0);
            counts.put(value, count + 1);
        }

        // Find the integer lower than 6
        int lowerThan6 = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getKey() < 6) {
                lowerThan6 += entry.getValue();
            }

        }

        List<String> outputStr = new ArrayList<>();

        outputStr.add("Nilai         \t\t|  Frekuensi");
        outputStr.add("Kurang dari 6 \t\t|  " + lowerThan6);

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getKey() > 5) {
                outputStr.add(entry.getKey() + "              \t\t|  " + entry.getValue());
            }

        }

        File file = new File("data_sekolah_modus.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            // Write the data to the file.
            for (String x : outputStr) {
                writer.write(x + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getMeanMedianModus() throws FileNotFoundException {
        // Create a BufferedReader object to read the CSV file.
        BufferedReader reader = new BufferedReader(new FileReader("data_sekolah.csv"));

        // Create an ArrayList to store the data from the CSV file.
        ArrayList<Integer> data = new ArrayList<>();

        // Read the CSV file line by line.
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
                // Split the line into an array of strings.
                String[] values = line.split(";");
                Arrays.stream(values).skip(1).forEach(x -> {
                    data.add(Integer.parseInt(x));
                });
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }

        // Create a HashMap to store the count of each integer in the ArrayList.
        HashMap<Integer, Integer> counts = new HashMap<>();
        int n = 0;

        // Count the number of times each integer appears in the ArrayList.
        for (int value : data) {
            int count = counts.getOrDefault(value, 0);
            counts.put(value, count + 1);
            n += 1;
        }

        ArrayList<Integer> values = new ArrayList<>(counts.keySet());
        ArrayList<Integer> freq = new ArrayList<>(counts.values());

        List<String> outputStr = new ArrayList<>();

        outputStr.add("Berikut hasil pengolahan nilai:");
        outputStr.add("");
        outputStr.add("Berikut hasil sebaran data nilai");
        outputStr.add("Mean: " + findMean(values, freq));
        outputStr.add("Median: " + findMedian(values, freq));
        outputStr.add("Modus: " + findModus(values, freq));

        File file = new File("data_sekolah_modus_median.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            // Write the data to the file.
            for (String x : outputStr) {
                writer.write(x + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double findMean(ArrayList<Integer> values, ArrayList<Integer> frequencies) {
        // Calculate the sum of all values.
        double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i) * frequencies.get(i);
        }

        // Calculate the mean.
        return sum / frequencies.stream().reduce(0, Integer::sum);
    }

    public static double findMedian(ArrayList<Integer> values, ArrayList<Integer> frequencies) {
        // Calculate the total number of values.
        int total = 0;
        int cumulativeFreqN = 0;
        ArrayList<Integer> cumulativeFreq = new ArrayList<>();
        for (int i = 0; i < frequencies.size(); i++) {
            cumulativeFreqN += frequencies.get(i);
            cumulativeFreq.add(cumulativeFreqN);
            total += frequencies.get(i);
        }

        int medianIndex = (total + 1) / 2;
        int tempIndex = 0;
        for (int i = 0; i <cumulativeFreq.size() ; i++) {
            if (cumulativeFreq.get(i) < medianIndex) tempIndex = i;
            else {
                tempIndex = i;
                break;
            }
        }

        return values.get(tempIndex);
    }

    public static int findModus(ArrayList<Integer> values, ArrayList<Integer> frequencies) {
        int maxValIndex = frequencies.indexOf(Collections.max(frequencies));

        return values.get(maxValIndex);
    }

    private static void getPage1() {
        printHeader();
        System.out.println("Letakkan file csv dengan nama file data_sekolah di direktori berikut: "+ System.getProperty("user.dir"));
        System.out.println();
        System.out.println("pilih menu:");
        System.out.println("1. Generate txt untuk menampilkan modus");
        System.out.println("2. Generate txt untuk menampilkan nilai rata-rata, median");
        System.out.println("3. Generate kedua file");
        System.out.println("0. Exit");
    }

    private static void printHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("Aplikasi Pengolah Nilai Siswa");
        System.out.println("-------------------------------------------------");
    }

}
