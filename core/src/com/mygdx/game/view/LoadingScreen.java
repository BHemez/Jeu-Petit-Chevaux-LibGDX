package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JeuDesPetitsChevaux;

/**
 * LoadingScreen is the screen displayed when the app launch.
 * It allow the app to load every assets before giving
 * the users control. Prevent the game from freezing.
 */
public class LoadingScreen extends ScreenAdapter {
	private JeuDesPetitsChevaux parent; //Parent class of the screen
    private Stage stage; //A 2D scene graph containing hierarchies of actors. Stage handles the viewport and distributes input events.
    
    //Set of images used in the screen.
    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    private Actor loadingBar; //Animated image for the loading bar
    
    private float startX, endX; //Start and End position of the loading bar
    private float percent; //Loading progress in %

    /**
     * LoadingScreen's constructor
     */
    public LoadingScreen(JeuDesPetitsChevaux jdpc) {
        this.parent = jdpc;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

	/**
	 * show is called when this screen becomes the current screen
	 */
    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        parent.assetManager.queueAddLoading();
        // Wait until they are finished loading
        parent.assetManager.manager.finishLoading();

        // Get our textureatlas from the manager
        TextureAtlas atlas = parent.assetManager.manager.get("loading/loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
        Animation<AtlasRegion> anim = new Animation<AtlasRegion>(0.05f, atlas.findRegions("loading-bar-anim") );
        anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        loadingBar = new Animated(anim);

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        // Add everything to be loaded
		parent.assetManager.queueAddSounds();
		parent.assetManager.queueAddMusic();
		parent.assetManager.queueAddSkin();
		parent.assetManager.queueAddPions();
		parent.assetManager.queueAddPlayerIcon();
		parent.assetManager.queueAddButtons();
		parent.assetManager.queueAddDice();
		parent.assetManager.queueAddPossibleMove();
    }

	/**
	 * resize is called when the Application is resized.
	 */
    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);

        // Make the background fill the screen
        screenBg.setSize(width, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        logo.setY((stage.getHeight() - logo.getHeight()) / 2 + 100);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

	/**
	 * render is called when the screen should render itself.
	 * Calculate and displa the loading bar progression.
	 */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (parent.assetManager.manager.update()) { // will return true if done loading
            if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the menu screen
                parent.changeScreen(JeuDesPetitsChevaux.MENU);
            }
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, parent.assetManager.manager.getProgress(), 0.1f);
    	
        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }
    
	/**
	 * hide is called when this screen is no longer the current screen.
	 */
    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
    	parent.assetManager.manager.unload("loading/loading.pack");
    }
}
