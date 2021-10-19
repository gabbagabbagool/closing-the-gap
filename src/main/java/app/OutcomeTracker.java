package app;
import java.util.HashMap;

public class OutcomeTracker {
    private HashMap <Integer, String> RawOutcomes = new HashMap<Integer, String>();
    private HashMap <Integer, String> ProportionalOutcomes = new HashMap<Integer, String>();

    public OutcomeTracker(){
        for(int i = 0; i < 4; ++i){
            this.RawOutcomes.put(i, "");
            this.ProportionalOutcomes.put(i, "");
        }
    }

    /**
    * Input: 
    * <p>
    * type of outcome (raw, proportional) 
    * <p>
    * a single outcome number (1,5,6,8)
    * <p>
    * outcome value (TODO Should proportion be float 0-1)
    * <p>
    * Sets the value at the location specified
    */
    public void setOutcomes(String myType, int outcome, String myValue){
        if(myType == "raw"){
            this.RawOutcomes.put(outcome, myValue);
        }
        else{
            this.ProportionalOutcomes.put(outcome, myValue);
        }
    }

    public String getOutcomeMetric(String myType, int outcome){
        String myValue;
        if (myType == "raw"){
            myValue = this.RawOutcomes.get(outcome);
        }
        else{
            myValue = this.ProportionalOutcomes.get(outcome);
        }
        return myValue;
    }
}