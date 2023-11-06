import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DataStreamsApp extends JFrame {
    private JTextArea originalTextArea;
    private JTextArea filteredTextArea;
    private JTextField searchTextField;
    private JLabel searchLabel;

    public DataStreamsApp() {
        originalTextArea = new JTextArea(20, 40);
        filteredTextArea = new JTextArea(20, 40);
        searchTextField = new JTextField(20);
        searchLabel = new JLabel("Search String:");

        JButton loadButton = new JButton("Load a file");
        JButton searchButton = new JButton("Search the file");
        JButton quitButton = new JButton("Quit");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFile();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DataStreams Application");
        setLayout(new FlowLayout());
        add(loadButton);
        add(searchButton);
        add(quitButton);
        add(searchLabel);
        add(searchTextField);
        add(new JScrollPane(originalTextArea));
        add(new JScrollPane(filteredTextArea));
        pack();
        setVisible(true);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Path filePath = fileChooser.getSelectedFile().toPath();
                Stream<String> lines = Files.lines(filePath);

                originalTextArea.setText("");
                lines.forEach(line -> originalTextArea.append(line + "\n"));
                lines.close();
            } catch (IOException e) {
                handleFileIOException(e);
            }
        }
    }

    private void searchFile() {
        String searchStr = searchTextField.getText().trim();
        searchLabel.setText("Search String: " + searchStr);

        String originalText = originalTextArea.getText();

        if (!searchStr.isEmpty()) {
            List<String> searchTerms = Arrays.asList(searchStr.split("\\s+"));

            Stream<String> filteredLines = Stream.of(originalText.split("\n"))
                    .filter(line -> containsAnyTerm(line, searchTerms));

            filteredTextArea.setText("");
            filteredLines.forEach(line -> filteredTextArea.append(line + "\n"));
        }
    }

    private boolean containsAnyTerm(String line, List<String> searchTerms) {
        for (String term : searchTerms) {
            if (line.contains(term)) {
                return true;
            }
        }
        return false;
    }

    private void handleFileIOException(IOException e) {
        JOptionPane.showMessageDialog(this, "An error occurred while reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DataStreamsApp());
    }
}
