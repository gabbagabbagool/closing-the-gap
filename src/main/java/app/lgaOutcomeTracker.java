package app;
/**
 * Class to track lga outcomes
 */
public class lgaOutcomeTracker extends OutcomeTracker{
    private int lgaCode;
    private String lgaName;
    /**
     * 
     * @param lgaName Name of lga
     * @param lgaCode Code of lga
     */
    public void setLga(String lgaName, int lgaCode){
        this.lgaName = lgaName;
        this.lgaCode = lgaCode;
    }
    /**
     * 
     * @return lga name
     */
    public String getLgaName(){
        return this.lgaName;
    }
    /**
     * 
     * @return lga code
     */
    public int getLgaCode(){
        return this.lgaCode;
    }

}
