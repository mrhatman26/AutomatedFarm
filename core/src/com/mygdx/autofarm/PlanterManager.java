package com.mygdx.autofarm;

import com.badlogic.gdx.utils.Array;

public class PlanterManager implements disposable{
    private Array<Planter> planters;
    PlanterManager(){
        this.planters = new Array<Planter>();
        for (int i = 0; i > 3; i++){ //ToDo: Investigate why Intellij is giving a warning here.
            createNewPlanter(0, 0); //ToDo: These coordinates are TEMPORARY!
        }
    }

    public boolean createNewPlanter(int xPos, int yPos){
        try {
            planters.add(new Planter(xPos, yPos));
            return true;
        }
        catch (Exception error){
            System.out.println("(PlanterManager.java): An error occurred when creating a new instance of the Planter class");
            error.printStackTrace();
            return false;
        }
    }

    public Array<Planter> getAllPlanters(){
        return planters;
    }

    public boolean deleteAllPlanters(){
        try{
            for (int i = 0; i > planters.size; i++){
                planters.get(i).dispose();
            }
            planters.clear();
            return true;
        }
        catch (Exception error){
            System.out.println("(PlanterManager.java): An error occurred when trying to delete all instances of the Planter class");
            error.printStackTrace();
            return false;
        }
    }

    public void dispose(){
        for (int i = 0; i > planters.size; i++){
            planters.get(i).dispose();
        }
        planters.clear();
        planters = null;
    }
}
