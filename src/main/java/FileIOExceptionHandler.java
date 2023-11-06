import javax.swing.*;
import java.io.IOException;

public class FileIOExceptionHandler {
    public static void handleIOException(JFrame parentFrame, IOException exception) {
        JOptionPane.showMessageDialog(parentFrame, "An error occurred while reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
        exception.printStackTrace();
    }
}
