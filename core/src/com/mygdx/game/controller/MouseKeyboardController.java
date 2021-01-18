package com.mygdx.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * MouseKeyboardController, implementation of libGDX's InputProcessor
 * allow to keep track of the user's inputs using mouse and Keyboard.
 */
public class MouseKeyboardController implements InputProcessor {

	/**
	 * A list of boolean for each key, click or action the app needs 
	 * to kepp track of.
	 */
	public boolean spacebar,escape;
	public boolean isMouse1Down, isMouse2Down,isMouse3Down;
	public boolean isDragged;
	/**
	 * Vector of the X and Y position of the mouse.
	 */
	public Vector2 mouseLocation = new Vector2(0,0);
	
	/**
	 * keyDown(int) called when a key is pressed down. The keycode allow
	 * to set the corresponding boolean to the right state.
	 */
	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		switch (keycode) 
		{

		        case Keys.SPACE:
		        	spacebar = true;
		            keyProcessed = true;
		            break;
		        case Keys.ESCAPE:
		        	escape = true;
		        	keyProcessed = true;
	    }
		return keyProcessed;
	}

	/**
	 * keyUp(int) called when a key is released. The keycode allow
	 * to set the corresponding boolean to the right state.
	 */
	@Override
		public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch (keycode)
	        {
		        case Keys.SPACE:
		        	spacebar = false;
		            keyProcessed = true;
		            break;
		        case Keys.ESCAPE:
		        	escape = false;
		        	keyProcessed = true;
	        }
		return keyProcessed;
	}

	/**
	 * touchDown(int,int,int,int) is used when the user click on the screen.
	 * Also work for mobile.
	 * Button 0 is left click
	 * Button 1 is midle click
	 * Button 2 is right click
	 * Pointer is the number of thing pointing at the screen
	 */
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

	/**
	 * same thing as touchDown but when the click/finger is released.
	 */
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

	/**
	 * touchDragged is called when touchDown is called and the mouse move over.
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragged = true;
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	/**
	 * called when the mouse just change position.
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	/**
	 * Called when a key is pressed.
	 * Unused in this app.
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Called when the mouse scroll.
	 * Unused in this app.
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
