package com.kompetensum.villain;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameInput implements InputProcessor {
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public static final int JUMP = 4;
	
	public static final int DROP = 5;
	
	public boolean keysPressed[] = new boolean[6];

	public GameInput() {
		for (int i = 0; i < keysPressed.length; i++) {
			keysPressed[i] = false;
		}
	}
	
	private void handle(int keycode, boolean down) {
		
		switch (keycode)
		{
		case Keys.DPAD_LEFT:
			keysPressed[LEFT] = down;
			break;
		case Keys.DPAD_RIGHT:
			keysPressed[RIGHT] = down;
			break;
		case Keys.DPAD_UP:
			keysPressed[JUMP] = down;
			break;
		case Keys.SPACE:
			keysPressed[DROP] = down;
			break;
		}
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		handle(keycode,true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		handle(keycode,false);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
