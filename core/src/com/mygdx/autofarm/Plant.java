package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Plant implements disposable{
    private int harvestingTimer, wateringTimer, feedingTimer, dyingTimer, value, pathGroupNo;
    private int[] position;
    private boolean grown, failed;
    private Texture sprite;
    private Rectangle plantRect;
    public Plant(int harvestingTimer, int wateringTimer, int feedingTimer, int dyingTimer, int value, int pathGroupNo, int x, int y, int[] position){
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
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlant.png"));
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

    public void update(Batch spriteBatch){
        spriteBatch.draw(sprite, plantRect.x, plantRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
