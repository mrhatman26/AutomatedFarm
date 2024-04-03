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
	private BitmapFont font;
	private static int money;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.background = staticMethods.spriteTest(Gdx.files.internal("sprBackground.png"));
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.planterPathCreator = new PlanterPathCreator();
		this.planterManager = new PlanterManager(planterPathCreator);
		this.plantHandler = new PlantHandler();
		money = 1000;
		font = new BitmapFont();
	}

	public static void increaseMoney(int addition){
		money = money + addition;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		planterPathCreator.updateAllPlanterPaths(batch, font);
		plantHandler.updateAllPlants(batch, font);
		planterManager.updateAllPlanters(batch, planterPathCreator, font, plantHandler);
		font.draw(batch, "Money: " + String.valueOf(money), 40, 550);
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
