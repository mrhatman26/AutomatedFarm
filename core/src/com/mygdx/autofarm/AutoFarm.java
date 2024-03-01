package com.mygdx.autofarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class AutoFarm extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private OrthographicCamera camera;
	private PlantHandler plantHandler;
	private PlanterManager planterManager;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.background = staticMethods.spriteTest(Gdx.files.internal("sprBackground.png"));
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.plantHandler = new PlantHandler();
		this.planterManager = new PlanterManager();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		camera.update();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
