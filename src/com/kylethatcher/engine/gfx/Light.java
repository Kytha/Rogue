package com.kylethatcher.engine.gfx;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.Renderer;
import com.kylethatcher.game.entities.Entity;

public class Light {
	
	public static final int NONE = 0;
	public static final int FULL = 1;
	public static final int SOME = 2;
	
	public int[]dlm;
	private int radius, diameter, color;
	private int avgRadius;
	private float timer;
	private int[] lm;
	private int x;
	private int y;
	private int radiusOffset;
	private boolean isGrowing;
	
	
	public Light(int radius, int color, int x, int y) {
		
		this.avgRadius = radius;
		this.radius = radius;
		this.diameter = radius * 2;
		this.color = color;
		this.x = x;
		this.y = y;
		lm = new int[diameter * diameter];
		dlm = new int[lm.length];
		radiusOffset = 0;
		isGrowing = true;
		timer = 0;
		
		for(int i = 0; i < diameter; i ++) {
			for(int j = 0; j < diameter; j ++) {
				double distance = Math.sqrt((j - radius)*(j - radius) + (i - radius)*(i - radius));
				
				if(distance < radius) {
					double power = 1 - (distance / radius);
					
					lm[j + i * diameter] = ((int)(((color >> 16) & 0xff) * power) << 16 | (int)(((color >> 8) & 0xff) * power) << 8 | (int)((color & 0xff) * power));
					dlm[j + i * diameter] = ((int)(((color >> 16) & 0xff) * (4*power/5)) << 16 | (int)(((color >> 8) & 0xff) * (4*power/5)) << 8 | (int)((color & 0xff) * (4*power/5)));
				}
				else {
					lm[j + i * diameter] = 0;
					dlm[j + i * diameter] = 0;
				}
			}
		}
	}
	
	public int getLightValue(int x, int y) {
		if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
			return 0;
		}
		return lm[x+y * diameter];
	}

	public void centerAroundEntity(Entity e) {
		x = e.getX() + e.getBounds().width/2 + e.getBounds().x;
		y = e.getY() + e.getBounds().height/2 + e.getBounds().y;
	}
	
	public int getDimLightValue(int x, int y) {
		if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
			return 0;
		}
		return dlm[x+y * diameter];
	}
	
	public void render(Handler handler) {
		float offX = handler.getGameCamera().getxOffset();
		float offY = handler.getGameCamera().getyOffset();
		handler.getR().drawLight(this,(int)(x - offX),(int) (y - offY));
	}
	
	public void update(float dt) {
		
		timer+= dt;
		if(timer > 0.20) {
			if(isGrowing == true)radiusOffset++;
			else { radiusOffset --;}
			recalibrateLight(radiusOffset);
			timer = 0;
		}
		if(radiusOffset > 3) isGrowing = false;
		if(radiusOffset < -3) isGrowing = true;
	}
	
	private void recalibrateLight(int radiusOffset) {
		radius = avgRadius + radiusOffset;
		diameter = (radius)*2;
		lm = new int[diameter * diameter];
		dlm = new int[lm.length];
		
		for(int i = 0; i < diameter; i ++) {
			for(int j = 0; j < diameter; j ++) {
				double distance = Math.sqrt((j - radius)*(j - radius) + (i - radius)*(i - radius));
				
				if(distance < radius) {
					double power = 1 - (distance / radius);
					
					lm[j + i * diameter] = ((int)(((color >> 16) & 0xff) * power) << 16 | (int)(((color >> 8) & 0xff) * power) << 8 | (int)((color & 0xff) * power));
					dlm[j + i * diameter] = ((int)(((color >> 16) & 0xff) * (4*power/5)) << 16 | (int)(((color >> 8) & 0xff) * (4*power/5)) << 8 | (int)((color & 0xff) * (4*power/5)));
				}
				else {
					lm[j + i * diameter] = 0;
					dlm[j + i * diameter] = 0;
				}
			}
		}
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;	
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int[] getLm() {
		return lm;
	}

	public void setLm(int[] lm) {
		this.lm = lm;
	}
}
