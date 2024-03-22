package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class PlanterManager implements disposable{
    private Array<Planter> planters;
    PlanterManager(PlanterPathCreator planterPathCreator){
        this.planters = new Array<Planter>();
        int pathGroupNo = 0;
        for (int i = 0; i < planterPathCreator.getPlanterPathsArraySize(); i++){
            PlanterPath tempPath = planterPathCreator.getFirstPath(i);
            if (tempPath != null) {
                createNewPlanter(tempPath.getX(true) - 16, tempPath.getY(true) - 16, tempPath.getCPos(), tempPath.getRPos()); //ToDo: These coordinates are TEMPORARY!
                pathGroupNo++;
            }
        }
    }

    public boolean createNewPlanter(int xPos, int yPos, int cPos, int rPos){
        try {
            planters.add(new Planter(xPos, yPos, cPos, rPos));
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
            for (int i = 0; i < planters.size; i++){
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

    public void updateAllPlanters(Batch spriteBatch){
        for (int i = 0; i < planters.size; i++){
            planters.get(i).update(spriteBatch);
        }
    }

    public void dispose(){
        for (int i = 0; i < planters.size; i++){
            planters.get(i).dispose();
        }
        planters.clear();
        planters = null;
    }
}
