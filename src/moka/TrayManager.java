package moka;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static moka.LogicEngine.createImage;

/**
 *
 * @author EvilZerg
 */
public class TrayManager {
    private LogicEngine engine;
    private CheckboxMenuItem CheckBoxTrayQuiz;
    
    public TrayManager(LogicEngine engine){
        this.engine = engine;
        if (SystemTray.isSupported()){
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {createAndShowGUI();}
            });
        }
    }
    
    public void setCheckBoxTrayQuiz(boolean isActive){
        CheckBoxTrayQuiz.setState(isActive);   
    }
    
    private void createAndShowGUI(){
        final PopupMenu popup = new PopupMenu();

        final SystemTray tray = SystemTray.getSystemTray();
        double height_icon = tray.getTrayIconSize().getHeight();
        
        String path_icon;
        
        System.out.println(height_icon);
        if(height_icon == 24) path_icon = "moka_logo_24.png";
        else if(height_icon == 16) path_icon = "moka_logo_16.png";
        else path_icon = "moka_logo.png";
        
        final TrayIcon trayIcon = new TrayIcon(createImage(path_icon));
        trayIcon.setImageAutoSize(true);
        
        // Create a popup menu components
        MenuItem MainWindowItem = new MenuItem("Main window");
        MenuItem startNewQuizItem = new MenuItem("Start quiz");
        MenuItem addNewWordItem = new MenuItem("Add new word");
        CheckBoxTrayQuiz = new CheckboxMenuItem("Auto question");
        CheckBoxTrayQuiz.setState(engine.irkManager.isActive());
        MenuItem exitItem = new MenuItem("Exit");
        
        /*MenuItem settingsItem = new MenuItem("Settings");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        */
        
        //Add components to popup menu
        popup.add(MainWindowItem);
        popup.addSeparator();
        popup.add(addNewWordItem);
        popup.add(startNewQuizItem);
        popup.add(CheckBoxTrayQuiz);
        popup.addSeparator();
        popup.add(exitItem);
  
        /*
        popup.add(settingsItem);
        popup.addSeparator();
        popup.add(cb2);
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        */
        
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
        
       MainWindowItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.runMainGUI();
                tray.remove(trayIcon);
            }
        });
       
        startNewQuizItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.startQuiz();
            }
        });
       
        addNewWordItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.addNewWord();
            }
        });
        
        CheckBoxTrayQuiz.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                engine.activateTrayQuiz(cb1Id == ItemEvent.SELECTED);
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                engine.closeApplication();
            }
        });
        
        /*
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
    }      
}
