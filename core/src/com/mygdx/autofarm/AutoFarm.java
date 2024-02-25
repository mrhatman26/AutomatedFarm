package com.mygdx.autofarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class AutoFarm extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("badlogic.jpg"); //Todo: Replace badlogic.jpg with the background.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
