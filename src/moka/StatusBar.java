package moka;

/**
 *
 * @author EvilZerg
 */
//thanks to unknown man from http://stackoverflow.com
//http://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
public class StatusBar extends javax.swing.JLabel {

    /** Creates a new instance of StatusBar */
    public StatusBar() {
        super();
        super.setPreferredSize(new java.awt.Dimension(100, 16));
        setMessage("Ready");
    }

    public void setMessage(String message) {
        setText(" "+message);        
    }        
}
