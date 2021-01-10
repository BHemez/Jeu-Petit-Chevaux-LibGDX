package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

public class PreferencesScreen extends ScreenAdapter{
	
	private JeuDesPetitsChevaux parent;
	private Stage stage;
	
	private Sound click;
	private Skin skin;
	
	private Label titleLabel; 
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
	
	private static final int CLICK_SOUND = 0;
	
	public PreferencesScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		click = parent.assetManager.manager.get("sounds/click.mp3", Sound.class);
		skin = parent.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
	}
	
	@Override
	public void show() {
		
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(false);		//Ajoute des boites pour visualiser l'emplacement des elements si true.
		stage.addActor(table);
		 
		//volume music
		final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
		        volumeMusicSlider.setValue( parent.getPreferences().getMusicVolume() );
		        volumeMusicSlider.addListener( new EventListener() {
		  		@Override
				public boolean handle(Event event) {
		  			parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
		                return false;
			}
		});
		        
		//volume sound
		final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
				volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume() );
				volumeSoundSlider.addListener( new EventListener() {
		  		@Override
				public boolean handle(Event event) {
		  			parent.getPreferences().setSoundVolume( volumeSoundSlider.getValue() );
		                return false;
			}
		});        
               
		 //music
		final CheckBox musicCheckbox = new CheckBox(null, skin);
		musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
		musicCheckbox.addListener( new EventListener() {
		   	@Override
			public boolean handle(Event event) {
		       	boolean enabled = musicCheckbox.isChecked();
		       	parent.getPreferences().setMusicEnabled( enabled );
		       	return false;
			}
		});
		
		 //sound
		final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
		soundEffectsCheckbox.setChecked( parent.getPreferences().isSoundEffectsEnabled() );
		soundEffectsCheckbox.addListener( new EventListener() {
		   	@Override
			public boolean handle(Event event) {
		       	boolean enabled = soundEffectsCheckbox.isChecked();
		       	parent.getPreferences().setSoundEffectsEnabled( enabled );
		       	return false;
			}
		});
		     
		// return to main screen button
		final TextButton backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playSound(CLICK_SOUND);
				parent.changeScreen(JeuDesPetitsChevaux.MENU);
			}
		});

		titleLabel = new Label( "Preferences", skin );
		volumeMusicLabel = new Label( "Music Volume", skin );
		volumeSoundLabel = new Label( "Sound Volume", skin );
		musicOnOffLabel = new Label( "Music", skin );
		soundOnOffLabel = new Label( "Sound Effect", skin );
			
		table.add(titleLabel).colspan(2);
		table.row().pad(10,0,0,10);
		table.add(volumeMusicLabel).left();
		table.add(volumeMusicSlider);
		table.row().pad(10,0,0,10);
		table.add(musicOnOffLabel).left();
		table.add(musicCheckbox);
		table.row().pad(10,0,0,10);
		table.add(volumeSoundLabel).left();
		table.add(volumeSoundSlider);
		table.row().pad(10,0,0,10);
		table.add(soundOnOffLabel).left();
		table.add(soundEffectsCheckbox);
		table.row().pad(10,0,0,10);
		table.add(backButton).colspan(2);		
		
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		if(parent.getPreferences().isMusicEnabled()) {
			parent.menuScreen.jazz.play();
			parent.menuScreen.jazz.setVolume(parent.getPreferences().getMusicVolume());
		} else {
			parent.menuScreen.jazz.pause();
		}
	}
 
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	public void playSound(int sound) {
		if(parent.getPreferences().isSoundEffectsEnabled()) {
			switch(sound){
			case CLICK_SOUND:
				click.play(parent.getPreferences().getSoundVolume());
				break;
			}
		}
	}
	
}
