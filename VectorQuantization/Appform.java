import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Appform {
    private JButton compressButton;
    private JPanel panel;
    private JTextField compressPathTextField;
    private JButton browseButton;
    private JButton decompressButton;
    private JTextField decompressPathTextField;
    private JSpinner vHeightSpinner;
    private JSpinner vWidthSpinner;
    private JSpinner vSizeSpinner;
    private JScrollPane imagePlaceholder;
    private JButton originalCompressedButton;
    private File compressFile;
    private File decompressFile;
    private BufferedImage originalImage;
    private BufferedImage compressedImage;
    private JLabel image = new JLabel();
    private boolean compressedImgActive = false;

    private void switchImage(Boolean isCompressed) {
        if (isCompressed) {
            if (compressedImage == null)
                return;
            image.setIcon(new ImageIcon(compressedImage));
            image.setHorizontalAlignment(JLabel.CENTER);
            imagePlaceholder.getViewport().add(image);
            compressedImgActive = true;
        } else {
            if (originalImage == null)
                return;
            image.setIcon(new ImageIcon(originalImage));
            image.setHorizontalAlignment(JLabel.CENTER);
            imagePlaceholder.getViewport().add(image);
            compressedImgActive = false;
        }
        if (originalImage != null && compressedImage != null)
            originalCompressedButton.setEnabled(true);
    }

    public Appform() {

        vHeightSpinner = new JSpinner();
        vWidthSpinner = new JSpinner();
        vSizeSpinner = new JSpinner();

        vHeightSpinner.setValue(2);
        vWidthSpinner.setValue(2);
        vSizeSpinner.setValue(64);
        

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String path = compressFile.getAbsolutePath();
                    // Replace the next line with your own compression logic
                    // YourCompressionClass.compress((int) vHeightSpinner.getValue(), (int)
                    // vWidthSpinner.getValue(), (int) vSizeSpinner.getValue(), path);
                    // Replace the next line with your own decompression logic
                    // YourCompressionClass.decompress(YourCompressionClass.getCompressedPath(path));
                    compressedImage = ImageIO.read(new File(path)); // Update this line based on your decompression
                                                                    // logic
                    switchImage(true);
                    JOptionPane.showMessageDialog(null, "Your compression message here");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred. Your error message here");
                }
            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser browser = new JFileChooser();
                browser.showOpenDialog(null);
                compressFile = browser.getSelectedFile();
                compressPathTextField.setText(compressFile.getAbsolutePath());
                try {
                    originalImage = ImageIO.read(compressFile);
                    switchImage(false);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unreadable Image, please select an Image");
                }
            }
        });
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String path = decompressFile.getAbsolutePath();
                    // Replace the next line with your own decompression logic
                    // YourDecompressionClass.decompress(path);
                    compressedImage = ImageIO.read(new File(path)); // Update this line based on your decompression
                                                                    // logic
                    switchImage(true);
                    JOptionPane.showMessageDialog(null, "Your decompression message here");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred. Your error message here");
                }
            }
        });
        originalCompressedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchImage(!compressedImgActive);
            }
        });
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Your Application Title");
        jFrame.setContentPane(new Appform().panel);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
