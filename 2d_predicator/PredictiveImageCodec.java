import java.util.*;
import java.io.*;
import java.nio.*;

public class PredictiveImageCodec {

    String ImagePath;
    Quantizer quantizer;
    int ImageWidth;
    int ImageHeight;

    public PredictiveImageCodec(String Path) {
        this.SetImagePath(Path);
        quantizer = new Quantizer();
        this.ImageHeight = 0;
        this.ImageWidth = 0;
    }

    public void SetImagePath(String Path) {
        this.ImagePath = Path;
    }

    public String GetImagePath() {
        return this.ImagePath;
    }

    public void Compress(String FilePath) {
        ImageRW temp = new ImageRW(this.ImagePath);
        int[][] OriginalPixels = temp.readImage();

        this.ImageHeight = OriginalPixels.length;
        this.ImageWidth = OriginalPixels[0].length;

        int[][] PredictedPixels = new int[ImageHeight][ImageWidth];
        int[][] Difference = new int[ImageHeight][ImageWidth];
        int[][] QuantizedDifference = new int[ImageHeight][ImageWidth];

        // generating the Difference 2d array
        for (int i = 0; i < ImageHeight; i++) {
            for (int j = 0; j < ImageWidth; j++) {

                // saving the inital pixels which are needed for predection process
                if (i == 0 || j == 0) {
                    PredictedPixels[i][j] = Difference[i][j] = QuantizedDifference[i][j] = OriginalPixels[i][j];
                } else {
                    PredictedPixels[i][j] = this.PredictValue(OriginalPixels[i][j - 1], OriginalPixels[i - 1][j - 1], OriginalPixels[i - 1][j]); // a,b,c
                    Difference[i][j] = OriginalPixels[i][j] - PredictedPixels[i][j];
                }
            }
        }

        quantizer.generateTable(Difference, 4);

        for (int i = 1; i < ImageHeight; i++)
            for (int j = 1; j < ImageWidth; j++)
                QuantizedDifference[i][j] = quantizer.quantize(Difference[i][j]);

        WriteCompressedData(FilePath, QuantizedDifference);
    }

    public void decompress(String FilePath, String OutputFile) {
        try (FileInputStream fileInputStream = new FileInputStream(FilePath);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            int width = dataInputStream.readInt();
            int height = dataInputStream.readInt();

            int[][] quantizedDifference = new int[height][width];
            int[][] dequantizedDifference = new int[height][width];
            int[][] decodeImage = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int x = dataInputStream.readInt();
                    if (i == 0 || j == 0) {
                        dequantizedDifference[i][j] = x;
                    }
                    quantizedDifference[i][j] = x;
                }
            }

            ArrayList<QuantizationLevel> Table = new ArrayList<QuantizationLevel>();
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                QuantizationLevel row = new QuantizationLevel();
                row.setQ(dataInputStream.readInt());
                row.setQ_(dataInputStream.readInt());
                Table.add(row);
            }

            for (int i = 1; i < height; i++) {
                for (int j = 1; j < width; j++) {
                    dequantizedDifference[i][j] = Quantizer.getRowByQ(quantizedDifference[i][j], Table).getQ_();
                }
            }

            int x;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == 0 || j == 0) {
                        decodeImage[i][j] = quantizedDifference[i][j];
                    } else {
                        x = this.PredictValue(decodeImage[i][j - 1], decodeImage[i - 1][j - 1], decodeImage[i - 1][j]); // a,b,c
                        decodeImage[i][j] = x + dequantizedDifference[i][j];

                        if (decodeImage[i][j] < 0)
                            decodeImage[i][j] = 0;
                        if (decodeImage[i][j] > 255)
                            decodeImage[i][j] = 255;
                    }
                }
            }

            ImageRW.writeImage(decodeImage, width, height, OutputFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int PredictValue(int a, int b, int c) {
        int PredictedValue = 0;

        if (b <= Math.min(a, c))
            PredictedValue = Math.max(a, c);

        else if (b >= Math.max(a, c))
            PredictedValue = Math.min(a, c);

        else
            PredictedValue = a + c - b;

        return PredictedValue;
    }

    public void WriteCompressedData(String FilePath, int[][] QuantizedDifference) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(FilePath);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeInt(ImageWidth);
            dataOutputStream.writeInt(ImageHeight);
            for (int i = 0; i < ImageHeight; i++) {
                for (int j = 0; j < ImageWidth; j++) {
                    dataOutputStream.writeInt(QuantizedDifference[i][j]);
                }
            }
            dataOutputStream.writeInt(quantizer.getTable().size());
            for (QuantizationLevel row : quantizer.getTable()) {
                dataOutputStream.writeInt(row.getQ());
                dataOutputStream.writeInt(row.getQ_());
            }

            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
