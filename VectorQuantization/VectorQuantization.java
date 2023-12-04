import java.util.Vector;
import java.io.*;

public class VectorQuantization {
    public static void main(String[] args) {
        ImageReadWrite image = new ImageReadWrite();
        int[][] pixels = image.readImage("/home/mina/repos/Data-Compression-Algorithms-using-Java/VectorQuantization/test.jpg");
        // for (int i = 0; i < pixels.length; i++) {
        // for (int j = 0; j <pixels[i].length; j++) {
        //
        // System.out.print(pixels[i][j]);
        // System.out.print(" ");
        // }
        // System.out.println();
        // }
        System.out.println(image.height);
        System.out.println(image.width);
    }
}
