package app;
import java.util.HashMap;

public class OutcomeTracker {
    private HashMap <Integer, Integer> RawOutcomes = new HashMap<Integer, Integer>();
    private HashMap <Integer, Integer> ProportionalOutcomes = new HashMap<Integer, Integer>();

    public OutcomeTracker(){
        for(int i = 0; i < 4; ++i){
            this.RawOutcomes.put(i, 0);
            this.ProportionalOutcomes.put(i, 0);
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
    public void setOutcomes(String myType, int outcome, int myValue){
        if(myType == "raw"){
            this.RawOutcomes.put(outcome, myValue);
        }
        else{
            this.ProportionalOutcomes.put(outcome, myValue);
        }
    }

    public int getOutcomeMetric(String myType, int outcome){
        int myValue;
        if (myType == "raw"){
            myValue = this.RawOutcomes.get(outcome);
        }
        else{
            myValue = this.ProportionalOutcomes.get(outcome);
        }
        return myValue;
    }
}