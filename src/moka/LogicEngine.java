package moka;

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
        irkManager.setActivate(false);
        runMainGUI();
    }
    
    public void addNewWord(){
        irkManager.setActivate(false);
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
        irkManager.setActivate(true);
    }
    
    public void runMainGUI(){
        irkManager.setActivate(false);
        new MainWindow(this).setVisible(true);
    }
    
    public void startQuiz(){
        irkManager.setActivate(false);
        new QuizManager(this).startQuiz();
    }
    
    public void startTrayQuiz(){
        irkManager.setActivate(false);
        new QuizManager(this).startTrayQuiz();
    }
    
    public void handleResultsOfQuiz(int word[], boolean results[], boolean isE2R[], boolean isQuiz){
        int len = word.length;
        System.out.println("---");
        for(int i=0; i < len; i++)
            System.out.println(word[i] + " " + results[i] + " " + isE2R[i]);
        
        if(!isQuiz) irkManager.setActivate(true);
    }
    
    private void handleAnswer(int id, boolean result, boolean isE2R){
        
    }
    
    public void closeApplication(){
        System.exit(0);
    }
}