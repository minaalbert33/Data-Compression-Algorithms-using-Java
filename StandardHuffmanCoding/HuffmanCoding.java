import java.util.*;
import java.io.*;

class Node implements Comparable<Node> {
    int frequency;
    char data;
    Node left, right;

    public Node(int frequency, char data) {
        this.frequency = frequency;
        this.data = data;
        left = right = null;
    }

    public int compareTo(Node node) {
        if (frequency != node.frequency) {
            return frequency - node.frequency;
        } else {
            return node.data - data;
        }
    }
}


public class HuffmanCoding {

    public  Node buildHuffmanTree(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            pq.add(new Node(entry.getValue(), entry.getKey()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.frequency + right.frequency, '-');
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        return pq.poll();
    }

    public void generateCodes(Node root, String code, HashMap<Character, String> codes) {
        if (root == null)
            return;
        if (root.data != '-') {
            codes.put(root.data, code);
        }
        generateCodes(root.left, code + "0", codes);
        generateCodes(root.right, code + "1", codes);
    }

    public HuffmanCodingResult compressWithTree(String filename) {

        File file = new File(filename);

        // Reading the text from the file
        String input = "";
        int ch;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((ch = br.read()) != -1) {
                input += (char) ch;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // Here starts the compression process
        HashMap<Character, Integer> frequencies = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        Node root = buildHuffmanTree(frequencies);
        HashMap<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);

        StringBuilder compressed = new StringBuilder();
        for (char c : input.toCharArray()) {
            compressed.append(codes.get(c));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("compressed.bin"))) {
            writer.write(compressed.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HuffmanCodingResult(compressed.toString(), root);
    }

    public String decompressWithTree(String filename, Node root) {

        File file = new File(filename);

        // Reading the compressed text from the file
        String compressed = "";
        int ch;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((ch = br.read()) != -1) {
                compressed += (char) ch;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Here starts the compression process
        StringBuilder decompressed = new StringBuilder();
        Node current = root;
        for (char bit : compressed.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            if (current == null) {
                System.out.println("Error: Reached a null node");
            }

            if (current != null && current.data != '-') {
                decompressed.append(current.data);
                current = root;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("decompressed.txt"))) {
            writer.write(decompressed.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return decompressed.toString();
    }

    public static void main(String[] args) {
        // HuffmanCoding main = new HuffmanCoding();
        // String originalFile = "original.txt";
        // HuffmanCodingResult compressed = main.compressWithTree(originalFile);
        // System.out.println("Compressed: " + compressed.compressedData);

        // String decompressed = main.decompressWithTree("compressed.txt", compressed.compressionTree);
        // System.out.println("Decompressed: " + decompressed);
    }
}

