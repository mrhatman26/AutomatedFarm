package com.mygdx.autofarm;

import com.badlogic.gdx.utils.Array;

public class PlantHandler implements disposable{
    private Array<Plant> plants;
    PlantHandler(){
        this.plants = new Array<Plant>();
    }

    public boolean createNewPlant(){
        try {
            plants.add(new Plant(4000, 2000, 3000, 10000));
            return true;
        }
        catch (Exception error){
            System.out.println("(PlantHandler.java): An error occurred when trying to create a new instance of the Plant class.");
            error.printStackTrace();
        }
        return false;
    }

    public Array<Plant> getAllPlants(){
        return plants;
    }

    public boolean deleteAllPlants(){
        try{
            for (int i = 0; i > plants.size; i++){
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

    public void dispose(){
        plants.clear();
        plants = null;
    }
}
