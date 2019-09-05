package com.kylethatcher.engine.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.game.assets.Assets;
import com.kylethatcher.game.tiles.Tile;



public class GuiFrame {

	private int width;
	private int height;
	private int x;
	private int y;
	private int zDepth; 
	private int centerX;
	private int centerY;
	
	public static final int CENTER = -1;
	
	private Handler handler;
	private boolean active = true;
	private static Image texture = Assets.guiFrame;
	
	private ArrayList<GuiComponent> components;
	
	public GuiFrame(int width, int height, int x, int y, int zDepth,Handler handler){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.handler = handler;
		this.zDepth = zDepth;
		
		components = new ArrayList<GuiComponent>();
	
	}
	
	
	public void render() {
		int currentDepth = handler.getR().getzDepth();
		handler.getR().setzDepth(zDepth);
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				handler.getR().drawStatic(texture, 3, x - texture.getW()/2, y - texture.getH()/2);
			}
		}
		for(GuiComponent c: components) {
			c.render(handler, x, y,zDepth+1);
		 }
		handler.getR().setzDepth(currentDepth);
	}
	
	public void update() {
		for(GuiComponent c: components) {
			if(c instanceof GuiActiveComponent)
			((GuiActiveComponent) c).update();
		 }
	}
	
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public void addText(int x, int y, String text, int color) {
		if (x == GuiFrame.CENTER) {
			Text t = new Text(texture.getW()/2,y - texture.getH()/2,text,color,this);
			t.setX(t.getX()-t.getLength()/2);
		}
		components.add(new Text(x - texture.getW()/2,y - texture.getH()/2,text,color,this));
		
	}
	
	public void addButton(int x, int y, String name, ComponentListener listener) {
		
		Button button = new Button(x - texture.getW()/2, y - texture.getH()/2, name, Assets.button, listener, this);
		components.add(button);
	}
	
	public void addSlider(int x, int y, String name,int color, SliderListener listener) {
		Slider slider = new Slider(x - texture.getW()/2,y - texture.getH()/2,name,Assets.slider,color, listener, this);
		components.add(slider);
	}
	
	public void addCheckBox(int x, int y, String name, ComponentListener listener) {
		CheckBox checkBox = new CheckBox(x,y,name, listener, this);
		components.add(checkBox);
	}
	
	
	public void onMouseMove(MouseEvent e) {
		for(GuiComponent c: components) {
			if(c instanceof GuiActiveComponent)
			((GuiActiveComponent) c).onMouseMove(e,x,y);
		 }
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(GuiComponent c: components) {
			if(c instanceof GuiActiveComponent)
			((GuiActiveComponent) c).onMouseRelease(e);
		 }
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
		if(active == true) {
			handler.getGuiManager().addGui(this);
		}
	}


	public Handler getHandler() {
		return handler;
	}
	
	
}
