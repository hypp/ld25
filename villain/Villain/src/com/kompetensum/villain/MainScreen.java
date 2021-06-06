package com.kompetensum.villain;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class MainScreen implements Screen, ContactListener {
	
	private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batcher;
	private Array<GameObject> sprites = new Array<GameObject>();
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Player player;
	
	private Array<Platform> playerTouches = new Array<Platform>();
	
	private static final float BOX_STEP=1/120f;
	private static final int  BOX_VELOCITY_ITERATIONS=8;
	private static final int BOX_POSITION_ITERATIONS=3;
	private float accumulator;
	private long catsKilled;
	private Pixmap pixmap;
	private Texture bg;
	private BitmapFont font;
	private GameInput input;
	
	private Random rnd = new Random();
	
	public MainScreen(Game game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		new Vector3();
		batcher = new SpriteBatch();
		
		input = new GameInput();
		Gdx.input.setInputProcessor(input);
		
		world = new World(new Vector2(0, -10), true);
		world.setContactListener(this);
		debugRenderer = new Box2DDebugRenderer();
		
		Platform ground = new Platform(world, 800 / 2, 5, 800, 2, 0);
		sprites.add(ground);
		
		Platform top = new Platform(world, 300, 400, 500, 10, -5);
		sprites.add(top);

		Platform left1 = new Platform(world, 200, 100, 300, 10, -10);
		sprites.add(left1);

		Platform left2 = new Platform(world, 300, 200, 300, 10, 0);
		sprites.add(left2);

		Platform right1 = new Platform(world, 500, 300, 300, 10, 15);
		sprites.add(right1);

		Platform leftWall = new Platform(world, 5, 600 / 2, 2, 600, 0);
		sprites.add(leftWall);

		Platform rightWall = new Platform(world, 800 - 5, 600 / 2, 2, 600, 0);
		sprites.add(rightWall);

		
		player = new Player(world, input, 690, 610);
		sprites.add(player);
		Sounds.player_falling.play(0.3f);

		pixmap = new Pixmap(800, 600, Format.RGB888);
		pixmap.setColor(0xc8e1ffff);
		pixmap.fill();
		
//		bg = new Texture(pixmap);
		bg = new Texture(800, 600, Format.RGB888);		
		
		font = new BitmapFont();
		
	}

	@Override
	public void render(float delta) {
		draw(delta);
		update(delta);
	}
	
	public void draw(float delta) {
		Gdx.gl.glClearColor(0, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		bg.draw(pixmap, 0, 0);

		batcher.setProjectionMatrix(camera.combined);
		batcher.begin();
		batcher.enableBlending();
//		batcher.disableBlending();
		batcher.draw(bg, 0, 0, 800, 600);
		
		Iterator<GameObject> gi = sprites.iterator();
		while (gi.hasNext()) {
			GameObject g = gi.next();
			g.draw(batcher);
		}
		font.draw(batcher, String.format("Cats killed: %d",catsKilled), 10, 600 - 10);
		batcher.end();

		// Lets render it in debug
//		Matrix4 debugMatrix = new Matrix4(camera.combined);
//		debugMatrix.scale(VillainGame.PIXELS_PER_METER, VillainGame.PIXELS_PER_METER, 0);
//		debugRenderer.render(world, debugMatrix);		
	}
		
	public void update(float delta) {
		accumulator+=delta;
    	while(accumulator>BOX_STEP){
    		world.step(BOX_STEP,BOX_VELOCITY_ITERATIONS,BOX_POSITION_ITERATIONS);
      		accumulator-=BOX_STEP;
    	}

    	Iterator<GameObject> si = sprites.iterator();
		while (si.hasNext()) {
			GameObject go = si.next();
			go.update(delta);
		}
		
		if (sprites.size < 200) {
			if (sprites.size < 15 || Gdx.input.isKeyPressed(Keys.SPACE)) {
				Cat cat = new Cat(world, rnd.nextInt(600) + 100, 700);
				sprites.add(cat);
			}
		}

    	if (playerTouches.size > 0) {
    		float angle = playerTouches.get(0).getBody().getAngle();
    		player.rotateTo(angle);
    	} else {
    		player.rotateTo(0);
    	}
		
		// remove all dead cats
    	boolean deadCat = false;
		Iterator<GameObject> sit = sprites.iterator();
		while (sit.hasNext()) {
			GameObject go = sit.next();
		    if (go instanceof Cat) {
		    	Cat cat = (Cat) go;
		    	if (!cat.isAlive()) {
		    		deadCat = true;
		    		catsKilled++;
		    		
		    		sprites.removeValue(cat, false);
		    		Body body = cat.getBody();
		    		world.destroyBody(body);
		    		body.setUserData(null);
		    		
		    		Vector2 pos = cat.getPosition();
		    		addBlood(pos);
		    	}
		    }
		}
		if (deadCat) {
			int num = rnd.nextInt(Sounds.mjau.size);
    		Sounds.mjau.get(num).play(0.1f);
    		
    		if (catsKilled % 50 == 49) {
    	   		Sounds.player_haha.play(0.8f);						
    		}
		}
		
		
		if (catsKilled > 500) {
			game.setScreen(new GameOverScreen(game));
		}
	}

	private void addBlood(Vector2 pos) {
		int x = (int) pos.x - Art.blood.getWidth() / 2 + (rnd.nextInt(10) - 5);
		int y = (600 - (int)pos.y) - Art.blood.getHeight() / 2 + (rnd.nextInt(10) - 5);
		pixmap.drawPixmap(Art.blood, x, y);
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
	}

	@Override
	public void beginContact(Contact contact) {
		// Figure what we are touching
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		Body bA = fA.getBody();
		Body bB = fB.getBody();
		PhysicsGameObject pgoA = (PhysicsGameObject)bA.getUserData();
		PhysicsGameObject pgoB = (PhysicsGameObject)bB.getUserData();
		
		pgoA.hit(pgoB);
		pgoB.hit(pgoA);
		
		if (pgoA instanceof Player && pgoB instanceof Platform) {
			playerTouches.add((Platform) pgoB);
		}
		
		if (pgoB instanceof Player && pgoA instanceof Platform) {
			playerTouches.add((Platform) pgoA);
		}

	}

	@Override
	public void endContact(Contact contact) {
		// Figure what we are touching
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		Body bA = fA.getBody();
		Body bB = fB.getBody();
		PhysicsGameObject pgoA = (PhysicsGameObject)bA.getUserData();
		PhysicsGameObject pgoB = (PhysicsGameObject)bB.getUserData();

		if (pgoA instanceof Player && pgoB instanceof Platform) {
			playerTouches.removeValue((Platform) pgoB,false);
		}
		
		if (pgoB instanceof Player && pgoA instanceof Platform) {
			playerTouches.removeValue((Platform) pgoA,false);
		}		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		float impulses[] = impulse.getNormalImpulses();
		
		float maxImp = 0;
		for (int i = 0; i < impulses.length; i++) {
			if (impulses[i] > maxImp) {
				maxImp = impulses[i];
			}
		}
		
		// We only collide if it is hard enough
		if (impulses[0] > 1 || impulses[1] > 1) {
			// Figure what we are touching
/*			Fixture fA = contact.getFixtureA();
			Fixture fB = contact.getFixtureB();
			Body bA = fA.getBody();
			Body bB = fB.getBody();
			PhysicsGameObject pgoA = (PhysicsGameObject)bA.getUserData();
			PhysicsGameObject pgoB = (PhysicsGameObject)bB.getUserData();
			
			pgoA.hit();
			pgoB.hit();
*/		}
		
	}

}
