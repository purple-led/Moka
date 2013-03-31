package moka;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author EvilZerg
 */
public class TrayManager {
    private LogicEngine engine;
    
    public TrayManager(LogicEngine engine){
        this.engine = engine;
        if (SystemTray.isSupported()){
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {createAndShowGUI();}
            });
        }
    }
    
    private void createAndShowGUI(){
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
               new TrayIcon(createImage("moka_logo_16.png", "tray icon"));
        //trayIcon.setImageAutoSize(true);
        
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem addNewWordItem = new MenuItem("Add new word");
        MenuItem startNewQuizItem = new MenuItem("Start quiz");
        //MenuItem settingsItem = new MenuItem("Settings");
        //CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        //CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        //Menu displayMenu = new Menu("Display");
        //MenuItem errorItem = new MenuItem("Error");
        //MenuItem warningItem = new MenuItem("Warning");
        //MenuItem infoItem = new MenuItem("Info");
        //MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(addNewWordItem);
        popup.add(startNewQuizItem);
        //popup.add(settingsItem);
        //popup.addSeparator();
        //popup.add(cb1);
        //popup.add(cb2);
        //popup.addSeparator();
        //popup.add(displayMenu);
        //displayMenu.add(errorItem);
        //displayMenu.add(warningItem);
        //displayMenu.add(infoItem);
        //displayMenu.add(noneItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.runMainGUI();
                tray.remove(trayIcon);
            }
        });
        
        addNewWordItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.addNewWord();
            }
        });
        
        startNewQuizItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.startQuiz();
            }
        });
        
        /*cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });
        
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED){
                    trayIcon.setToolTip("Sun TrayIcon");
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    
                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    
                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };
        
        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);
        */
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                engine.closeApplication();
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayManager.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }        
}
