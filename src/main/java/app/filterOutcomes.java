package app;

import java.util.Comparator;

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
                this.lgaRank += this.outcome8Gap;
            } catch (Exception e) {
                this.lgaRank += 0.0;
            }
        }
        this.lgaRank =  Double.parseDouble(String.format("%.1f", this.lgaRank));
    }
}

// sort rank Ascending
class sortOutcomeRankAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.lgaRank.compareTo(obj2.lgaRank); 
    }
}

// sort rank Descending
class sortOutcomeRankDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.lgaRank.compareTo(obj1.lgaRank); 
    }
}

// sort outcome 1
class sortOutcome1GapAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome1Gap.compareTo(obj2.outcome1Gap); 
    }
}

class sortOutcome1GapDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome1Gap.compareTo(obj1.outcome1Gap); 
    }
}
// indig raw
class sortOutcome1IndigRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome1IndigRaw.compareTo(obj2.outcome1IndigRaw); 
    }
}

class sortOutcome1IndigRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome1IndigRaw.compareTo(obj1.outcome1IndigRaw); 
    }
}

// Non raw
class sortOutcome1NonRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome1NonRaw.compareTo(obj2.outcome1NonRaw); 
    }
}

class sortOutcome1NonRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome1NonRaw.compareTo(obj1.outcome1NonRaw); 
    }
}

// indig proportional
class sortOutcome1IndigProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome1IndigFrac.compareTo(obj2.outcome1IndigFrac); 
    }
}

class sortOutcome1IndigProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome1IndigFrac.compareTo(obj1.outcome1IndigFrac); 
    }
}

// non proportional
class sortOutcome1NonProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome1NonFrac.compareTo(obj2.outcome1NonFrac); 
    }
}

class sortOutcome1NonProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome1NonFrac.compareTo(obj1.outcome1NonFrac); 
    }
}


// oucome 5
class sortOutcome5GapAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome5Gap.compareTo(obj2.outcome5Gap); 
    }
}

class sortOutcome5GapDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome5Gap.compareTo(obj1.outcome5Gap); 
    }
}
// indig raw
class sortOutcome5IndigRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome5IndigRaw.compareTo(obj2.outcome5IndigRaw); 
    }
}

class sortOutcome5IndigRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome5IndigRaw.compareTo(obj1.outcome5IndigRaw); 
    }
}

// Non raw
class sortOutcome5NonRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome5NonRaw.compareTo(obj2.outcome5NonRaw); 
    }
}

class sortOutcome5NonRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome5NonRaw.compareTo(obj1.outcome5NonRaw); 
    }
}

// indig proportional
class sortOutcome5IndigProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome5IndigFrac.compareTo(obj2.outcome5IndigFrac); 
    }
}

class sortOutcome5IndigProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome5IndigFrac.compareTo(obj1.outcome5IndigFrac); 
    }
}

// non proportional
class sortOutcome5NonProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome5NonFrac.compareTo(obj2.outcome5NonFrac); 
    }
}

class sortOutcome5NonProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome5NonFrac.compareTo(obj1.outcome5NonFrac); 
    }
}

// outcome 6
class sortOutcome6GapAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome6Gap.compareTo(obj2.outcome6Gap); 
    }
}

class sortOutcome6GapDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome6Gap.compareTo(obj1.outcome6Gap); 
    }
}
// indig raw
class sortOutcome6IndigRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome6IndigRaw.compareTo(obj2.outcome6IndigRaw); 
    }
}

class sortOutcome6IndigRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome6IndigRaw.compareTo(obj1.outcome6IndigRaw); 
    }
}

// Non raw
class sortOutcome6NonRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome6NonRaw.compareTo(obj2.outcome6NonRaw); 
    }
}

class sortOutcome6NonRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome6NonRaw.compareTo(obj1.outcome6NonRaw); 
    }
}

// indig proportional
class sortOutcome6IndigProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome6IndigFrac.compareTo(obj2.outcome6IndigFrac); 
    }
}

class sortOutcome6IndigProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome6IndigFrac.compareTo(obj1.outcome6IndigFrac); 
    }
}

// non proportional
class sortOutcome6NonProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome6NonFrac.compareTo(obj2.outcome6NonFrac); 
    }
}

class sortOutcome6NonProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome6NonFrac.compareTo(obj1.outcome6NonFrac); 
    }
}

// outcome 8
class sortOutcome8GapAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome8Gap.compareTo(obj2.outcome8Gap); 
    }
}

class sortOutcome8GapDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome8Gap.compareTo(obj1.outcome8Gap); 
    }
}
// indig raw
class sortOutcome8IndigRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome8IndigRaw.compareTo(obj2.outcome8IndigRaw); 
    }
}

class sortOutcome8IndigRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome8IndigRaw.compareTo(obj1.outcome8IndigRaw); 
    }
}

// Non raw
class sortOutcome8NonRawAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome8NonRaw.compareTo(obj2.outcome8NonRaw); 
    }
}

class sortOutcome8NonRawDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome8NonRaw.compareTo(obj1.outcome8NonRaw); 
    }
}

// indig proportional
class sortOutcome8IndigProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome8IndigFrac.compareTo(obj2.outcome8IndigFrac); 
    }
}

class sortOutcome8IndigProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome8IndigFrac.compareTo(obj1.outcome8IndigFrac); 
    }
}

// non proportional
class sortOutcome8NonProportionalAscending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj1.outcome8NonFrac.compareTo(obj2.outcome8NonFrac); 
    }
}

class sortOutcome8NonProportionalDescending implements Comparator<filterOutcomes> {
    public int compare(filterOutcomes obj1,filterOutcomes obj2){
        return  obj2.outcome8NonFrac.compareTo(obj1.outcome8NonFrac); 
    }
}