package com.kylethatcher.game.tiles;

import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.utils.Utils;

public class ClearTile extends Tile{

	public ClearTile(int id, Image texture) {
		super(id, texture);
	}
	
	public ClearTile(int id, Image texture, int reflection) {
		super(id, Utils.reflectImage(texture, reflection));
	}
	
	public boolean isSolid() {
		return false;
	}
}
