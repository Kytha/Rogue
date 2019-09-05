package com.kylethatcher.game.entities;

import java.awt.Rectangle;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.game.tiles.Tile;

public abstract class Creature extends Entity {

	protected int health;
	protected int maxHealth;
	protected int xMove, yMove, xMoveCounter, yMoveCounter;
	protected int speed;
	protected static final int DEFUALT_SPEED = 2;
	//protected TileVector[] tileSpace = new TileVector[8];
	//protected TileVector t;
	
	protected boolean moveUp, moveDown, moveLeft, moveRight;
	
	protected Animation walkDown, walkUp, walkLeft, walkRight,idle;
	protected Animation currentAnimation;

	public Creature(Handler handler, int x, int y, int maxHealth) {
		super(handler, x, y);
		this.maxHealth = maxHealth;
		this.xMove = 0;
		this.yMove = 0;
		xMoveCounter = 0;
		yMoveCounter = 0;
		//for(int i = 0; i < tileSpace.length; i ++) {
			//tileSpace[i] = null;
		//}
	}
	
	protected void move() {
		
		/*for(int i = 0; i < 2; i ++) {
			tileSpace[0 + 4*i] = getTileVector(x+xMove,y+yMove,i);
			tileSpace[1 + 4*i] = getTileVector(x+bounds.width+xMove,y+yMove,i);
			tileSpace[2 + 4*i] = getTileVector(x+bounds.width+xMove, y+bounds.height+yMove,i);
			tileSpace[3 + 4*i] = getTileVector(x+xMove, y+bounds.height+yMove,i);
		}
		*/
		//if(!checkCollisionX()) {
			xMoveCounter++;
			if(xMoveCounter >= speed){
				x += xMove;
				xMoveCounter = 0;
			}
		//}
		//if(!checkCollisionY()) {
			yMoveCounter++;
			if(yMoveCounter >= speed){
				y += yMove;
				yMoveCounter = 0;
			}
		//}
		
	}
	/*
	private boolean checkCollisionX() {
		Rectangle bounds = new Rectangle(this.bounds.x + xMove + x, this.bounds.y + y, this.bounds.width, this.bounds.height);
		for(int i = 0; i < 2; i ++) {
			if(xMove > 0) {
				if(checkCollision(bounds, 1 + 4*i))return true;
				if(checkCollision(bounds, 2 + 4*i))return true;
			}
			
			if(xMove < 0) {
				if(checkCollision(bounds, 0 + 4*i))return true;
				if(checkCollision(bounds, 3 + 4*i))return true;
			}
		}
		return false;
	}
	/*
	private boolean checkCollisionY() {
		Rectangle bounds = new Rectangle(this.bounds.x+x, this.bounds.y + y+yMove, this.bounds.width, this.bounds.height);
		for(int i = 0; i < 2; i ++) {
			if(yMove > 0) {
				if(checkCollision(bounds, 3 + 4*i))return true;
				if(checkCollision(bounds, 2 + 4*i))return true;
			}
			if(yMove < 0) {
				if(checkCollision(bounds, 0 + 4*i))return true;
				if(checkCollision(bounds, 1 + 4*i))return true;
			}
		}
		return false;
	}
	/*
	private boolean checkCollision(Rectangle bounds, int tile) {
		if(tileSpace[tile].getTile() != null) {
			if(tileSpace[tile].getTile().getBounds() != null) {
				Rectangle tileBounds = tileSpace[tile].sumVector();
				if(bounds.intersects(tileBounds)) return true;
			} else return false;
		} 
		return false;
	}
	*/
	/*protected TileVector getTileVector(int offx, int offy, int layer) {
		int x = (offx + bounds.x);
		int y = (offy + bounds.y);
		return new TileVector(handler.getMap().getTile(x,y,layer),(x/Tile.TILEWIDTH),(y/Tile.TILEHEIGHT));
	}*/
	
	protected void Animations(float dt) {
		
		if(moveLeft) {
			currentAnimation = walkLeft;
		}
		else if(moveRight) {
			currentAnimation = walkRight;
		}
		else if(moveDown) {
			currentAnimation = walkDown;
		}
		else if(moveUp) {
			currentAnimation = walkUp;
		}else {
			currentAnimation = idle;
		}
		currentAnimation.update(dt);
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public float getxMove() {
		return xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setxMove(int xMove) {
		this.xMove = xMove;
	}

	public void setyMove(int yMove) {
		this.yMove = yMove;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
