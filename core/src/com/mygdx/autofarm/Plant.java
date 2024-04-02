package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Plant implements disposable{
    private int harvestingTimer, wateringTimer, feedingTimer, dyingTimer, value, pathGroupNo, creationDirection, planterId;
    private int[] position;
    private boolean grown, failed;
    private Texture sprite;
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
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlant.png"));
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

    public int getPathGroupNo(){
        return this.pathGroupNo;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getCreationDirection(){
        return this.creationDirection;
    }

    private void processTimers() {
        if (!this.failed || !this.grown){
            if (this.wateringTimer >= 1) {
                this.wateringTimer--;
            }
            if (this.feedingTimer >= 1) {
                this.feedingTimer--;
            }
            this.harvestingTimer++;
            if (this.wateringTimer < 1 || this.feedingTimer < 1) {
                this.dyingTimer--;
                if (this.dyingTimer < 1) {
                    this.failed = true;
                }
            }
            if (this.harvestingTimer > 10000) {
                this.grown = true;
            }
        }
    }

    public void update(Batch spriteBatch, BitmapFont font) {
        spriteBatch.draw(sprite, plantRect.x, plantRect.y);
        processTimers();
        if (AutoFarm.debug){
            //font.draw(spriteBatch, "R" + this.position[1] + " | C" + this.position[0], plantRect.x - 8, plantRect.y + 24);
            //font.draw(spriteBatch, String.valueOf(creationDirection), plantRect.x + 4, plantRect.y - 8);
            font.draw(spriteBatch, "WT" + String.valueOf(wateringTimer) + " | FT" + feedingTimer + " | HT" + harvestingTimer + " | DT" + dyingTimer, plantRect.x - 8, plantRect.y + 24);
        }
    }

    public void dispose(){
        sprite.dispose();
    }
}
