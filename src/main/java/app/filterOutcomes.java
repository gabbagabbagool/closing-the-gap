package app;
import java.text.DecimalFormat;

public class filterOutcomes {
    public int areaCode;
    public String areaName;
    public Double outcome1IndigRaw;
    public Double outcome5IndigRaw;
    public Double outcome6IndigRaw;
    public Double outcome8IndigRaw;
    public Double outcome1NonRaw;
    public Double outcome5NonRaw;
    public Double outcome6NonRaw;
    public Double outcome8NonRaw;
    public Double outcome1IndigFrac;
    public Double outcome5IndigFrac;
    public Double outcome6IndigFrac;
    public Double outcome8IndigFrac;
    public Double outcome1NonFrac;
    public Double outcome5NonFrac;
    public Double outcome6NonFrac;
    public Double outcome8NonFrac;
    public Double outcome1Gap;
    public Double outcome5Gap;
    public Double outcome6Gap; 
    public Double outcome8Gap;
    public Double lgaRank;


    public void setOutcomes(String outcome, Double value, Double proportion, Double population){
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
            this.outcome1Gap = Double.parseDouble(String.format("%.1f", (this.outcome1NonFrac - this.outcome1IndigFrac)));
        } catch (Exception e) {
            this.outcome1Gap = null;
        }
        try {
            this.outcome5Gap = Double.parseDouble(String.format("%.1f", (this.outcome5NonFrac - this.outcome5IndigFrac)));
        } catch (Exception e) {
            this.outcome5Gap = null;
        }
        try {
            this.outcome6Gap = Double.parseDouble(String.format("%.1f", (this.outcome6NonFrac - this.outcome6IndigFrac)));
        } catch (Exception e) {
            this.outcome6Gap = null;
        } 
        try {
            this.outcome8Gap = Double.parseDouble(String.format("%.1f", (this.outcome8NonFrac - this.outcome8IndigFrac)));
        } catch (Exception e) {
            this.outcome8Gap = null;
        } 
    }

    // set the ranknig value per outcome
    public void setRanking(String checkboxOutcome1, String checkboxOutcome5, String checkboxOutcome6, String checkboxOutcome8 ) {
        this.lgaRank = 0.0;
        if (checkboxOutcome1 != null) {
            try {
                this.lgaRank += this.outcome1Gap;
            } catch (Exception e) {
                this.lgaRank += 0.0;
            }
        }
        if (checkboxOutcome5 != null) {
            try {
                this.lgaRank += this.outcome5Gap;
            } catch (Exception e) {
                this.lgaRank += 0.0;
            }
        }
        if (checkboxOutcome6 != null) {
            try {
                this.lgaRank += this.outcome6Gap;
            } catch (Exception e) {
                this.lgaRank += 0.0;
            }
        }
        if (checkboxOutcome8 != null) {
            try {
                this.lgaRank -= this.outcome8Gap;
            } catch (Exception e) {
                this.lgaRank += 0.0;
            }
        }
    
    }
}
