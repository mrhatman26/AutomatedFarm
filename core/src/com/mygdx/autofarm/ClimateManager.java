package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ClimateManager {
    private final String[] SEASONS = new String[]{"Spring", "Summer", "Autumn", "Winter"};
    private final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String textToDraw;
    private int month, monthTimer, externalTemperatureCurrent, externalTemperatureTarget, internalTemperatureCurrent, internalTemperatureTarget, temperatureChangeTimer, seasonNo;
    private final int MONTH_TIMER_MAX = 1000;
    private final int TEMPERATURE_TIMER_MAX = 50;
    private final int[] EXTERNAL_TEMPERATURE_TARGETS = new int[]{15, 21, 18, 0}; //First number is spring, second is summer, third is autumn, fourth is winter;
    private final int[] INTERNAL_TEMPERATURE_TARGETS = new int[]{18, 19, 18, 17}; //Ditto to the above comment.
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
        externalTemperatureTarget = staticMethods.getRandom(EXTERNAL_TEMPERATURE_TARGETS[seasonNo] + 3, EXTERNAL_TEMPERATURE_TARGETS[seasonNo] - 3);
        internalTemperatureTarget = staticMethods.getRandom(INTERNAL_TEMPERATURE_TARGETS[seasonNo] + 1, INTERNAL_TEMPERATURE_TARGETS[seasonNo] - 1);
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

    public void update(SpriteBatch spriteBatch, BitmapFont font){
        monthTimer--;
        if (monthTimer < 1){
            monthTimer = MONTH_TIMER_MAX;
            month++;
            if (month > 12){
                month = 1;
            }
            seasonHandling();
        }
        temperatureChange();
        //Drawing
        textToDraw = "Month: " + MONTHS[month - 1];
        textToDraw = textToDraw + "\nSeason: " + SEASONS[seasonNo];
        textToDraw = textToDraw + "\nExternal Temperature: \u2103" + externalTemperatureCurrent;
        textToDraw = textToDraw + "\nInternal Temperature: \u2103" + internalTemperatureCurrent;
        if (AutoFarm.debug){
            textToDraw = textToDraw + "\nExternal Temperature (TARGET): \u2103" + externalTemperatureTarget;
            textToDraw = textToDraw + "\nInternal Temperature (TARGET): \u2103" + internalTemperatureTarget;
            textToDraw = textToDraw + "\nmonthTimer: " + monthTimer;
            textToDraw = textToDraw + "\ntemperatureChangeTimer: " + temperatureChangeTimer;
        }
        font.draw(spriteBatch, textToDraw, 200, 630);
        //40, 550
    }
}
