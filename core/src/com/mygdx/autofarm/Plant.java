package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Plant implements disposable{
    private int harvestingTimer, wateringTimer, feedingTimer, dyingTimer;
    private boolean grown, failed;
    private Texture sprite;
    public Plant(int harvestingTimer, int wateringTimer, int feedingTimer, int dyingTimer){
        this.harvestingTimer = harvestingTimer;
        this.wateringTimer = wateringTimer;
        this.feedingTimer = feedingTimer;
        this.dyingTimer = dyingTimer;
        this.grown = false;
        this.failed = false;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlant.png")); //Todo: Replace badlogic.jpg with the background.
    }

    public void dispose(){
        sprite.dispose();
    }
}
