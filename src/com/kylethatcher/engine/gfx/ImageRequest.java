package com.kylethatcher.engine.gfx;

public class ImageRequest {
	
	public Image image;
	public int offX,offY;
	public int zDepth;
	public ImageRequest(Image image, int zDepth, int offX, int offY) {
		this.zDepth = zDepth;
		this.offX = offX;
		this.offY = offY;
		this.image = image;
	}
}
