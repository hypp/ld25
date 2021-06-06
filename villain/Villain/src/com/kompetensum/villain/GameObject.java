package com.kompetensum.villain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	protected Vector2 position = new Vector2();
	protected float rotation = 0;

	// TODO Override in subclass!
	public void draw(SpriteBatch batcher) {
	}

	// TODO Override in subclass!
	public void update(float delta) {
	}

	public Vector2 getPosition() {
		return position;
	}
}
