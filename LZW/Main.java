import java.io.*;
import java.util.*;

public class Main {

    public static void compress(String filename) {


        // Initialize our map with Chararcters from 0 to 127
        Integer i = 0;
        HashMap<String, Integer> map = new HashMap<>();
        while (i < 128) {
            char ch = (char) (int) i;
            String s = String.valueOf(ch);
            map.put(s, i);
            i++;
        }

        ArrayList<Integer> locationsOfExistedPatterns = new ArrayList<>();
        File file = new File(filename);

        int ch;
        String current = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            current += (char) br.read();
            while ((ch = br.read()) != -1) {
                if (map.containsKey(current + (char) ch)) {
                    current += (char) ch;
                } else {
                    locationsOfExistedPatterns.add(map.get(current));
                    map.put(current + (char) ch, i);
                    current = "" + (char) ch;
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // writing locationsOfExistedPatterns to compressed file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("compressed.txt"))) {

            for (Integer location : locationsOfExistedPatterns) {
                writer.write(location.toString());
                writer.write(" ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decompress(String filename) {

        // Map to store the codes and their corresponding patterns
        HashMap<Integer, String> map = new HashMap<>();
        Integer j = 0;
        while (j < 128) {
            char ch = (char) (int) j;
            String s = String.valueOf(ch);
            map.put(j, s);
            j++;
        }

        File file = new File(filename);
        ArrayList<Integer> codes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] numbers = line.split(" ");
            for (String number : numbers) {
                int num = Integer.parseInt(number);
                codes.add(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String originaltext = "";

        Integer priorCode = codes.get(0);
        Integer code = -1;
        originaltext += map.get(priorCode);
        String newEntry = "";

        for (int i = 1; i < codes.size(); i++) {
            code = codes.get(i);
            if (map.containsKey(code)) {
                originaltext += map.get(code);
                newEntry = map.get(priorCode) + map.get(code).charAt(0);
            } else {
                newEntry = map.get(priorCode) + map.get(priorCode).charAt(0);
                originaltext += newEntry;
            }
            map.put(j, newEntry);
            j++;
            priorCode = code;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("decompressed.txt"))) {
            writer.write(originaltext);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Compress or Decompress: ");
        String input = scanner.nextLine();
        String originalFile = "originaltext.txt";
        String compressedFile = "compressed.txt";
        String decompressedFile = "decompressed.txt";
        switch (input) {
            case "compress":
                compress(originalFile);
                break;

            default:
                decompress(compressedFile);
                break;
        }
        //
        scanner.close();
    }

}

