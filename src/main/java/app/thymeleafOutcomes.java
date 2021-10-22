package app;

public class thymeleafOutcomes {
    public int areaCode;
    public String areaName;
    public String outcome1Raw;
    public String outcome5Raw;
    public String outcome6Raw;
    public String outcome8Raw;
    public String outcome1Frac;
    public String outcome5Frac;
    public String outcome6Frac; 
    public String outcome8Frac;

    public void setOutcomes(String outcome, String value){
        switch(outcome){
            case "1r":
                this.outcome1Raw = value;
                break;
            case "5r":
                this.outcome5Raw = value;
                break;
            case "6r":
                this.outcome6Raw = value;
                break;
            case "8r":
                this.outcome8Raw = value;
                break;
            case "1p":
                this.outcome1Frac = value;
                break;
            case "5p":
                this.outcome5Frac = value;
                break;
            case "6p":
                this.outcome6Frac = value;
                break;
            case "8p":
                this.outcome8Frac = value;
                break;
        }
    }
}
