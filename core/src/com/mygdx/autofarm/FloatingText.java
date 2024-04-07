package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class FloatingText {
    private Rectangle textPos;
    private String textValue;
    private float alpha;
    private int r, g, b;
    FloatingText(String textValue, int xPos, int yPos, int r, int g, int b){
        this.textPos = new Rectangle();
        this.textPos.x = xPos;
        this.textPos.y = yPos;
        this.textValue = textValue;
        this.alpha = (float)1.0;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getAlpha(){
        return this.alpha;
    }

    public void update(SpriteBatch spriteBatch, BitmapFont font){
        font.setColor(r, g, b, alpha);
        font.draw(spriteBatch, textValue, textPos.x, textPos.y);
        alpha = alpha - (float)0.005;
        textPos.y++;
        font.setColor(255, 255, 255, 1);
    }
}
