package com.mygdx.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class MouseKeyboardController implements InputProcessor{

	public boolean left,right,up,down,escape;
	public boolean isMouse1Down, isMouse2Down,isMouse3Down;
	public boolean isDragged;
	public Vector2 mouseLocation = new Vector2(0,0);
	
	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false; //Permet la gestion de plusieurs touches en meme temps
		switch (keycode) 
		{

		        case Keys.LEFT:
		            left = true;
		            keyProcessed = true;
		            break;
		        case Keys.RIGHT:
		            right = true;
		            keyProcessed = true;
		            break;
		        case Keys.UP:
		            up = true;
		            keyProcessed = true;
		            break;
		        case Keys.DOWN:
		            down = true;
		            keyProcessed = true;
		            break;
		        case Keys.ESCAPE:
		        	escape = true;
		        	keyProcessed = true;
	    }
		return keyProcessed;
	}

@Override
	public boolean keyUp(int keycode) {
	boolean keyProcessed = false;
	switch (keycode)
        {
	        case Keys.LEFT:
	            left = false;
	            keyProcessed = true;
	            break;
	        case Keys.RIGHT:
	            right = false;
	            keyProcessed = true;
	            break;
	        case Keys.UP:
	            up = false;
	            keyProcessed = true;
	            break;
	        case Keys.DOWN:
	            down = false;
	            keyProcessed = true;
	        case Keys.ESCAPE:
	        	escape = false;
	        	keyProcessed = true;
        }
	return keyProcessed;
}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == 0){
			isMouse1Down = true;
		}else if(button == 1){
			isMouse2Down = true;
		}else if(button == 2){
			isMouse3Down = true;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDragged = false;
		if(button == 0){
			isMouse1Down = false;
		}else if(button == 1){
			isMouse2Down = false;
		}else if(button == 2){
			isMouse3Down = false;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragged = true;
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
