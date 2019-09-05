package com.kylethatcher.engine.gui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.kylethatcher.engine.GameContainer;
import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Font;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.utils.Utils;




public class Slider extends GuiActiveComponent{

	private int pointer;
	private String name;
	private int nameLength;
	private Image texture;
	private ComponentListener listener;
	private int pointerWidth = 2;
	private int color;
	
	public Slider(int x, int y,String name,Image texture,int color, SliderListener listener,GuiFrame parent) {
		super(x, y, 0, 0, parent);
		this.name = name;
		this.texture = texture;
		this.listener = listener;
		this.color = color;
		nameLength = name.length() * Font.STANDARD_FONTSIZE;
		bounds = new Rectangle(Utils.generateBounds(texture));
		pointer = (int)(listener.getGameInput() + 40)*(bounds.width - 8)/46;
		this.x -= bounds.x;
		this.y -= bounds.y;
		this.h = bounds.height;
		this.w = bounds.width;
	}

	@Override
	public void render(Handler handler, int offX, int offY, int zDepth) {
		handler.getR().drawStatic(texture, zDepth, offX + x, offY +y);
		handler.getR().drawText(name, offX + x + bounds.x + bounds.width/2 - nameLength/2, offY + y + bounds.y + bounds.height/2 - 13, 0xff000000);
		handler.getR().drawRect(offX + x + bounds.x + pointer + 4, offY + y + bounds.y + 2, pointerWidth, 4, color, true);
		
	}

	@Override
	public void update() {
		if (pointer < 0) {
			pointer = 0;
		}
		if(pointer > bounds.width - 8 - pointerWidth) {
			pointer = bounds.width - pointerWidth - 8;
		}
	}

	public int getPointer() {
		return pointer;
	}

	@Override
	public void onClick(MouseEvent e) {

		pointer = (int)(e.getX()/GameContainer.scale) - parent.getX() - x - 4;
		if (pointer < 0) {
			pointer = 0;
		}
		if(pointer > bounds.width - 8 - pointerWidth) {
			pointer = bounds.width - pointerWidth - 8;
		}
		listener.componentPressed(this);
	}

	public int getInput() {
		return pointer;
	}

}
