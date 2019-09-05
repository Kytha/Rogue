package com.kylethatcher.engine.gui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.kylethatcher.engine.GameContainer;
import com.kylethatcher.engine.Handler;

public abstract class GuiActiveComponent extends GuiComponent{

	public GuiActiveComponent(int x, int y, int w, int h, GuiFrame parent) {
		super(x, y, w, h, parent);
		// TODO Auto-generated constructor stub
	}

	public abstract void render(Handler handler, int offX, int offY, int zDepth);
	
	public abstract void update();

	public void onMouseMove(MouseEvent e, int offX, int offY) {
		
		if(new Rectangle((x+ offX + bounds.x),(y + offY + bounds.y),bounds.width,bounds.height).contains(e.getX()/GameContainer.scale, e.getY()/GameContainer.scale)) {
			hovering = true;
		}
		else {
			hovering = false;
		}
	}
	
	public abstract void onClick(MouseEvent e);
	
	public void onMouseRelease(MouseEvent e) {
		if(hovering) {
			onClick(e);
		}
	}
	
	public abstract int getInput();
}
