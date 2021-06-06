package com.kompetensum.villain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsGameObject extends GameObject {

	protected float width;
	protected float height;
	protected float halfWidth;
	protected float halfHeight;
	
	protected TextureRegion texture;
	protected Body body;
	protected World world;
	
	public PhysicsGameObject() {
		
	}
	
	@Override
	public void draw(SpriteBatch batcher) {
		batcher.draw(texture,position.x - width / 2, position.y - height / 2, 
				halfWidth, halfHeight, width, height, 1f, 1f, rotation);		
	}

	public Body getBody() {
		return body;
	}
	
	public void hit(PhysicsGameObject pgoB) {

	}
	
	public void update(float delta) {
    	Vector2 pos = body.getPosition();
        position.x = pos.x * VillainGame.PIXELS_PER_METER;
        position.y = pos.y * VillainGame.PIXELS_PER_METER;
        rotation = MathUtils.radiansToDegrees * body.getAngle();
		
		
	}

}
