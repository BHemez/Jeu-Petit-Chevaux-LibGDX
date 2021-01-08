package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

public class MenuScreen extends ScreenAdapter{
	
	private JeuDesPetitsChevaux parent;
	private Stage stage;
	
	private Sound click;
	private Music jazz;
	private Skin skin;
	
	public static final int CLICK_SOUND = 0;
	
	public MenuScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		click = parent.assetManager.manager.get("sounds/click.mp3", Sound.class);
		jazz = parent.assetManager.manager.get("music/Jazz.mp3", Music.class);
		skin = parent.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
	}
	
	@Override
	public void show() {
		
		stage.clear();
		
		if(parent.getPreferences().isMusicEnabled()) {
			jazz.play();
		}

		jazz.setVolume(parent.getPreferences().getMusicVolume());
		
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(false);		//Ajoute des boites pour visualiser l'emplacement des elements si true.
		stage.addActor(table);
		
		//CREATION DES BOUTONS
		TextButton newGame = new TextButton("New Game", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		
		//AJOUT DES BOUTONS
		table.row().pad(0, 0, 10, 0);
		table.add(newGame).fillX().uniformX();
		table.row().pad(0, 0, 10, 0);
		table.add(preferences).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();

		
		//CREATION DES LISTENER DES BOUTONS
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				Gdx.app.exit();
			}
		});
		
		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				parent.changeScreen(JeuDesPetitsChevaux.PREFERENCES);		
			}
		});
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				parent.changeScreen(JeuDesPetitsChevaux.APPLICATION);		
			}
		});
		
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
		stage.getViewport().update(width, height, true);
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
		//jazz.pause();
		jazz.stop();
	}
 
	@Override
	public void dispose() {
		stage.dispose();
		jazz.dispose();
		parent.assetManager.manager.dispose();
	}
	
	public void playSound(int sound){
		if(parent.getPreferences().isSoundEffectsEnabled()) {
			switch(sound){
			case CLICK_SOUND:
				click.play(parent.getPreferences().getSoundVolume());
				break;
			}
		}
	}
	
}
