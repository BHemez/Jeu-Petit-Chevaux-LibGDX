package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;

public class LoadingScreen extends ScreenAdapter{

	private JeuDesPetitsChevaux parent;
	
	public LoadingScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void render(float delta) {
		parent.changeScreen(JeuDesPetitsChevaux.MENU);
	}
 
	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
}
