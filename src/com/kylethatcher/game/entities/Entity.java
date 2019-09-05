package com.kylethatcher.game.entities;

import java.awt.Rectangle;

import com.kylethatcher.engine.Handler;

public abstract class Entity {

	protected int x;
	protected int y;
	
	protected Rectangle bounds;
	protected Handler handler;
	protected boolean active;
	
	public Entity(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
	}
	
	public abstract void render();
	
	public abstract void update(float dt);
	
	public abstract void remove();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
}
