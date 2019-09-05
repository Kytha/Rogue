package com.kylethatcher.engine.gfx;

public class ImageTile extends Image {
	private int tileW,tileH;

	public ImageTile(String path, int tileW, int tileH) {
		super(path);
		this.tileW = tileW;
		this.tileH = tileH;
	}
	
	public ImageTile(int[] p, int width, int height, int tileW, int tileH) {
		super(p,width,height);
		this.tileW = tileW;
		this.tileH = tileH;
	}
	public Image getTileImage(int tileX, int tileY) {
		int[] p = new int[tileW * tileH];
		
		for(int y = 0; y < tileH; y ++) {
			
			for(int x = 0; x < tileW; x ++) {
				p[x + y * tileW] = this.getP()[(x + tileX * tileW) + (y + tileY * tileH) *this.getW()];
				
			}
		}
		
		return new Image(p, tileW, tileH);
	}
	public Image getTileImage(int tileX, int tileY, int modX, int modY) {
		int newWidth = tileW * modX;
		int newHeight = tileH * modY;
		int[] p = new int[(newWidth) * (newHeight)];
		for(int y = 0; y < newHeight; y ++) {
			
			for(int x = 0; x < newWidth; x ++) {
				p[x + y * newWidth] = this.getP()[(x + tileX * tileW) + (y + tileY * tileH) *this.getW()];
				
			}
		}
		
		return new Image(p, newWidth, newHeight);
	}
	
	public ImageTile getSpriteSheet(int tileX, int tileY, int modX, int modY) {
		int newWidth = tileW * modX;
		int newHeight = tileH * modY;
		int[] p = new int[(newWidth) * (newHeight)];
		for(int y = 0; y < newHeight; y ++) {
			
			for(int x = 0; x < newWidth; x ++) {
				p[x + y * newWidth] = this.getP()[(x + tileX * tileW) + (y + tileY * tileH) *this.getW()];
				
			}
		}
		
		return new ImageTile(p, newWidth, newHeight, this.tileW, this.tileH);
	}

	public int getTileW() {
		return tileW;
	}

	public void setTileW(int tileW) {
		this.tileW = tileW;
	}

	public int getTileH() {
		return tileH;
	}

	public void setTileH(int tileH) {
		this.tileH = tileH;
	}
}
