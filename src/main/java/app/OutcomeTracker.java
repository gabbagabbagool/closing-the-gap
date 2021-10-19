package app;
import java.util.HashMap;


/*** 
 * Base class for lga/state outcome tracking
 * Provides Hashmaps that will store a key of outcome number (1,5,6,8) 
 * and a value which is the performance in this outcome.
 * Also provides setter/getter methods for these hashmaps.
 */

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
    * @param myType (raw, proportional) Type of outcome <p> TODO Should we change this to "r" and "p"?
    * <p>
    * @param outcome (1,5,6,8) a single outcome number 
    * <p>
    * @param myValue the value to store for this outcome
    */
    public void setOutcomes(String myType, int outcome, String myValue){
        if(myType == "raw"){
            this.RawOutcomes.put(outcome, myValue);
        }
        else{
            this.ProportionalOutcomes.put(outcome, myValue);
        }
    }
    /**
     * 
     * @param myType (raw, proportional) Type of outcome
     * @param outcome (1,5,6,8) a single outcome number 
     * @return String The value for given outcome, in format provided by myType
     */
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