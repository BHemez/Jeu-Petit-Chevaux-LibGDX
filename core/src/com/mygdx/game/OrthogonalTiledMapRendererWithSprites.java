package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 * OrthogonalTiledMapRendererWithSprites is an extension of libGDX's OrthogonalTiledMapRenderer
 * wich is used to render a Tiled map int the game.
 * Here it does the same but with the capacity to render sprite on top of the map.
 */
public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
	/**
	 * The list of sprite to render on the map.
	 */
    private List<Sprite> sprites;
	/**
	 * The layer on which the sprite are rendered.
	 * Allow sprite to be behind some ellements of the map.
	 */
    private int drawSpritesAfterLayer = 1;
    
	/**
	 * OrthogonalTiledMapRendererWithSprites's constructor.
	 */
    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        sprites = new ArrayList<Sprite>();
    }
    
	/**
	 * addSprite(Sprite) is used to simply add a sprite to the list
	 * of sprites too be rendered.
	 */
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }
    
	/**
	 * render() renders all the layers of the map
	 * is called automatically every frame when the map is displayed.
	 */
    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(Sprite sprite : sprites)
                            sprite.draw(this.getBatch());
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }
    
}