package com.mygdx.autofarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class AutoFarm extends ApplicationAdapter {
	public static boolean debug = true;
	private SpriteBatch batch;
	private Texture background;
	private OrthographicCamera camera;
	private PlantHandler plantHandler;
	private PlanterManager planterManager;
	private PlanterPathCreator planterPathCreator;
	private FloatingTextHandler floatingTextHandler;
	private BitmapFont font;
	private static int money, moneyIn, moneyOut, moneyTimer;
	private static final int MONEY_TIMER_MAX = 1000;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.background = staticMethods.spriteTest(Gdx.files.internal("sprBackground.png"));
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.planterPathCreator = new PlanterPathCreator();
		this.planterManager = new PlanterManager(planterPathCreator);
		this.plantHandler = new PlantHandler();
		this.floatingTextHandler = new FloatingTextHandler();
		money = 1000;
		moneyIn = 0;
		moneyOut = 0;
		moneyTimer = MONEY_TIMER_MAX;
		font = new BitmapFont();
	}

	public static void increaseMoneyIn(int addition){
		moneyIn = moneyIn + addition;
	}

	public static void increaseMoneyOut(int subtraction){
		moneyOut = moneyOut + subtraction;
	}

	@Override
	public void render () {
		moneyTimer--;
		if (moneyTimer < 1){
			moneyTimer = MONEY_TIMER_MAX;
			money = money + moneyIn;
			money = money - moneyOut;
			moneyIn = 0;
			moneyOut = 0;
		}
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		floatingTextHandler.updateAllText(batch, font);
		planterPathCreator.updateAllPlanterPaths(batch, font);
		plantHandler.updateAllPlants(batch, font);
		planterManager.updateAllPlanters(batch, planterPathCreator, font, plantHandler);
		if (debug) {
			font.draw(batch, "Money: £" + String.valueOf(money) + "\nMoney In: +£" + String.valueOf(moneyIn) + "\nMoney Out: -£" + String.valueOf(moneyOut) + "\nMoney Timer: " + String.valueOf(moneyTimer), 40, 550);
		}
		else{
			font.draw(batch, "Money: £" + String.valueOf(money) + "\nMoney In: +£" + String.valueOf(moneyIn) + "\nMoney Out: -£" + String.valueOf(moneyOut), 40, 550);
		}
		batch.end();
		camera.update();
		staticMethods.miscControls();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		font.dispose();
	}
}
