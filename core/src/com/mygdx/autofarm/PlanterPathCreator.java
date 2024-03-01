package com.mygdx.autofarm;

import com.badlogic.gdx.utils.Array;

public class PlanterPathCreator {
    private Array<PlanterPath> planterPaths;
    PlanterPathCreator(){
        this.planterPaths = new Array<PlanterPath>();
        //Todo: Create all paths here. Idea: Have the paths be described in a text file or something like that?
    }

    public boolean createNewPath(int xPos, int yPos){
        try{
            planterPaths.add(new PlanterPath(xPos, yPos));
            return true;
        }
        catch (Exception error){
            System.out.println("(PlanterPathCreator.java): An error occurred when trying to create a new instance of the PlanterPath class");
            error.printStackTrace();
            return false;
        }
    }

    public Array<PlanterPath> getAllPlanterPaths(){
        return planterPaths;
    }

    public boolean deleteAllPlanterPaths(){
        try{
            for (int i = 0; i > planterPaths.size; i++){
                planterPaths.get(i).dispose();
            }
            planterPaths.clear();
            return true;
        }
        catch (Exception error){
            System.out.println("(PlanterPathCreator.java): An error occurred when trying to delete all instances of the PlanterPath class");
            error.printStackTrace();
            return false;
        }
    }
}
