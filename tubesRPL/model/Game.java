package model;

import java.util.ArrayList;

public class Game extends Item{
    private ArrayList<DLC> DLC;

    public Game(){
        
    }

    

    public ArrayList<model.DLC> getDLC() {
        return DLC;
    }

    public void setDLC(ArrayList<model.DLC> DLC) {
        this.DLC = DLC;
    }
}
