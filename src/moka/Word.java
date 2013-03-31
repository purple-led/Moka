package moka;

public class Word {
    public Word(String rus, String eng, int r2e, int e2r, boolean isA){
        russian = rus;
        english = eng;
        value_r2e = r2e;
        value_e2r = e2r;
        isActive = isA;
    }
    
    public String russian;
    public String english;
    public int value_r2e;
    public int value_e2r;
    public boolean isActive;
    
    public String toString(){
        return "word: " + this.russian + "  " + this.english + " "
                + this.value_r2e + " " + this.value_e2r + "  isactive:  " + isActive;
    }
}
