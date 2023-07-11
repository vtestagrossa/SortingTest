import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

public class ReaderApp {
    public static void main(String[] args) throws Exception {
        try {
            CsvReader reader = new CsvReader();
            File file = new File(System.getProperty("user.dir"));
            JFileChooser path = new JFileChooser();
            path.setCurrentDirectory(file);

            path.showOpenDialog(null);
            file = path.getSelectedFile();
            reader.setTable(file);
            JTable table = reader.getTable(file);
            table.setBounds(0, 0, 800, 600);
            JFrame frame = new JFrame("CSV Reader App");
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);
            frame.setSize(800, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (FileNotFoundException ex) {
            // TODO: handle exception
            System.out.println("Main App: No file was selected. Closing Program...");
            System.exit(-1);
        }
    }
}
