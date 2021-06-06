package com.kompetensum.villain;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class IntroScreen implements Screen {

	private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batcher;

	public IntroScreen(Game game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		batcher = new SpriteBatch();
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batcher.setProjectionMatrix(camera.combined);
		batcher.begin();
		batcher.disableBlending();
		batcher.draw(Art.intro, 400 - Art.intro.getWidth() / 2, 300 - Art.intro.getHeight() / 2, 
				Art.intro.getWidth(), Art.intro.getHeight());
		batcher.end();
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			game.setScreen(new MainScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
