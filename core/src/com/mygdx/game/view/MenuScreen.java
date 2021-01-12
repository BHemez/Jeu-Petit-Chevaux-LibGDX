package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

/**
 * MenuScreen is the sceen showing the app menu
 */
public class MenuScreen extends ScreenAdapter{
	
	private JeuDesPetitsChevaux parent; //Parent class of the screen.
	private Stage stageMenu;  //Stage holding the default menu actors.
	private Stage stageMenuResume; //Stage holding the menu actors with the resume button
	private Stage stageChoice; //Stage holding the menu actors when the user need to choose a number of player
	
	private int currentMenu; //int to keep track of wich actor to display
	private static final int MENU = 0;
	private static final int MENU_RESUME = 1;
	private static final int MENU_CHOICE = 2;
	
	//Sound and music the screen will use
	private Sound click;
	private static final int CLICK_SOUND = 0;
	public Music jazz;
	
	private Skin skin; //Appearence assets of the screen's UI
	
	/**
	 * MenuScreen's constructor
	 */
	public MenuScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		stageMenu = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stageMenuResume = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stageChoice = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.currentMenu = MENU;
		click = parent.assetManager.manager.get("sounds/click.mp3", Sound.class);
		jazz = parent.assetManager.manager.get("music/Jazz.mp3", Music.class);
		skin = parent.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
	}
	
	/**
	 * show is called when this screen becomes the current screen
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stageMenu);
		
		//=== BUTTONS AND TABLE CREATION ===
		//Defauult Menu
		TextButton newGame = new TextButton("New Game", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		Table tableMenu = new Table();
		tableMenu.setFillParent(true);
		tableMenu.setDebug(false);	//Ajoute des boites pour visualiser l'emplacement des elements si true.
		tableMenu.row().pad(0, 0, 10, 0);
		tableMenu.add(newGame).fillX().uniformX();
		tableMenu.row().pad(0, 0, 10, 0);
		tableMenu.add(preferences).fillX().uniformX();
		tableMenu.row();
		tableMenu.add(exit).fillX().uniformX();
		stageMenu.addActor(tableMenu);
	
		//Menu with Resume button
		TextButton resume = new TextButton("Resume Game", skin);
		TextButton newGameR = new TextButton("New Game", skin);
		TextButton preferencesR = new TextButton("Preferences", skin);
		TextButton exitR = new TextButton("Exit", skin);
		Table tableMenuResume = new Table();
		tableMenuResume.setFillParent(true);
		tableMenuResume.setDebug(false);				//Ajoute des boites pour visualiser l'emplacement des elements si true.
		tableMenuResume.row().pad(0, 0, 10, 0);
		tableMenuResume.add(newGameR).fillX().uniformX();
		tableMenuResume.add(resume).fillX().uniformX();
		tableMenuResume.row().pad(0, 0, 10, 0).colspan(2);
		tableMenuResume.add(preferencesR).fillX().uniformX();
		tableMenuResume.row().colspan(2);
		tableMenuResume.add(exitR).fillX().uniformX();
		stageMenuResume.addActor(tableMenuResume);
		
		//Menu with playerNumber choice
		Label howMany = new Label("How many players ?", skin);
		TextButton newGame2P = new TextButton("2P", skin);
		TextButton newGame3P = new TextButton("3P", skin);
		TextButton newGame4P = new TextButton("4P", skin);
		TextButton back = new TextButton("Back", skin);
		Table tableChoice = new Table();
		tableChoice.setFillParent(true);
		tableChoice.setDebug(false);		//Ajoute des boites pour visualiser l'emplacement des elements si true.
		tableChoice.add(howMany).colspan(3);
		tableChoice.row().pad(0, 0, 10, 0);
		tableChoice.add(newGame2P).fillX().uniformX();
		tableChoice.add(newGame3P).fillX().uniformX();
		tableChoice.add(newGame4P).fillX().uniformX();
		tableChoice.row().pad(0, 0, 10, 0);
		tableChoice.add(back).fillX().uniformX().colspan(3);
		stageChoice.addActor(tableChoice);
		
		//=== BUTTON'S LISTENER CREATION ===
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				Gdx.app.exit();
			}
		});
		
		exitR.addListener(new ChangeListener() {
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
		
		preferencesR.addListener(new ChangeListener() {
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
				currentMenu = MENU_CHOICE;
			}
		});
		
		newGameR.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				currentMenu = MENU_CHOICE;
			}
		});
		
		newGame2P.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				currentMenu = MENU_RESUME;
				parent.changeScreen(JeuDesPetitsChevaux.APPLICATION_2P);		
			}
		});
		
		newGame3P.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				currentMenu = MENU_RESUME;
				parent.changeScreen(JeuDesPetitsChevaux.APPLICATION_3P);		
			}
		});
		
		newGame4P.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				currentMenu = MENU_RESUME;
				parent.changeScreen(JeuDesPetitsChevaux.APPLICATION_4P);		
			}
		});
		
		resume.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				parent.changeScreen(JeuDesPetitsChevaux.RESUME);		
			}
		});
		
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				if(parent.mainScreen == null) {
					currentMenu = MENU;
				} else {
					currentMenu = MENU_RESUME;
				}
			}
		});
		
	}
 
	/**
	 * render is called when the screen should render itself.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		switch(currentMenu) {
		case MENU:
			Gdx.input.setInputProcessor(stageMenu);
			stageMenu.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stageMenu.draw();
			break;
		case MENU_RESUME:
			Gdx.input.setInputProcessor(stageMenuResume);
			stageMenuResume.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stageMenuResume.draw();
			break;
		case MENU_CHOICE:
			Gdx.input.setInputProcessor(stageChoice);
			stageChoice.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stageChoice.draw();
			break;
		}
		
		if(parent.preferences.isMusicEnabled()) {
			jazz.play();
			jazz.setVolume(parent.preferences.getMusicVolume());
		} else {
			jazz.pause();
		}
		
	}
 
	/**
	 * resize is called when the Application is resized.
	 */
	@Override
	public void resize(int width, int height) {
		stageMenu.getViewport().update(width, height, true);
		stageMenuResume.getViewport().update(width, height, true);
		stageChoice.getViewport().update(width, height, true);
	}
	
	/**
	 * playSound is called when a sound needs to be played
	 */
	public void playSound(int sound) {
		if(parent.preferences.isSoundEffectsEnabled()) {
			switch(sound){
			case CLICK_SOUND:
				click.play(parent.preferences.getSoundVolume());
				break;
			}
		}
	}
	
}
