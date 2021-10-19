package app;
/**
 * Class to track state outcomes
 */
public class stateOutcomeTracker extends OutcomeTracker{
    private int stateCode;
    private String stateName;

    /**
     * 
     * @param stateName Name of state
     * @param stateCode Code of state (first digit of lga16_code)
     */
    public void setState(String stateName, int stateCode){
        this.stateName = stateName;
        this.stateCode = stateCode;
    }

    /**
     * 
     * @return State name
     */
    public String getStateName(){
        return this.stateName;
    }
    /**
     * 
     * @return State code
     */
    public int getStateCode(){
        return this.stateCode;
    }

}
