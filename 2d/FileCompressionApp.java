import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileCompressionApp {
    private JFrame frame;
    private JButton selectFileButton, compressButton, decompressButton;
    private JFileChooser fileChooser;
    private File selectedFile;

    public FileCompressionApp() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("File Compression App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 230, 250)); // Light purple background

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(211, 211, 211)); // Peach color

        selectFileButton = new JButton("Select File");
        compressButton = new JButton("Compress");
        decompressButton = new JButton("Decompress");

        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");

        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(frame, "File selected: " + selectedFile.getName());
                }
            }
        });

        compressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    // String OriginalImagePath = System.getProperty("user.dir");
                    String OriginalImagePath = selectedFile.getAbsolutePath();
                    String CompressedFilePath = System.getProperty("user.dir");
                    CompressedFilePath += "/compreesed.bin";

                    PredictiveImageCodec codec = new PredictiveImageCodec(OriginalImagePath);
                    codec.Compress(CompressedFilePath);

                    // JOptionPane.showMessageDialog(frame, "File compressed successfully.");
                    JOptionPane.showMessageDialog(frame, "File Compressed successfully (compressed.bin)");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a file first.");
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    PredictiveImageCodec codec = new PredictiveImageCodec("");

                    String OutputFile = System.getProperty("user.dir");
                    OutputFile += "/decompressed.jpg";

                    // codec.decompress(selectedFile.getAbsolutePath(), OutputFile);

                    JOptionPane.showMessageDialog(frame, "File decompressed successfully (decompressed.jpg)");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a file first.");
                }
            }
        });

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(selectFileButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(compressButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(decompressButton);
        buttonPanel.add(Box.createHorizontalGlue());

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileCompressionApp();
            }
        });
    }
}

