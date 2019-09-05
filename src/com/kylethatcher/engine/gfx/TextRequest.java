package com.kylethatcher.engine.gfx;

public class TextRequest {
	public String text;
	public int offX;
	public int offY;
	public int color;
	public int size;
	
	public TextRequest(String text, int offX, int offY, int color, int size) {
		this.text = text;
		this.offX = offX;
		this.offY = offY;
		this.color = color;
		this.size = size;
	}
}
