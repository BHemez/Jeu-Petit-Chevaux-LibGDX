package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

public class EndScreen extends ScreenAdapter {

	private JeuDesPetitsChevaux parent;
	private Stage stage;
	private Skin skin;
	
	private Label winnerLabel;
	private Label congratulationLabel;
	
	public EndScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = parent.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(false);		//Ajoute des boites pour visualiser l'emplacement des elements si true.
		stage.addActor(table);
		
		congratulationLabel = new Label( "Congratulation !", skin );
		congratulationLabel.setAlignment(Align.center);
		String winner;
		switch(this.parent.mainScreen.system.playerTurn) {
			case 1:
				winner = "RED won the race !";
				break;
			case 2:
				winner = "BLUE won the race !";
				break;
			case 3:
				winner = "Purple won the race !";
				break;
			case 4:
				winner = "GREEN won the race !";
				break;
			default:
				winner = "ERROR";
				break;
		}

		winnerLabel = new Label(winner, skin);
		winnerLabel.setAlignment(Align.center);
		
		table.add(congratulationLabel);
		table.row().pad(10,0,0,10);
		table.add(winnerLabel);
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
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