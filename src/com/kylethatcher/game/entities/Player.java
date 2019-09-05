package com.kylethatcher.game.entities;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Light;
import com.kylethatcher.game.assets.Assets;

public class Player extends Creature {

	private static final int DEFUALT_PLAYER_HEALTH = 20;
	private static final int PLAYER_DEPTH = 1;
	private Light light;
	
	public Player(Handler handler, int x, int y) {
		super(handler, x, y, DEFUALT_PLAYER_HEALTH);
		// TODO Auto-generated constructor stub
		bounds = new Rectangle(3,11,10,5);
		x =  x - bounds.x;
		y = y - bounds.y;
		
		
		light = new Light(55, 0xffFFE4B5, x,y);//0xffFFE4B5
		handler.getMap().getLightManager().addLight(light);
		speed = DEFUALT_SPEED;
		
		walkDown = new Animation(Assets.player,0, 4,0.2);
		walkLeft = new Animation(Assets.player,1, 4,0.2);
		walkRight = new Animation(Assets.player,2, 4,0.2);
		walkUp = new Animation(Assets.player,3, 4,0.2);
		idle = new Animation(Assets.player,4,4,0.2);
		currentAnimation = walkDown;
		
	}

	@Override
	public void render() {
		float offX = handler.getGameCamera().getxOffset();
		float offY = handler.getGameCamera().getyOffset();
		
		handler.getR().setzDepth(PLAYER_DEPTH);
		handler.getR().drawImage(currentAnimation.getFrame(), (int)(x - offX), (int)(y - offY));
		handler.getR().drawRect((int)(x - offX + bounds.x), (int)(y - offY + bounds.y), bounds.width, bounds.height, 0xFFFF0000, false);
		//for(int i = 0; i < 4; i ++) {
			//if(tileSpace[i]!=null) handler.getR().drawRect((tileSpace[i].getX()*Tile.TILEHEIGHT - offX), (tileSpace[i].getY()*Tile.TILEWIDTH - offY), Tile.TILEWIDTH, Tile.TILEHEIGHT, 0xffffffff, false);
		//}
	}

	@Override
	public void update(float dt) {
		getInput();
		Animations(dt);
		if(xMove != 0 || yMove != 0) move();
		handler.getGameCamera().centerOnEntity(this);
		light.centerAroundEntity(this);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	private void getInput() {
		moveUp = handler.getGc().getInput().isKey(KeyEvent.VK_W);
		moveLeft = handler.getGc().getInput().isKey(KeyEvent.VK_A);
		moveRight = handler.getGc().getInput().isKey(KeyEvent.VK_D);
		moveDown = handler.getGc().getInput().isKey(KeyEvent.VK_S);
		xMove = 0;
        yMove = 0;
		if(moveUp) {
			yMove = -1;
		}
		
		if(moveDown) {
			yMove = 1;
		}
		
		if(moveLeft) {
			xMove = -1;
		}
		
		if(moveRight) {
			xMove = 1;
		}
	}

}
