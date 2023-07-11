import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.FileNotFoundException;

public class CsvReader {
    //Stores the elements to be loaded into the data object and the columnNames for the table
    private ArrayList<String[]> elements = new ArrayList<String[]>();
    private JTable table = new JTable();
    //Stores the table data
    private Object[][] data;
    //Stores the number of columns for the loops
    private int columns;

    /*
     * Loads the table data from a file.
     */
    public void setTable(File file){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            String[] colNames = null;
            boolean firstLoop = true;
            while((line = br.readLine()) != null){
                String[] splitString = line.split(",");
                if (firstLoop){
                    colNames = splitString;
                    firstLoop = !firstLoop;
                }
                else {
                    elements.add(splitString);
                    columns = splitString.length;
                }
            }

            data = new Object[elements.size()][columns];
            for (int i = 0; i < elements.size(); i++) {
                for (int j = 0; j < columns; j++){
                    data[i][j] = elements.get(i)[j];
                }
            }
            table.setModel(new DefaultTableModel(data, colNames));
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(JLabel.RIGHT);
            for (int i = 0; i < columns; i++){
                table.getColumnModel().getColumn(i).setCellRenderer(render);
            }
        } 
        catch (FileNotFoundException ex) {
            // TODO: handle FNFexception
            System.out.println("CSVReader: File not found.");
        } 
        catch (IOException ex) {
            // TODO: handle IOexception
            System.out.println("CSVReader: IO Error");
        }
        catch (NullPointerException ex){
            //TODO: handle NPexception
            System.out.println("CSVReader: File was null");
        }
    }
    public JTable getTable(File file) throws FileNotFoundException{
        if (file == null){
            String msg = "CSVReader: File not found";
            throw new FileNotFoundException(msg);
        }
        return table;
    }
}
