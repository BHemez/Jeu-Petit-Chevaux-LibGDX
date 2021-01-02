package com.mygdx.game.system;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.OrthogonalTiledMapRendererWithSprites;

public class GameMap {
	
    public TiledMap tiledMap;
    public OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    
    public static float TILESIZE;
    
    public static final float[][] POSITIONMATRIX = new float[][]
		{
			{7,7,7,7,7,7,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9},	//Position X des 56 cases
			{1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9,7,7,7,7,7,7,7,6,5,4,3,2,1,1}	//position Y des 56 cases
		};

	public static final float[][] REDLADDERPOSITIONMATRIX = new float[][]
		{
			{9,9,9,9,9,9,9},	//Position X des cases
			{2,3,4,5,6,7,9}		//position Y des cases
		};
		
	public static final float[][] BLUELADDERPOSITIONMATRIX = new float[][]
		{
			{2,3,4,5,6,7,9},	//Position X des cases
			{9,9,9,9,9,9,9}		//position Y des cases
		};	
	
	public static final float[][] PURPLELADDERPOSITIONMATRIX = new float[][]
		{
			{9,9,9,9,9,9,9},		//Position X des cases
			{16,15,14,13,12,11,9}	//position Y des cases
		};	

	public static final float[][] GREENLADDERPOSITIONMATRIX = new float[][]
		{
			{16,15,14,13,12,11,9},	//Position X des cases
			{9,9,9,9,9,9,9}			//position Y des cases
		};

	public GameMap(TiledMap tiledMap, float tileSize) {
		this.tiledMap = tiledMap;
		this.tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
		GameMap.TILESIZE = tileSize;
	}
    	
}
