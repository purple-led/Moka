package moka;

import java.util.Random;

/**
 *
 * @author EvilZerg
 */
public class QuizManager {
    public LogicEngine engine;
    private Word[] data;
    private int nActiveWords = 0;
    private Random randomGenerator = new Random();
    private int usedWords[];
    private boolean quizResults[];
    private boolean isE2R[];
    private boolean isQuiz;
    private int iStep = -1;
    
    private final int N_QUIZ_ANSWER = 5;
    private final String NO_WORDS_TITLE = "No words";
    private final String NO_WORDS_MSG = "I can't teach you without any words. So, add new words to the dictionary.";
    
    public QuizManager(LogicEngine engine){
        this.engine = engine;
        data = engine.data;
        
        for(int i=0; i < data.length; i++){
            if(data[i].isActive) nActiveWords ++;
        }
    }
    
    public void startQuiz(){
        isQuiz = true;
        startTest();
    }
    
    public void startTrayQuiz(){
        isQuiz = false;
        startTest();   
    }
    
    private void startTest(){
        if(nActiveWords == 0){
            new InformWindow(NO_WORDS_TITLE, NO_WORDS_MSG, "OK").setVisible(true);
            engine.noWordsQuiz();
         
            return;
        }
        
        int nAnswer = 1;
        if (isQuiz) nAnswer = N_QUIZ_ANSWER;
        
        if (iStep < 0){
            usedWords = new int[nAnswer];
            quizResults = new boolean[nAnswer];
            isE2R = new boolean[nAnswer];
            for(int i=0; i<nAnswer; i++) usedWords[i] = -1;

            boolean check = false;
            for(int i=0; i<nAnswer; i++){
                int random = randomGenerator.nextInt(data.length);
                int first = random;
                while ((!data[random].isActive)||(isIncluded(usedWords,random))){
                    random = (random+1)%data.length;
                    if(random == first) {check = true; break;}
                }
                if(check) break;
                usedWords[i] = random;
            }
            
            iStep = 0;
            startTest();
        } else if (iStep < getNumberOfQuestionsInQuiz()) {
                int i = iStep;
                int carr = usedWords[i];
                
                if (data[carr].value_e2r==2) isE2R[iStep] = false;
                else if (data[carr].value_r2e==2) isE2R[iStep] = true;
                else isE2R[iStep] = (randomGenerator.nextInt(2)==0);
                askQuestion(carr, isE2R[iStep++]);
        } else {
            engine.handleResultsOfQuiz(usedWords, quizResults, isE2R, isQuiz);
            iStep = -1;
        }
    }
    
    public void setAnswer(String answer){
        iStep--;
        int carr = usedWords[iStep];
        if (isE2R[iStep]){
            if (answer.trim().toLowerCase().equals(data[carr].russian.trim().toLowerCase())) quizResults[iStep] = true;
            else quizResults[iStep] = false;
        } else {
            if (answer.trim().toLowerCase().equals(data[carr].english.trim().toLowerCase())) quizResults[iStep] = true;
            else quizResults[iStep] = false;
        }
        iStep++;
        startTest();
    }
    
    public void handleFuckOff(){
        if(!isQuiz) engine.activateTrayQuiz(false);
    }
    
    public void gtfo(){
        engine.journal.addNewEvent("gtfo", usedWords[iStep], isE2R[iStep]);
    }
    
    private boolean isIncluded(int array[], int elem){
        for(int i=0; i<array.length; i++)
            if (array[i]==elem) return true;
        return false;
    }
    
    private int getNumberOfQuestionsInQuiz(){
        int res = 0;
        int nAnswer = 1;
        if (isQuiz) nAnswer = N_QUIZ_ANSWER;
        
        for(int i=0; i<nAnswer; i++)
             if (usedWords[i]!=-1)
                 res++;
        return res;
    }
    
    private void askQuestion(int i, boolean isE2R){
        String title;
        String word;
        String question;

        if(isQuiz) title = "Quiz"; else title = "Auto question";
        if(isE2R) word = data[i].english; else word = data[i].russian;
        question = "<html><body>Hey man! What does <b>" + word + "</b> mean?</body></html>";
        
        new AskWindow(this, title, question, !isQuiz).setVisible(true);
    }
}
