package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Animated class, used to extend animated actors
 */
public class Animated extends Actor {
	private Animation<AtlasRegion> animation;//libGDX's animation class, used to store animated content
	private TextureRegion reg;//Image used by the animation on a single frame
	private float stateTime;//Keep track of time in the app

	/**
	 * Animated's constructor
	 */
    public Animated(Animation<AtlasRegion> animation) {
        this.animation = animation;
        reg = animation.getKeyFrame(0);
    }

    /**
     * act is called every frame to update the actor with a new image
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        reg = animation.getKeyFrame(stateTime);
    }

    /**
     * draw is called when the actor need to be displayed
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(reg, getX(), getY());
    }
}
