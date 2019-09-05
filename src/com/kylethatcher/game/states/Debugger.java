package com.kylethatcher.game.states;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.TextRequest;
import com.kylethatcher.game.tiles.Tile;

public class Debugger {
	
	private Tile[] hoveringTiles;
	private Handler handler;
	private int mouseX;
	private int mouseY;
	private int tileX;
	private int tileY;
	private int offY;
	private int offX;
	private ArrayList<TextRequest> textRequests;
	
	public Debugger(Handler handler) {
		this.handler = handler;
		textRequests = new ArrayList<TextRequest>();
		hoveringTiles = new Tile[2];
	}
	
	public void update(float dt) {
		
		mouseX = handler.getGc().getInput().getMouseX();
		mouseY = handler.getGc().getInput().getMouseY();
		offX = (int)handler.getGameCamera().getxOffset();
		offY = (int)handler.getGameCamera().getyOffset();
		tileX = (mouseX+ offX)/Tile.TILEWIDTH;
		tileY = (mouseY+ offY)/Tile.TILEHEIGHT;
		textRequests.add(new TextRequest("Tile X: " + Integer.toString(tileX), mouseX, mouseY - 11, 0xFFFFFFFF,1));
		textRequests.add(new TextRequest("Tile Y: " + Integer.toString(tileY), mouseX, mouseY - 5, 0xFFFFFFFF,1));

		
		hoveringTiles[0] = handler.getMap().getTile((mouseX + offX), (mouseY + offY),0);
		hoveringTiles[1] = handler.getMap().getTile((mouseX + offX), (mouseY + offY),1);
		
	}
	
	public void render(){
		for(int i = 0; i < textRequests.size(); i ++) {
			TextRequest tR = textRequests.get(i);
			handler.getR().drawText(tR.text, tR.offX, tR.offY, tR.color);
		}
		textRequests.clear();
		/*
		handler.getR().drawRect((tileX * Tile.TILEWIDTH - offX), ((tileY) * Tile.TILEHEIGHT - offY), Tile.TILEWIDTH, Tile.TILEHEIGHT, 0xff00ffff, false);
		if(hoveringTiles[0] != null && hoveringTiles[0].getBounds() != null) {
			Rectangle bounds = hoveringTiles[0].getBounds();
			handler.getR().drawRect((tileX * Tile.TILEWIDTH - offX + bounds.x),((tileY) * Tile.TILEHEIGHT - offY+ bounds.y), bounds.width, bounds.height, 0xffff0000, false);
		}
		
		if(hoveringTiles[1] != null && hoveringTiles[1].getBounds() != null) {
			Rectangle bounds = hoveringTiles[1].getBounds();
			handler.getR().drawRect((tileX * Tile.TILEWIDTH - offX + bounds.x),((tileY) * Tile.TILEHEIGHT - offY+ bounds.y), bounds.width, bounds.height, 0xFFff0000, false);
		}
		*/
	}
}
