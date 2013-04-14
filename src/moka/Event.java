package moka;

/**
 *
 * @author EvilZerg
 */
public class Event {
    public Event(String date, String type, int id, boolean isE2R, boolean isCorrect){
        this.date = date;
        this.type = type;
        this.id = id;
        this.isE2R = isE2R;
        this.isCorrect = isCorrect;
    }
    
    public String date;
    public String type;
    public int id;
    public boolean isE2R;
    public boolean isCorrect;
}
