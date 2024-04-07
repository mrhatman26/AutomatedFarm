package com.mygdx.autofarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class AutoFarm extends ApplicationAdapter {
	public static boolean debug = false;
	private SpriteBatch batch;
	private Texture background, overlaySpring, overlaySummer, overLayAutumn, overlayWinter;
	private OrthographicCamera camera;
	private PlantHandler plantHandler;
	private PlanterManager planterManager;
	private PlanterPathCreator planterPathCreator;
	private FloatingTextHandler floatingTextHandler;
	private ClimateManager climateManager;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private static int money, moneyIn, moneyOut, moneyTimer;
	private static final int MONEY_TIMER_MAX = 1000;
	private static final int FONT_SIZE = 16;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.background = staticMethods.spriteTest(Gdx.files.internal("sprBackground.png"));
		this.overlaySpring = staticMethods.spriteTest(Gdx.files.internal("sprOverlaySpring.png"));
		this.overlaySummer = staticMethods.spriteTest(Gdx.files.internal("sprOverlaySummer.png"));
		this.overLayAutumn = staticMethods.spriteTest(Gdx.files.internal("sprOverlayAutumn.png"));
		this.overlayWinter = staticMethods.spriteTest(Gdx.files.internal("sprOverlayWinter.png"));
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.planterPathCreator = new PlanterPathCreator();
		this.planterManager = new PlanterManager(planterPathCreator);
		this.plantHandler = new PlantHandler();
		this.floatingTextHandler = new FloatingTextHandler();
		this.climateManager = new ClimateManager();
		this.shapeRenderer = new ShapeRenderer();
		money = 1000;
		moneyIn = 0;
		moneyOut = 0;
		moneyTimer = MONEY_TIMER_MAX;
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Helvetica.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = FONT_SIZE;
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}

	public static void increaseMoneyIn(int addition){
		moneyIn = moneyIn + addition;
	}

	public static void increaseMoneyOut(int subtraction){
		moneyOut = moneyOut + subtraction;
	}

	private void drawSeasonOverlay(){
		int seasonNo = climateManager.getSeasonNo();
		switch (seasonNo){
			case 0: //Spring
				batch.draw(overlaySpring, 0, 0);
				break;
			case 1: //Summer
				batch.draw(overlaySummer, 0, 0);
				break;
			case 2: // Autumn
				batch.draw(overLayAutumn, 0, 0);
				break;
			case 3:
				batch.draw(overlayWinter, 0, 0);
				break;
		}
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
		climateManager.update(batch, font, camera);
		if (debug) {
			font.draw(batch, "Money: £" + String.valueOf(money) + "\nMoney In: +£" + String.valueOf(moneyIn) + "\nMoney Out: -£" + String.valueOf(moneyOut) + "\nMoney Timer: " + String.valueOf(moneyTimer), 40, 550);
		}
		else{
			font.draw(batch, "Money: £" + String.valueOf(money) + "\nMoney In: +£" + String.valueOf(moneyIn) + "\nMoney Out: -£" + String.valueOf(moneyOut), 200, 550);
		}
		batch.end();
		batch.begin();
		drawSeasonOverlay();
		batch.end();
		camera.update();
		staticMethods.miscControls();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		overlaySpring.dispose();
		overlaySummer.dispose();
		overLayAutumn.dispose();
		overlayWinter.dispose();
		font.dispose();
	}
}
