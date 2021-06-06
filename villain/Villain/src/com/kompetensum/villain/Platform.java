package com.kompetensum.villain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.MathUtils;

public class Platform extends PhysicsGameObject {
		
	public Platform(World world, float xPixels, float yPixels, float widthPixels, float heightPixels, float rotation) 
	{
		position.x = xPixels;
		position.y = yPixels;
		width = widthPixels;
		height = heightPixels;
		halfWidth = width / 2;
		halfHeight = height / 2;
		this.rotation = rotation;
		this.world = world;
		
		float xWorld = position.x / VillainGame.PIXELS_PER_METER;
		float yWorld = position.y / VillainGame.PIXELS_PER_METER;
		float widthWorld = width / VillainGame.PIXELS_PER_METER;
		float heightWorld = height / VillainGame.PIXELS_PER_METER;
		float halfWidthWorld = widthWorld / 2;
		float halfHeightWorld = heightWorld / 2;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(xWorld, yWorld);
		bodyDef.angle = MathUtils.degreesToRadians * rotation;

		body = world.createBody(bodyDef);
		body.setUserData(this);

		PolygonShape box = new PolygonShape();
		box.setAsBox(halfWidthWorld, halfHeightWorld);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.7f; 
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.3f; // Make it bounce a little bit

		Fixture fixture = body.createFixture(fixtureDef);

		box.dispose();
		
		texture = new TextureRegion(Art.platform);
	}
	
}
