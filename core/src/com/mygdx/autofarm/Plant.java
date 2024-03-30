package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Plant implements disposable{
    private int harvestingTimer, wateringTimer, feedingTimer, dyingTimer, value, pathGroupNo;
    private boolean grown, failed;
    private Texture sprite;
    public Plant(int harvestingTimer, int wateringTimer, int feedingTimer, int dyingTimer, int value, int pathGroupNo){
        this.harvestingTimer = harvestingTimer;
        this.wateringTimer = wateringTimer;
        this.feedingTimer = feedingTimer;
        this.dyingTimer = dyingTimer;
        this.grown = false;
        this.failed = false;
        this.value = value;
        this.pathGroupNo = pathGroupNo;
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

    public void dispose(){
        sprite.dispose();
    }
}
