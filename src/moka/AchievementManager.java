package moka;

/**
 *
 * @author EvilZerg
 */
public class AchievementManager {
    private LogicEngine engine;
    private JournalManager journal;
    private int NMB_OF_ACH = 15;
    
    public Achievement achivements[];
    
    public AchievementManager(LogicEngine engine, JournalManager journal){
        this.engine = engine;
        this.journal = journal;
        achivements = new Achievement[NMB_OF_ACH];
    }
    
    public void testAchivements(){
    }
}
