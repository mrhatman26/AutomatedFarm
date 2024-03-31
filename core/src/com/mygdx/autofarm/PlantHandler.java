package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class PlantHandler implements disposable{
    private static Array<Plant> plants;
    PlantHandler(){
        this.plants = new Array<Plant>();
    }

    public static Plant createNewPlant(int pathGroupNo, int x, int y, int[] position){
        //Todo: Have the new plant's position be set in the creator. This doesn't say where the plant should be!
        try {
            Plant tempPlant = new Plant(4000, 2000, 3000, 10000, 100, pathGroupNo, x, y, position);
            plants.add(tempPlant);
            return tempPlant;
        }
        catch (Exception error){
            System.out.println("(PlantHandler.java): An error occurred when trying to create a new instance of the Plant class.");
            error.printStackTrace();
            return null;
        }
    }

    public Array<Plant> getAllPathPlants(int pathGroupNo){
        Array<Plant> tempPlantList = null;
        for (int i = 0; i < plants.size; i++){
            Plant tempPlant = plants.get(i);
            if (tempPlant.getPathGroupNo() == pathGroupNo){
                tempPlantList.add(tempPlant);
            }
        }
        return tempPlantList;
    }

    public boolean deleteAllPlants(){
        try{
            for (int i = 0; i < plants.size; i++){
                plants.get(i).dispose();
            }
            plants.clear();
            return true;
        }
        catch (Exception error){
            System.out.println("(PlantHandler.java): An error occurred when trying to delete all instaces of the Plant class");
            error.printStackTrace();
            return false;
        }
    }

    public void updateAllPlants(Batch spriteBatch){
        if (plants != null){
            System.out.println("(PlantHandler:updateAllPlants): plants.size = " + plants.size);
            for (int i = 0; i < plants.size; i++){
                plants.get(i).update(spriteBatch);
            }
        }
    }

    public void dispose(){
        for (int i = 0; i < plants.size; i++){
            plants.get(i).dispose();
        }
        plants.clear();
        plants = null;
    }
}
