package com.kompetensum.villain;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Cat extends PhysicsGameObject {

	private float life;
	private Vector2 leftSpeed;
	private Vector2 rightSpeed;
	private Vector2 upSpeed;
	private Vector2 center;
	private float rollingVelocity;
	
	public Cat(World world, float xPixels, float yPixels) {
		texture = new TextureRegion(Art.cat);

		position.x = xPixels;
		position.y = yPixels;
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
		halfWidth = width / 2;
		halfHeight = height / 2;
		this.world = world;
		
		float xWorld = position.x / VillainGame.PIXELS_PER_METER;
		float yWorld = position.y / VillainGame.PIXELS_PER_METER;
		float widthWorld = width / VillainGame.PIXELS_PER_METER;
		float heightWorld = height / VillainGame.PIXELS_PER_METER;
		float halfWidthWorld = widthWorld / 2;
		float halfHeightWorld = heightWorld / 2;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(xWorld, yWorld);

		body = world.createBody(bodyDef);
		body.setUserData(this);

		PolygonShape box = new PolygonShape();
		box.setAsBox(halfWidthWorld, halfHeightWorld);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.2f; 
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f; // Make it bounce a little bit

		body.createFixture(fixtureDef);

		box.dispose();

		leftSpeed = new Vector2(-0.01f, 0.0f);
		rightSpeed = new Vector2(0.01f, 0.0f);
		upSpeed = new Vector2(0.0f, 1.0f);
		center = new Vector2(
				width / 2 / VillainGame.PIXELS_PER_METER,
				height / 2 / VillainGame.PIXELS_PER_METER);
		
		Random rnd = new Random();
		if (rnd.nextInt(2) == 0) {
			texture.flip(true, false);
//			body.applyLinearImpulse(rightSpeed, center);
		} else {
			texture.flip(false, false);
//			body.applyLinearImpulse(leftSpeed, center);			
		}
		
		
		// A Cat has 9 lives
		life = 3;
	}

	public boolean isAlive() {
		return life > 0;
	}
	
	@Override
	public void hit(PhysicsGameObject pgoB) {
		
		if (life <= 0) {
			return;
		}
		
		if (pgoB instanceof Cat) {
//			life -= 0.01f;
			return;
		}
		
		if (pgoB instanceof Player) {
			life -= 2f;
			return;
		}
		
		if (pgoB instanceof Platform) {
//			life -= 0.02f;
			return;
		}
		
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		// Cats always want their feet down!
		float currentAngle = body.getAngle();
		if (currentAngle != 0.0f) {
			float targetAngle = 0 * MathUtils.degreesToRadians;
			float totalRotation = targetAngle - currentAngle;
			while ( totalRotation < -180 * MathUtils.degreesToRadians) 
				totalRotation += 360 * MathUtils.degreesToRadians;
			while ( totalRotation >  180 * MathUtils.degreesToRadians) 
				totalRotation -= 360 * MathUtils.degreesToRadians;
			float maxChangePerTimeStep = 0.1f;
			float maxChange = Math.max(-maxChangePerTimeStep, totalRotation);
			float minChange = Math.min(maxChangePerTimeStep, maxChange);
			float newAngle = currentAngle + minChange;
			
			body.setTransform(body.getPosition(), newAngle);
		}		
	}
	
}
