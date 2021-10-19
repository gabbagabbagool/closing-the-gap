package app;

public class lgaOutcomeTracker extends OutcomeTracker{
    private int lgaCode;
    private String lgaName;

    public void setLga(String lgaName, int lgaCode){
        this.lgaName = lgaName;
        this.lgaCode = lgaCode;
    }

    public String getLgaName(){
        return this.lgaName;
    }

    public int getLgaCode(){
        return this.lgaCode;
    }

}
