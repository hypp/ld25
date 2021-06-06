package com.kompetensum.villain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Art {
	
	static public Texture platform;
	public static Texture player;
	public static Texture cat;
	public static Pixmap blood;
	public static Texture intro;
	public static Texture gameover;

	public static void load()
	{
//		Texture.setEnforcePotImages(false);

		intro = new Texture(Gdx.files.internal("data/intro.png"));
		gameover = new Texture(Gdx.files.internal("data/gameover.png"));
		platform = new Texture(Gdx.files.internal("data/platform.png"));
		player = new Texture(Gdx.files.internal("data/player.png"));
		cat = new Texture(Gdx.files.internal("data/cat.png"));
		blood = new Pixmap(Gdx.files.internal("data/blood.png"));
//		raindrop = new Texture(Gdx.files.internal("data/raindrop.png"));
	}
	
}
