package app;

public class stateOutcomeTracker extends OutcomeTracker{
    private int stateCode;
    private String stateName;

    public void setState(String stateName, int stateCode){
        this.stateName = stateName;
        this.stateCode = stateCode;
    }

    public String getStateName(){
        return this.stateName;
    }

    public int getStateCode(){
        return this.stateCode;
    }

}
