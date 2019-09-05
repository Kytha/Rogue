package com.kylethatcher.engine.gui;


import com.kylethatcher.engine.Handler;

import java.awt.Rectangle;


public abstract class GuiComponent {

	protected int x,y,w,h;

	protected Rectangle bounds;
	protected boolean hovering = false;
	protected boolean focusable = true;
	protected GuiFrame parent;
	
	public GuiComponent(int x, int y, int w, int h, GuiFrame parent) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
		this.parent = parent;
		bounds = new Rectangle(x,y,w,h);
	}
	
	public abstract void render(Handler handler, int offX, int offY, int zDepth);
	

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

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setH(int h) {
		this.h = h;
	}

	public boolean isFocusable() {
		return focusable;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	public GuiFrame getParent() {
		return parent;
	}
	
}
