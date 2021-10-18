package app;
import java.util.HashMap;

public class OutcomeTracker {
    private int lgaCode;
    private String lgaName;
    private HashMap <Integer, Integer> RawOutcomes = new HashMap<Integer, Integer>();
    private HashMap <Integer, Integer> ProportionalOutcomes = new HashMap<Integer, Integer>();

    public OutcomeTracker(){
        for(int i = 0; i < 4; ++i){
            this.RawOutcomes.put(i, 0);
            this.ProportionalOutcomes.put(i, 0);
        }
    }

    /**
    * Takes type of outcome, outcome number, and outcome value.
    * <p>
    * Sets the value at the location specified
    */
    public void setOutcomes(String myType, int outcome, int myValue){
        if(myType == "raw"){
            this.RawOutcomes.put(outcome, myValue);
        }
        else{
            this.ProportionalOutcomes.put(outcome, myValue);
        }
    }

    public void setLga(String lgaName, int lgaCode){
        this.lgaName = lgaName;
        this.lgaCode = lgaCode;
    }

    public String getLgaName(){
        return this.lgaName;
    }
}
