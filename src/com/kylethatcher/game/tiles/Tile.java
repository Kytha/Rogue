package com.kylethatcher.game.tiles;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.utils.IDAssigner;


public class Tile {
	
	public static int TILEWIDTH = 32;
	public static int TILEHEIGHT = 32;
	
	public static int VERTICAL = 2;
	public static int HORIZONTAL = 1;
	public static int HORVER = 3;
	
	public boolean collisionFlag;
	private ArrayList<Rectangle> objects;

	//FOREST ASSETS
	public static IDAssigner tileID = new IDAssigner(1);
	public static Tile[] tiles = new Tile[256];

	
	protected int id;
	protected Image texture;
	
	public Tile(int id, Image texture, ArrayList<Rectangle> objects) {
		
		this.id = id;
		this.texture = texture;
		tiles[id] = this;
		this.objects = objects; 
		collisionFlag = true;

	}
	

	public Tile(int id, Image texture) {
		collisionFlag = false;
		this.id = id;
		this.texture = texture;
		tiles[id] = this;
		this.objects = null;
	}
	
	public void render(Handler handler, int x, int y) {
		if(x - TILEWIDTH *4 > handler.getGc().getWidth() || x + TILEWIDTH * 4 < 0 ) {
			return;
		}
		handler.getR().drawImage(texture, x, y);

		int depth = handler.getR().getzDepth();
		handler.getR().setzDepth(5);
		if(objects != null) {
			for(Rectangle r: objects) {
				
				handler.getR().drawRect(x + r.x, y + r.y, r.width, r.height, 0xFFFFFFFF, false);
				handler.getR().setzDepth(handler.getR().getzDepth()+1);
			}
		}
		handler.getR().setzDepth(depth);
	}
	

	public boolean isCollidable() {
		return collisionFlag;
	}

	public int getID() {
		return id;
	}
}
