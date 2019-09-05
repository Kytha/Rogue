package com.kylethatcher.engine.gui;

import java.awt.event.MouseEvent;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Font;

public class Text extends GuiPassiveComponent  {


	private String text;
	private int color;
	private int length;
	
	public Text(int x, int y, String text, int color, GuiFrame parent) {
		super(x,y,Font.HEIGHT, Font.UNIT_WIDTH * text.length(), parent);
		this.text = text;
		length = text.length() * Font.UNIT_WIDTH;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public void render(Handler handler, int offX, int offY, int zDepth) {
		// TODO Auto-generated method stub
		handler.getR().drawText(text,x + offX , y + offY, color);
	}
}
