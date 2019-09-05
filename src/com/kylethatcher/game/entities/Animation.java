package com.kylethatcher.game.entities;

import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.gfx.ImageTile;

public class Animation {
	
	private int index;
	private double speed;
	private ImageTile sprite;
	private Image frame;
	private int spriteY;
	private int numOfFrames;
	
	private float delta;
	
	public Animation(ImageTile sprite,int spriteY, int numOfFrames, double speed) {
		this.sprite = sprite;
		this.speed = speed;
		this.numOfFrames = numOfFrames;
		this.spriteY = spriteY;
		index = 0;
		delta = 0;
		frame = sprite.getTileImage(index, this.spriteY);
	}

	public Image getFrame() {
		return frame;
	}

	public void setFrame(Image frame) {
		this.frame = frame;
	}

	public void update(float dt) {
		delta += dt;
		if(delta >= speed) {
			delta = 0;

			if(index == numOfFrames-1) {
				index = 0;
			}else index ++;
			frame = sprite.getTileImage(index, spriteY);
		}
	}
}
