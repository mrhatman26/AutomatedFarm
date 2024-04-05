package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class FloatingTextHandler {
    private static Array<FloatingText> floatingTextArray = new Array<FloatingText>();

    public static void createNewFloatingText(String textValue, int xPos, int yPos, int r, int g, int b){
        floatingTextArray.add(new FloatingText(textValue, xPos, yPos, r, g, b));
    }

    public static void updateAllText(SpriteBatch spriteBatch, BitmapFont font){
        FloatingText tempText;
        for (int i = 0; i < floatingTextArray.size; i++){
            tempText = floatingTextArray.get(i);
            tempText.update(spriteBatch, font);
            if (tempText.getAlpha() < 0.01){
                floatingTextArray.removeIndex(i);
            }
        }
    }
}
