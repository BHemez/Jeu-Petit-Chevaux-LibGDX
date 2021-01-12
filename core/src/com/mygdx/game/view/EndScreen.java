package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

/**
 * EndScreen is the screen displayed when a player win and end the game.
 */
public class EndScreen extends ScreenAdapter {
	private JeuDesPetitsChevaux parent; //Parent class of the screen
	private Stage stage; //A 2D scene graph containing hierarchies of actors. Stage handles the viewport and distributes input events.
	private Skin skin;//Appearence assets of the screen's UI
	//Simple text label
	private Label winnerLabel;
	private Label congratulationLabel;
	//Sounds the screen will use
	private Sound fanfare;
	
	/**
	 * EndScreen's constructor
	 * Create a new camera and fetch the assets
	 */
	public EndScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = parent.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
		fanfare = jdpc.assetManager.manager.get("triumphalFanfare/click.mp3", Sound.class);
	}
	
	/**
	 * show is called when this screen becomes the current screen
	 * Display which player is the winner
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		congratulationLabel = new Label( "Congratulation !", skin );
		congratulationLabel.setAlignment(Align.center);
		String winner;
		switch(this.parent.mainScreen.system.playerList.get(this.parent.mainScreen.system.playerTurn-1)) {
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
		
		fanfare.play(parent.preferences.getSoundVolume());
	}
 
	/**
	 * render is called when the screen should render itself.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
}