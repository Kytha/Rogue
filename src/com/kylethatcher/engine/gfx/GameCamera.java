/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylethatcher.engine.gfx;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.game.entities.Creature;

/**
 *
 * @author Kyle
 */
public class GameCamera {
    
    private float yOffset;
    private float xOffset;
    private Handler handler;
    
    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.yOffset = yOffset;
        this.xOffset = xOffset;
    }
    
    public void followMouse(){
    	
    	int mouseX = handler.getGc().getInput().getMouseX();
    	int mouseY = handler.getGc().getInput().getMouseY();
    	
    	if(mouseX - 100 > handler.getWidth()/2) {
    		this.move(1, 0);
    	}
    	if(mouseX + 100  < handler.getWidth()/2) {
    		this.move(-1, 0);
    	}
    	if(mouseY - 100 > handler.getHeight()/2) {
    		this.move(0, 1);
    	}
    	if(mouseY + 100 < handler.getHeight()/2) {
    		this.move(0, -1);
    	}
    }
    
    public void centerOnEntity(Creature e) {
    	
    	 if(e.getxMove() != 0 && e.getX() > (handler.getWidth()/ 2) + 30 + xOffset || e.getX() < (handler.getWidth()/ 2) - 30 + xOffset) {
             
                xOffset += e.getxMove();

         }
    	 
    	 if(e.getyMove() != 0 && e.getY() > (handler.getHeight()/ 2) + 30 + yOffset || e.getY() < (handler.getHeight()/ 2) - 30 + yOffset) {
             
             	yOffset += e.getyMove();

      }
        
    }
    
    //Getters and Setters
    public float getxOffset(){
        return xOffset;
    }
    
    public float getyOffset(){
        return yOffset;
    }
    
    public void setxOffset(int xOffset){
        
        this.xOffset = xOffset;
        
    }
    public void setyOffset(int yOffset){
        this.yOffset = yOffset;
        
    }
    
    public void move(float xAmt, float yAmt){
        this.yOffset += yAmt;
        this.xOffset += xAmt;
    }
    
}
