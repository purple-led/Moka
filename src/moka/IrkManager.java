package moka;

import javax.swing.*;
import java.awt.event.*;

/** 
 *
 * @author EvilZerg
 */
public class IrkManager {
    private LogicEngine engine;
    private boolean isActive = false;
    private Timer timer = new Timer(0, null);
    private IrkManager irk = this;
    
    private final int DEFAULT_DELAY_MIN = 30;
    
    public IrkManager(LogicEngine engine){
        this.engine = engine;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public void setActivate(boolean isActive){
        this.isActive = isActive;

        if(this.isActive && !timer.isRunning()){
            int delay_s = DEFAULT_DELAY_MIN * 60;

            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    irk.callWindow();
                }
            };

            timer = new Timer(delay_s * 1000, task);
            timer.setRepeats(false);
            timer.start();
        }
        else if(!this.isActive && timer.isRunning()){
            timer.stop();
        }
    }
    
    public void callWindow()
    {
        engine.startTrayQuiz();
    }
}
