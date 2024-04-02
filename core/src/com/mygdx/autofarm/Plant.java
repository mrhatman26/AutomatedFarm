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
    Plant(int harvestingTimer, int wateringTimer, int feedingTimer, int dyingTimer, int value, int pathGroupNo, int x, int y, int[] position, int creationDirection, int planterId){
        this.harvestingTimer = harvestingTimer;
        this.wateringTimer = wateringTimer;
        this.feedingTimer = feedingTimer;
        this.dyingTimer = dyingTimer;
        this.grown = false;
        this.failed = false;
        this.value = value;
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

    public void update(Batch spriteBatch, BitmapFont font) {
        spriteBatch.draw(sprite, plantRect.x, plantRect.y);
        if (AutoFarm.debug){
            font.draw(spriteBatch, "R" + this.position[1] + " | C" + this.position[0], plantRect.x - 8, plantRect.y + 24);
            font.draw(spriteBatch, String.valueOf(creationDirection), plantRect.x + 4, plantRect.y - 8);
        }
    }

    public void dispose(){
        sprite.dispose();
    }
}
