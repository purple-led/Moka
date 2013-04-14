package moka;

/**
 *
 * @author EvilZerg
 */
public class Achievement {
    public boolean isUnlocked;
    public String name;
    public String description;
    public String congratulation;
    
    public Achievement(){
        //setting default fields
        isUnlocked = false;
        name = "default achivement";
        description = "default description";
        congratulation = "default congratulation";
    }
    
    public Achievement(boolean isUnlocked, String name, String description, String congratulation){
        this.isUnlocked = isUnlocked;
        this.name = name;
        this.description = description;
        this.congratulation = congratulation; 
    }
            
    
    public void testAchivement(JournalManager journal){
        //do nothing
        //this method should be virtual
        //moreover the whole job of this method should be done by
        //achivemenet manager
    }   
}
