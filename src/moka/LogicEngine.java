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
    public IrkManager irkManager;
    public Word[] data;
    private MainWindow slave;
    private TrayManager tray;
    public JournalManager journal;
    public AchievementManager achivements;
    public SettingsManager settings;
            
    public LogicEngine(){
        parser = new DataParser(this);
        settings = new SettingsManager(this);
        journal = new JournalManager(this);
        irkManager = new IrkManager(this);
        achivements = new AchievementManager(this, journal);
    }
    
    public void run(){
        runMainGUI();
    }
    
    public void addNewWord(){
        setMainGUIEnable(false);
        new AddNewWordWindow(this).setVisible(true);
    }
    
    public void showDictionary(){
        setMainGUIEnable(false);
        new DictionaryWindow(this);
    }
    
    public void addNewWord(Word newWord){
        parser.addNode(newWord);
    }
    
    public void setMainGUIEnable(boolean enable){
        slave.setEnabled(enable);
        if (enable){
            slave.toFront(); 
            slave.requestFocus();
        }
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
        int len = 0;
        int countTrueAnswers = 0;

        while (word[len]!=-1) len++;
        for(int i = 0; i < len; i ++) if(results[i]) countTrueAnswers ++;
        
        // Update the dictionary data
        for(int i=0; i<len; i++){
            if (results[i]){
               if (isE2R[i]) data[word[i]].value_e2r++;
               else          data[word[i]].value_r2e++;
               
               if ((data[word[i]].value_e2r * data[word[i]].value_r2e) == 4)
                   data[word[i]].isActive = false;
            } else {
               if (isE2R[i]) data[word[i]].value_e2r = 0;
               else          data[word[i]].value_r2e = 0;
            }
            parser.changeNode(data[word[i]], data[word[i]]);
        }
        
        // Adding new event in journal
        if (isQuiz)
            for(int i=0; i<len; i++){
                journal.addNewEvent("quiz", word[i], isE2R[i], results[i]);
            }
        else journal.addNewEvent("trayquiz", word[0], isE2R[0], results[0]);
        
        // Create content for windows with results
        String resMessage = "<html><table border = \"0\">";
        String opinionMessage = "";

        for(int i=0; i<len; i++){
            resMessage += "<tr><td>";

            if ((results[i])&&(isE2R[i]))  resMessage += "<font color=green>";
            else
            if ((!results[i])&&(isE2R[i])) resMessage += "<font color=red>";
            else                           resMessage += "<font>";
           
            resMessage += data[word[i]].russian;
            resMessage += "</font></td><td>";
            
            if ((results[i])&&(!isE2R[i]))  resMessage += "<font color=green>";
            else
            if ((!results[i])&&(!isE2R[i])) resMessage += "<font color=red>";
            else                           resMessage += "<font>";
            
            resMessage += data[word[i]].english;
            resMessage += "</font></td></tr>";
        }
        resMessage += "</table></html>";
        
        if(countTrueAnswers == len) opinionMessage = "Nice job!";
        else if(countTrueAnswers == 0) opinionMessage = "You need to study harder!";
        else if(countTrueAnswers == len - 1) opinionMessage = "You could be better!";
        else opinionMessage = "You got me, guy!";   
        
        setMainGUIEnable(false);
        new ResultsWindow(this, "Results", resMessage, opinionMessage);
        
        if(!isQuiz) updateTrayQuiz();
    }
    
    public void clearWords(){
        parser.clearData();
    }
    
    public void closeApplication(){
        System.exit(0);
    }
}