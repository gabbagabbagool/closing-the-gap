package app;

import java.util.Comparator;

public class thymeleafOutcomes {
    public int areaCode;
    public String areaName;
    public Double outcome1Raw;
    public Double outcome5Raw;
    public Double outcome6Raw;
    public Double outcome8Raw;
    public Double outcome1Frac;
    public Double outcome5Frac;
    public Double outcome6Frac; 
    public Double outcome8Frac;

    public void setOutcomes(String outcome, Double value){
        if(value == null){
            value = -42069.0;
        }
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

class sortOutcome1RawAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome1Raw.compareTo(obj2.outcome1Raw); 
    }
}

class sortOutcome5RawAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome5Raw.compareTo(obj2.outcome5Raw); 
    }
}

class sortOutcome6RawAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome6Raw.compareTo(obj2.outcome6Raw); 
    }
}

class sortOutcome8RawAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome8Raw.compareTo(obj2.outcome8Raw); 
    }
}

class sortOutcome1RawDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome1Raw.compareTo(obj1.outcome1Raw); 
    }
}

class sortOutcome5RawDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome5Raw.compareTo(obj1.outcome5Raw); 
    }
}

class sortOutcome6RawDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome6Raw.compareTo(obj1.outcome6Raw); 
    }
}

class sortOutcome8RawDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome8Raw.compareTo(obj1.outcome8Raw); 
    }
}

class sortOutcome1ProportionalAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome1Frac.compareTo(obj2.outcome1Frac); 
    }
}

class sortOutcome5ProportionalAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome5Frac.compareTo(obj2.outcome5Frac); 
    }
}

class sortOutcome6ProportionalAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome6Frac.compareTo(obj2.outcome6Frac); 
    }
}

class sortOutcome8ProportionalAscending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj1.outcome8Frac.compareTo(obj2.outcome8Frac); 
    }
}

class sortOutcome1ProportionalDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome1Frac.compareTo(obj1.outcome1Frac); 
    }
}

class sortOutcome5ProportionalDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome5Frac.compareTo(obj1.outcome5Frac); 
    }
}

class sortOutcome6ProportionalDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome6Frac.compareTo(obj1.outcome6Frac); 
    }
}

class sortOutcome8ProportionalDescending implements Comparator<thymeleafOutcomes> {
    public int compare(thymeleafOutcomes obj1,thymeleafOutcomes obj2){
        return  obj2.outcome8Frac.compareTo(obj1.outcome8Frac); 
    }
}