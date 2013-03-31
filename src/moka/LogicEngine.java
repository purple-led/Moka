package moka;

import java.awt.Image;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author EvilZerg
 */
public class LogicEngine{
    public DataParser parser;
    public Journal journal;
    public IrkManager irkManager;
    public Word[] data;
    private MainWindow slave;
    private TrayManager tray;
    
    public LogicEngine(){
        parser = new DataParser();
        journal = new Journal();
        irkManager = new IrkManager(this);

        data = parser.getData();
    }
    
    public void updateData(){
        data = parser.getData();
    }
    
    public Word[] getData(){
        return data;
    }
    
    public void run(){
        runMainGUI();
    }
    
    public void addNewWord(){
        setMainGUIEnable(false);
        new AddNewWordWindow(this).setVisible(true);
    }
    
    public void addNewWord(Word newWord){
        parser.addNode(newWord);
        parser.XMLWrite();
        updateData();
    }
    
    public void setMainGUIEnable(boolean enable){
        slave.setEnabled(enable);
    }
    
    public void runTray(){
        tray = new TrayManager(this);
    }
    
    public void runMainGUI(){
       slave =  new MainWindow(this);
       slave.setVisible(true);
    }
    
    protected static void setGUIIcon(JFrame frame){
        frame.setIconImage(createImage("moka_logo.png"));
    }
    
    //Obtain the image URL
    protected static Image createImage(String path) {
        URL imageURL = TrayManager.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL)).getImage();
        }
    }  
    
    public void startQuiz(){
        new QuizManager(this).startQuiz();
    }
    
    public void noWordsQuiz(){
        activateTrayQuiz(false);
    }
    
    /*
     * Tray Quiz
     */
    public void startTrayQuiz(){
        new QuizManager(this).startTrayQuiz();
    }
    
    public void activateTrayQuiz(boolean isActive){
        tray.setCheckBoxTrayQuiz(isActive);
        irkManager.setActivate(isActive);      
    }
    
    public void updateTrayQuiz(){
        activateTrayQuiz(irkManager.isActive());
    }
    
    public boolean isActiveTrayQuiz(){
        return irkManager.isActive();
    }
    
    /*
     * Handle Quetions
     */
    
    public void handleResultsOfQuiz(int word[], boolean results[], boolean isE2R[], boolean isQuiz){
        int len = word.length;
        
        System.out.println("---");
        for(int i=0; i < len; i++)
            System.out.println(word[i] + " " + results[i] + " " + isE2R[i]);
        
        if(!isQuiz) updateTrayQuiz();
    }
    
    private void handleAnswer(int id, boolean result, boolean isE2R){
        //
    }
    
    public void closeApplication(){
        System.exit(0);
    }
}