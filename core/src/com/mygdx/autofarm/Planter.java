package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Planter implements disposable {
    private int[] position, targetPosition;
    private boolean override;
    private Texture sprite;
    public Planter(int[] position){
        this.position = position;
        this.targetPosition = new int[2];
        this.sprite = new Texture(Gdx.files.internal("badlogic.jpg")); //Todo: Replace badlogic.jpg with the background.

    }

    public void dispose(){
        sprite.dispose();
    }
}
