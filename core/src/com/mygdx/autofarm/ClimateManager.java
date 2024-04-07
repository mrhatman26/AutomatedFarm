package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class ClimateManager {
    private static Vector3 mousePos = new Vector3();
    private final String[] SEASONS = new String[]{"Spring", "Summer", "Autumn", "Winter"};
    private final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final String degreesSymbol = "\u00B0" + "C";
    private String textToDraw;
    private int month, monthTimer, externalTemperatureCurrent, externalTemperatureTarget, internalTemperatureCurrent, internalTemperatureTarget, temperatureChangeTimer, seasonNo;
    private final int MONTH_TIMER_MAX = 1000;
    private final int TEMPERATURE_TIMER_MAX = 50;
    private final int MAXIMUM_UNCOOLED_TEMPERATURE = 20;
    private final int MINIMUM_UNHEATED_TEMPERATURE = 10;
    private final int TEMPERATURE_COST = 100;
    private final int[] EXTERNAL_TEMPERATURE_TARGETS = new int[]{15, 21, 18, 0}; //First number is spring, second is summer, third is autumn, fourth is winter;
    private final int[] INTERNAL_TEMPERATURE_TARGETS = new int[]{18, 19, 18, 17}; //Ditto to the above comment.
    private final int[] EXTERNAL_TEMPERATURE_TARGETS_EXTREME = new int[]{25, 37, 32, -10};
    private final int[] INTERNAL_TEMPERATURE_TARGETS_EXTREME = new int[]{21, 24, 22, 17};
    private boolean extremeClimate;
    ClimateManager(){
        this.month = 1;
        this.monthTimer = MONTH_TIMER_MAX;
        this.seasonNo = 3;
        this.externalTemperatureTarget = staticMethods.getRandom(EXTERNAL_TEMPERATURE_TARGETS[seasonNo] + 3, EXTERNAL_TEMPERATURE_TARGETS[seasonNo] - 3);
        this.internalTemperatureTarget = staticMethods.getRandom(INTERNAL_TEMPERATURE_TARGETS[seasonNo] + 1, INTERNAL_TEMPERATURE_TARGETS[seasonNo] - 1);
        this.externalTemperatureCurrent = externalTemperatureTarget;
        this.internalTemperatureCurrent = internalTemperatureTarget;
        this.temperatureChangeTimer = TEMPERATURE_TIMER_MAX;
        this.textToDraw = "";
        this.extremeClimate = false;
    }

    public int getSeasonNo(){
        return this.seasonNo;
    }

    public void seasonHandling(){
        //Seasons
        //https://www.timeanddate.com/calendar/aboutseasons.html
        switch (month){
            case 3: //Spring starts in March.
                seasonNo = 0;
                break;
            case 5:
                seasonNo = 1;
                break;
            case 9:
                seasonNo = 2;
                break;
            case 11:
                seasonNo = 3;
                break;
        }
        if (!extremeClimate) {
            externalTemperatureTarget = staticMethods.getRandom(EXTERNAL_TEMPERATURE_TARGETS[seasonNo] + 3, EXTERNAL_TEMPERATURE_TARGETS[seasonNo] - 3);
            internalTemperatureTarget = staticMethods.getRandom(INTERNAL_TEMPERATURE_TARGETS[seasonNo] + 1, INTERNAL_TEMPERATURE_TARGETS[seasonNo] - 1);
        }
        else{
            externalTemperatureTarget = staticMethods.getRandom(EXTERNAL_TEMPERATURE_TARGETS_EXTREME[seasonNo] + staticMethods.getRandom(20, 0), EXTERNAL_TEMPERATURE_TARGETS_EXTREME[seasonNo] - staticMethods.getRandom(20, 0));
            internalTemperatureTarget = staticMethods.getRandom(INTERNAL_TEMPERATURE_TARGETS_EXTREME[seasonNo] + staticMethods.getRandom(20, 0), INTERNAL_TEMPERATURE_TARGETS_EXTREME[seasonNo] - staticMethods.getRandom(20, 0));
        }
    }

    public void temperatureChange(){
        temperatureChangeTimer--;
        if (temperatureChangeTimer < 1){
            temperatureChangeTimer = TEMPERATURE_TIMER_MAX;
            if (internalTemperatureTarget > internalTemperatureCurrent){
                internalTemperatureCurrent++;
            }
            if (internalTemperatureCurrent > internalTemperatureTarget){
                internalTemperatureCurrent--;
            }
            if (externalTemperatureTarget > externalTemperatureCurrent){
                externalTemperatureCurrent++;
            }
            if (externalTemperatureCurrent > externalTemperatureTarget){
                externalTemperatureCurrent--;
            }
        }
    }

    public void costCalculation(OrthographicCamera camera){
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        int monthCost = TEMPERATURE_COST;
        if (externalTemperatureCurrent > MAXIMUM_UNCOOLED_TEMPERATURE || externalTemperatureCurrent < MINIMUM_UNHEATED_TEMPERATURE){
            if (externalTemperatureCurrent > MAXIMUM_UNCOOLED_TEMPERATURE) {
                monthCost = monthCost * Math.round((float)externalTemperatureCurrent / (float)MAXIMUM_UNCOOLED_TEMPERATURE);
                monthCost = monthCost * 4; //Four weeks I guess
                FloatingTextHandler.createNewFloatingText("High Temperature Cost £" + String.valueOf(monthCost), Math.round(mousePos.x), Math.round(mousePos.y), 255, 0, 0);
                AutoFarm.increaseMoneyOut(monthCost);
            }
            else{
                monthCost = monthCost * Math.round((float)MINIMUM_UNHEATED_TEMPERATURE / (float)externalTemperatureCurrent);
                monthCost = monthCost * 4;
                FloatingTextHandler.createNewFloatingText("Low Temperature Cost £" + String.valueOf(monthCost), Math.round(mousePos.x), Math.round(mousePos.y), 255, 0, 0);
                AutoFarm.increaseMoneyOut(monthCost);
            }
        }
        else{
            monthCost = monthCost * 4;
            AutoFarm.increaseMoneyOut(monthCost);
            FloatingTextHandler.createNewFloatingText("Normal Temperature Cost £" + String.valueOf(monthCost), Math.round(mousePos.x), Math.round(mousePos.y), 255, 0, 0);
        }
    }

    public void update(SpriteBatch spriteBatch, BitmapFont font, OrthographicCamera camera){
        monthTimer--;
        if (monthTimer < 1){
            monthTimer = MONTH_TIMER_MAX;
            month++;
            if (month > 12){
                month = 1;
            }
            seasonHandling();
            costCalculation(camera);
        }
        temperatureChange();
        //Drawing
        textToDraw = "Month: " + MONTHS[month - 1];
        textToDraw = textToDraw + "\nSeason: " + SEASONS[seasonNo];
        textToDraw = textToDraw + "\nExternal Temperature: " + externalTemperatureCurrent + degreesSymbol;
        textToDraw = textToDraw + "\nInternal Temperature: " + internalTemperatureCurrent + degreesSymbol;
        if (AutoFarm.debug){
            textToDraw = textToDraw + "\nExternal Temperature (TARGET): " + externalTemperatureTarget + degreesSymbol;
            textToDraw = textToDraw + "\nInternal Temperature (TARGET): " + internalTemperatureTarget  + degreesSymbol;
            textToDraw = textToDraw + "\nmonthTimer: " + monthTimer;
            textToDraw = textToDraw + "\ntemperatureChangeTimer: " + temperatureChangeTimer;
            textToDraw = textToDraw + "\nextremeClimate: " + String.valueOf(extremeClimate);
        }
        font.draw(spriteBatch, textToDraw, 200, 630);
        //40, 550
    }
}
