package moka;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author EvilZerg
 */
public class JournalManager {
    private LogicEngine engine;
    private List<Event> journal;
    
    public JournalManager(LogicEngine engine){
        this.engine = engine;
        journal = new ArrayList<Event>();
    }
    
    public void addNewEvent(String type, int id, boolean isE2R){
        if (type.equals("gtfo")){
            addNewEvent(type, id, isE2R, false);
        }
    }
    
    public void addNewEvent(String type, int id, boolean isE2R, boolean isCorrect){
        journal.add(new Event((new Date()).toString(),type, id, isE2R, isCorrect));
    }
    
    public List<Event> getEvents(){
        return journal;
    }   
}
