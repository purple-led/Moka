package moka;

/**
 *
 * @author EvilZerg
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class DictionaryPanel extends JPanel {
//    private boolean DEBUG = false;
    private static LogicEngine engine;
    
    public DictionaryPanel(LogicEngine engine) {
        super(new GridLayout(1,0));
 
        this.engine = engine;
        
        String[] columnNames = {"English",
                                "Russian",
                                "isActive"};
 
        Object[][] data = new Object[engine.data.length][3];
        
        for(int i=0; i<engine.data.length; i++){
            data[i][0] = engine.data[i].english;
            data[i][1] = engine.data[i].russian;
            data[i][2] = new Boolean(engine.data[i].isActive);
        }
 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(420, 110));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
 /*
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
 
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
 */
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
   /*
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Dictionary");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
        DictionaryPanel newContentPane = new DictionaryPanel(engine);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        //Display the window.
        frame.setType(java.awt.Window.Type.UTILITY);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 */
 /*
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
*/
}

