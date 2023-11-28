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
    private HuffmanCodingResult result ;

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
                    HuffmanCoding main = new HuffmanCoding();
                    result = main.compressWithTree(selectedFile.getAbsolutePath());
                    // String currentPath = System.getProperty("user.dir");
                    JOptionPane.showMessageDialog(frame, "File compressed successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a file first.");
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    HuffmanCoding main = new HuffmanCoding();
                    // String currentPath = System.getProperty("user.dir");
                    // currentPath += "/decompressed.txt";
                    main.decompressWithTree(selectedFile.getAbsolutePath(), result.compressionTree);
                    JOptionPane.showMessageDialog(frame, "File decompressed successfully.");
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
