package com.kylethatcher.game.entities;

import java.awt.Rectangle;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.game.assets.Assets;

public class Villager extends Creature{
	
	private String name;
	public Villager(Handler handler, int x, int y, int maxHealth, String name) {
		super(handler, x,y,maxHealth);
		this.name = name;
		
		bounds = new Rectangle(3,11,10,5);
		x =  x - bounds.x;
		y = y - bounds.y;
		
		walkDown = new Animation(Assets.player,0, 4,0.3);
		walkLeft = new Animation(Assets.player,1, 4,0.3);
		walkRight = new Animation(Assets.player,2, 4,0.3);
		walkUp = new Animation(Assets.player,3, 4,0.3);
		idle = new Animation(Assets.player,4,4,0.3);
		currentAnimation = walkDown;
	}

	@Override
	public void render() {
		float offX = handler.getGameCamera().getxOffset();
		float offY = handler.getGameCamera().getyOffset();
		handler.getR().setzDepth(1);
		handler.getR().drawImage(currentAnimation.getFrame(),(int)(x - offX),(int)(y - offY));
	}

	@Override
	public void update(float dt) {
		Animations(dt);
		if(xMove != 0 || yMove != 0) move();
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
