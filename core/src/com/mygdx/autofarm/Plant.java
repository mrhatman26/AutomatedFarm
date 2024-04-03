package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Plant implements disposable{
    private int harvestingTimer, wateringTimer, feedingTimer, dyingTimer, value, pathGroupNo, creationDirection, planterId, id;
    private int[] position;
    private boolean grown, failed;
    private Texture sprite, spriteThirsty, spriteHungry, spriteThirstyAndHugnry, spriteDead, spriteFinished;
    private Rectangle plantRect;
    private static int accessCount = 0;
    Plant(int pathGroupNo, int x, int y, int[] position, int creationDirection, int planterId){
        this.harvestingTimer = staticMethods.getRandom(1000, 500);
        this.wateringTimer = staticMethods.getRandom(500, 200);
        this.feedingTimer = staticMethods.getRandom(1500, 600);
        this.dyingTimer = staticMethods.getRandom(2000, 1500);
        this.grown = false;
        this.failed = false;
        this.value = staticMethods.getRandom(1000, 50);
        this.pathGroupNo = pathGroupNo;
        this.plantRect = new Rectangle();
        this.plantRect.width = 16;
        this.plantRect.height = 16;
        this.plantRect.x = x;
        this.plantRect.y = y;
        this.position = position;
        this.creationDirection = creationDirection;
        this.planterId = planterId;
        this.id = -1;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlant.png"));
        this.spriteThirsty = staticMethods.spriteTest(Gdx.files.internal("sprPlantThirsty.png"));
        this.spriteHungry = staticMethods.spriteTest(Gdx.files.internal("sprPlantHungry.png"));
        this.spriteThirstyAndHugnry = staticMethods.spriteTest(Gdx.files.internal("sprPlantThirstyAndHungry.png"));
        this.spriteDead = staticMethods.spriteTest(Gdx.files.internal("sprPlantDead.png"));
        this.spriteFinished = staticMethods.spriteTest(Gdx.files.internal("sprPlantFinished.png"));
        accessCount++;
        staticMethods.systemMessage("Plant", null, "Plant initiliser has been accessed " + accessCount + " time(s).", true);
    }

    public int getPlanterId(){
        return this.planterId;
    }

    public int getWateringTimer(){
        return this.wateringTimer;
    }

    public int getFeedingTimer(){
        return this.feedingTimer;
    }

    public int getHarvestingTimer(){
        return this.harvestingTimer;
    }

    public int getDyingTimer(){
        return this.dyingTimer;
    }

    public boolean getGrown(){
        return this.grown;
    }

    public boolean getFailed(){
        return this.failed;
    }

    public int getPathGroupNo(){
        return this.pathGroupNo;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getCreationDirection(){
        return this.creationDirection;
    }

    public int getId(){
        return this.id;
    }

    public int getValue(){
        return this.value;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void resetWateringTimer(){
        this.wateringTimer = staticMethods.getRandom(500, 200);
    }

    public void resetFeedingTimer(){
        this.feedingTimer = staticMethods.getRandom(1500, 600);
    }

    private void processTimers() {
        if (!this.failed || !this.grown){
            if (this.wateringTimer >= 1) {
                this.wateringTimer--;
            }
            if (this.feedingTimer >= 1) {
                this.feedingTimer--;
            }
            this.harvestingTimer--;
            if (this.wateringTimer < 1 || this.feedingTimer < 1) {
                this.dyingTimer--;
                if (this.dyingTimer < 1) {
                    this.failed = true;
                }
            }
            if (this.harvestingTimer < 1) {
                this.grown = true;
            }
        }
    }

    public void update(Batch spriteBatch, BitmapFont font) {
        if (this.grown){
            spriteBatch.draw(spriteFinished, plantRect.x, plantRect.y);
        }
        else if (wateringTimer < 1 && feedingTimer < 1){
            spriteBatch.draw(spriteThirstyAndHugnry, plantRect.x, plantRect.y);
        }
        else if (wateringTimer < 1){
            spriteBatch.draw(spriteThirsty, plantRect.x, plantRect.y);
        }
        else if (feedingTimer < 1){
            spriteBatch.draw(spriteHungry, plantRect.x, plantRect.y);
        }
        else {
            spriteBatch.draw(sprite, plantRect.x, plantRect.y);
        }
        processTimers();
        if (AutoFarm.debug){
            //font.draw(spriteBatch, "R" + this.position[1] + " | C" + this.position[0], plantRect.x - 8, plantRect.y + 24);
            //font.draw(spriteBatch, String.valueOf(creationDirection), plantRect.x + 4, plantRect.y - 8);
            //font.draw(spriteBatch, "WT" + String.valueOf(wateringTimer) + " | FT" + feedingTimer + " | HT" + harvestingTimer + " | DT" + dyingTimer, plantRect.x - 8, plantRect.y + 24);
            font.draw(spriteBatch, String.valueOf(harvestingTimer), plantRect.x, plantRect.y + 24);
        }
    }

    public void dispose(){
        sprite.dispose();
        spriteThirsty.dispose();
        spriteHungry.dispose();
        spriteThirstyAndHugnry.dispose();
        spriteDead.dispose();
        spriteFinished.dispose();
    }
}
