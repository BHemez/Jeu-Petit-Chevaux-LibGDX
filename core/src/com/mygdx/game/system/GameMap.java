package com.mygdx.game.system;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.OrthogonalTiledMapRendererWithSprites;

/**
 * GameMap class, used to store the map renderer, map's properties
 * and also the position our game will need to know.
 */
public class GameMap {
	/**
	 * Our Map renderer.
	 */
    public OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    /**
     * Float used to know the map's dimention and the size of each tile.
     */
    public static float TILESIZE;
    public static float MAP_WIDTH;
    public static float MAP_HEIGHT;
    /**
     * Matrix of the X and Y position of the racetrack's tiles
     */
    public static final float[][] POSITIONMATRIX = new float[][]
		{
			{7,7,7,7,7,7,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9},	//Position X des 56 cases
			{1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9,7,7,7,7,7,7,7,6,5,4,3,2,1,1}	//position Y des 56 cases
		};

    /**
     * Matrix of the X and Y position of the red team ladder's tiles
     */
	public static final float[][] REDLADDERPOSITIONMATRIX = new float[][]
		{
			{9,9,9,9,9,9,9},	//Position X des cases
			{2,3,4,5,6,7,9}		//position Y des cases
		};
    /**
     * Matrix of the X and Y position of the blue team ladder's tiles
     */
	public static final float[][] BLUELADDERPOSITIONMATRIX = new float[][]
		{
			{2,3,4,5,6,7,9},	//Position X des cases
			{9,9,9,9,9,9,9}		//position Y des cases
		};	
    /**
     * Matrix of the X and Y position of the purple team ladder's tiles
     */
	public static final float[][] PURPLELADDERPOSITIONMATRIX = new float[][]
		{
			{9,9,9,9,9,9,9},		//Position X des cases
			{16,15,14,13,12,11,9}	//position Y des cases
		};	
    /**
     * Matrix of the X and Y position of the green team ladder's tiles
     */
	public static final float[][] GREENLADDERPOSITIONMATRIX = new float[][]
		{
			{16,15,14,13,12,11,9},	//Position X des cases
			{9,9,9,9,9,9,9}			//position Y des cases
		};

    /**
     * GameMap's constructor, create a renderer using a TiledMap and fetch
     * the map's properties.
     */
	public GameMap(TiledMap tiledMap) {
		this.tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
		MapProperties prop = tiledMap.getProperties();
		GameMap.TILESIZE = prop.get("tilewidth", Integer.class);
		GameMap.MAP_WIDTH = prop.get("width", Integer.class);
		GameMap.MAP_HEIGHT = prop.get("height", Integer.class);
	}
    	
}
