package com.kompetensum.villain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends PhysicsGameObject {

	private Vector2 leftSpeed;
	private Vector2 rightSpeed;
	private Vector2 upSpeed;
	private Vector2 center;
	
	private GameInput input;
	private int jumpTimer;
	private boolean isJumping;
	private float idleTimer;

	public Player(World world, GameInput input, float xPixels, float yPixels) {
		this.input = input;
		texture = new TextureRegion(Art.player);
		
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
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);
		body.setUserData(this);

		PolygonShape box = new PolygonShape();
		box.setAsBox(halfWidthWorld, halfHeightWorld);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.7f; 
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.3f; // Make it bounce a little bit

		Fixture fixture = body.createFixture(fixtureDef);

		box.dispose();
		
		leftSpeed = new Vector2(-0.05f, 0.0f);
		rightSpeed = new Vector2(0.05f, 0.0f);
		upSpeed = new Vector2(0.0f, 1.2f);
		center = new Vector2(
				width / 2 / VillainGame.PIXELS_PER_METER,
				height / 2 / VillainGame.PIXELS_PER_METER);
		
		jumpTimer = 0;
		isJumping = false;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		boolean anythingPressed = false;
		
		Vector2 currentVelocity = body.getLinearVelocity();
		float desiredVelocityX = 0;
		if (input.keysPressed[GameInput.RIGHT]) {
			anythingPressed = true;
			desiredVelocityX = Math.min(currentVelocity.x + 0.1f, 10f);
		} else if (input.keysPressed[GameInput.LEFT]) {
			anythingPressed = true;
			desiredVelocityX = Math.max(currentVelocity.x - 0.1f, -10f);
		} else
		{
			desiredVelocityX = currentVelocity.x * 0.9f;
		}
		currentVelocity.x = desiredVelocityX;
//		currentVelocity.y = 0;

		body.setLinearVelocity(currentVelocity);
//		body.applyLinearImpulse(currentVelocity, body.getWorldCenter());
		
		if (!isJumping) {
			if (input.keysPressed[GameInput.JUMP]) {
				anythingPressed = true;
				isJumping = true;
				Sounds.player_boing.play(0.6f);
				body.applyLinearImpulse(upSpeed, center);
				jumpTimer = 100;
			}
		} else {
			if (jumpTimer > 0) {
				jumpTimer--;
			}

			if (!input.keysPressed[GameInput.JUMP] && jumpTimer <= 0) {
				isJumping = false;
			}
		}
		

		if (anythingPressed == true) {
			idleTimer = 0;
			Sounds.player_lala.stop();
		} else {
			idleTimer += delta;
			if (idleTimer > 100) {
				Sounds.player_lala.play(0.5f);
				idleTimer = 0;
			}
		}
	}

	@Override
	public void hit(PhysicsGameObject pgoB) {
	}

	public void rotateTo(float angle) {
		// TODO Auto-generated method stub
		
	}
	
}
