package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class PlantHandler implements disposable{
    private Array<Plant> plants;
    PlantHandler(){
        this.plants = new Array<Plant>();
    }

    public Plant createNewPlant(int pathGroupNo, int x, int y, int[] position, int creationDirection, int planterId){
        try {
            staticMethods.systemMessage("PlantHandler", null, "Attempting to create a new plant...", true);
            Plant tempPlant = new Plant(pathGroupNo, x, y, position, creationDirection, planterId);
            plants.add(tempPlant);
            staticMethods.systemMessage("PlantHandler", null, "Plant created successfully", true);
            return tempPlant;
        }
        catch (Exception error){
            staticMethods.systemMessage("PlantHandler", null, "An error occurred when trying to create a new instance of the Plant class.", true);
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

    public Array<Plant> getPlanterPlants(int planterId){
        Array<Plant> planterPlants = new Array<>();
        for (Plant plant: plants){
            if (plant.getPlanterId() == planterId){
                planterPlants.add(plant);
            }
        }
        return planterPlants;
    }

    public Plant plantNeedsMaintaining(int planterId){
        for (Plant plant: plants){
            if (plant.getPlanterId() == planterId){
                if (plant.getWateringTimer() < 1){
                    return plant;
                }
                if (plant.getFeedingTimer() < 1){
                    return plant;
                }
                if (plant.getHarvestingTimer() < 1){
                    return plant;
                }
            }
        }
        return null;
    }

    public void updateAllPlants(Batch spriteBatch, BitmapFont font) {
        if (plants != null){
            for (int i = 0; i < plants.size; i++){
                plants.get(i).update(spriteBatch, font);
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
