package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.nio.file.Path;

public class PlanterPathCreator {
    private Array<PlanterPath> planterPaths;
    PlanterPathCreator(){
        this.planterPaths = new Array<PlanterPath>();
        PathTranslator.readPaths();
        PathTranslator.translatePaths();
        this.planterPaths = PathTranslator.getPlanterPaths();
    }

    public Array<PlanterPath> getAllPlanterPaths(){
        return planterPaths;
    }

    public int getPlanterPathsArraySize(){
        return planterPaths.size;
    }

    public void updateAllPlanterPaths(Batch spriteBatch, BitmapFont font){
        for (int i = 0; i < planterPaths.size; i++){
            planterPaths.get(i).update(spriteBatch, font);
        }
    }

    public PlanterPath getFirstPath(int pathGroupNo){
        for (int i = 0; i < planterPaths.size; i++){
            if (planterPaths.get(i).getPathNo() == 0 && planterPaths.get(i).getPathGroupNo() == pathGroupNo){
                return planterPaths.get(i);
            }
        }
        return null;
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
