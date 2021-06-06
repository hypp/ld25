package com.kompetensum.villain;

import com.badlogic.gdx.Game;

public class VillainGame extends Game {
	
	public static final float PIXELS_PER_METER = 60f;

	@Override
	public void create() {
		// Settings.load
		Art.load();
		Sounds.load();
		setScreen(new IntroScreen(this));
		
		Sounds.music.loop(0.6f);
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
		
		Sounds.music.stop();
	}

	@Override
	public void render() {
		super.render();
	}

}
