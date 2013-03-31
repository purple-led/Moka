package moka;

import java.util.Random;

/**
 *
 * @author EvilZerg
 */
public class QuizManager {
    private LogicEngine engine;
    private Word[] data;
    private int nQuizAnswer = 5;
    private int nActiveWords = 0;
    private Random randomGenerator = new Random();
    private int usedWords[];
    private boolean quizResults[];
    private boolean isE2R[];
    private boolean isQuiz;
    private int iStep = -1;

    private String NO_WORDS_TITLE = "No words";
    private String NO_WORDS_MSG = "I can't teach you without any words. So, add new words to the dictionary.";
    
    public QuizManager(LogicEngine engine){
        this.engine = engine;
        data = engine.getData();
        
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
    
    public void startTest(){
        if(nActiveWords == 0) {new InformWindow(NO_WORDS_TITLE, NO_WORDS_MSG, "OK").setVisible(true); return;}
        
        int nAnswer = 1;
        if (isQuiz) nAnswer = nQuizAnswer;
        
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
                
                if (data[carr].value_e2r==2) isE2R[iStep++] = true;
                else if (data[carr].value_r2e==2) isE2R[iStep++] = false;
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
            if (answer.equals(data[carr].russian)) quizResults[iStep] = true;
            else quizResults[iStep] = false;
        } else {
            if (answer.equals(data[carr].english)) quizResults[iStep] = true;
            else quizResults[iStep] = false;
        }
        iStep++;
        startTest();
    }
    
    private boolean isIncluded(int array[], int elem){
        for(int i=0; i<array.length; i++)
            if (array[i]==elem) return true;
        return false;
    }
    
    private int getNumberOfQuestionsInQuiz(){
        int res = 0;
        int nAnswer = 1;
        if (isQuiz) nAnswer = nQuizAnswer;
        
        for(int i=0; i<nAnswer; i++)
             if (usedWords[i]!=-1)
                 res++;
        return res;
    }
    
    private void askQuestion(int i, boolean isE2R){
        String word;
        String question;

        if(isE2R) word = data[i].english; else word = data[i].russian;
        question = "Hey man! What does " + word + " mean?";

        new AskWindow(this, word, question, !isQuiz).setVisible(true);
    }
}
