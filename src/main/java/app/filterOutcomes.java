package app;

public class filterOutcomes {
    public int areaCode;
    public String areaName;
    public String outcome1IndigRaw;
    public String outcome5IndigRaw;
    public String outcome6IndigRaw;
    public String outcome8IndigRaw;
    public String outcome1NonRaw;
    public String outcome5NonRaw;
    public String outcome6NonRaw;
    public String outcome8NonRaw;
    public String outcome1IndigFrac;
    public String outcome5IndigFrac;
    public String outcome6IndigFrac;
    public String outcome8IndigFrac;
    public String outcome1NonFrac;
    public String outcome5NonFrac;
    public String outcome6NonFrac;
    public String outcome8NonFrac;
    public String outcome1Gap;
    public String outcome5Gap;
    public String outcome6Gap; 
    public String outcome8Gap;
    public String outcome1Overall;
    public String outcome5Overall;
    public String outcome6Overall; 
    public String outcome8Overall;

    // value = count
    public void setOutcomes(String outcome, String value, String proportion, String population){
        switch(outcome){
            case "1Indig":
                this.outcome1IndigRaw = value;
                this.outcome1IndigFrac = proportion;
                break;
            case "5Indig":
                this.outcome5IndigRaw = value;
                this.outcome5IndigFrac = proportion;
                break;
            case "6Indig":
                this.outcome6IndigRaw = value;
                this.outcome6IndigFrac = proportion;
                break;
            case "8Indig":
                this.outcome8IndigRaw = value;
                this.outcome8IndigFrac = proportion;
                break;
            case "1Non":
                this.outcome1NonRaw = value;
                this.outcome1NonFrac = proportion;
                break;
            case "5Non":
                this.outcome5NonRaw = value;
                this.outcome5NonFrac = proportion;
                break;
            case "6Non":
                this.outcome6NonRaw = value;
                this.outcome6NonFrac = proportion;
                break;
            case "8Non":
                this.outcome8NonRaw = value;
                this.outcome8NonFrac = proportion;
                break;
        }
    }
    
    // set the gap value per outcome
    public void setGap() {
        try {
            this.outcome1Gap = String.valueOf(String.format("%.1f", Double.parseDouble(this.outcome1NonFrac) - Double.parseDouble(this.outcome1IndigFrac)));
        } catch (Exception e) {
            this.outcome1Gap = "NA Gap";
        }
        try {
            this.outcome5Gap = String.valueOf(String.format("%.1f", Double.parseDouble(this.outcome5NonFrac) - Double.parseDouble(this.outcome5IndigFrac)));
        } catch (Exception e) {
            this.outcome5Gap = "NA Gap";
        }
        try {
            this.outcome6Gap = String.valueOf(String.format("%.1f", Double.parseDouble(this.outcome6NonFrac) - Double.parseDouble(this.outcome6IndigFrac)));
        } catch (Exception e) {
            this.outcome6Gap = "NA Gap";
        } 
        try {
            this.outcome8Gap = String.valueOf(String.format("%.1f", Double.parseDouble(this.outcome8NonFrac) - Double.parseDouble(this.outcome8IndigFrac)));
        } catch (Exception e) {
            this.outcome8Gap = "NA Gap";
        } 
    }
}
