package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import java.nio.file.Path;

public class PlanterPathCreator {
    private Array<PlanterPath> planterPaths;
    PlanterPathCreator(){
        this.planterPaths = new Array<PlanterPath>();
        PathTranslator.readPaths();
        PathTranslator.translatePaths();
        planterPaths = PathTranslator.getPlanterPaths();
    }

    public Array<PlanterPath> getAllPlanterPaths(){
        return planterPaths;
    }

    public void updateAllPlanterPaths(Batch spriteBatch){
        for (int i = 0; i < planterPaths.size; i++){
            planterPaths.get(i).update(spriteBatch);
        }
    }

    public boolean deleteAllPlanterPaths(){
        try{
            for (int i = 0; i < planterPaths.size; i++){
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
