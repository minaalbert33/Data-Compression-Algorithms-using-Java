import java.util.Vector;

public class VectorQuantization {
    public static void main(String[] args) {
        int[][] image = ImageReadWrite.readImage("/mnt/D/Mina/Wallpapers/3.3.3.3.jpg");
        System.out.println(image.length);
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.print(image[i][j] + ' ');
            }
            System.out.println();
        }
    }
}
