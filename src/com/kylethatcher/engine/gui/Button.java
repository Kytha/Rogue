package com.kylethatcher.engine.gui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Font;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.utils.Utils;

public class Button extends GuiActiveComponent{

	private String name;
	private int nameLength;
	private Image texture;
	private ComponentListener listener;

	
	public Button(int x, int y, String name, Image texture, ComponentListener listener, GuiFrame guiFrame) {
		super(x, y, 0, 0, guiFrame);
		this.name = name;
		this.texture = texture;
		this.listener = listener;
		nameLength = name.length() * Font.STANDARD_FONTSIZE;
		bounds = new Rectangle(Utils.generateBounds(texture));
		this.x -= bounds.x;
		this.y -= bounds.y;
		this.h = bounds.height;
		this.w = bounds.width;
	}

	@Override
	public void update() {

	}

	@Override
	public void onClick(MouseEvent e) {
		listener.componentPressed(this);
	}

	@Override
	public void render(Handler handler, int offX, int offY, int zDepth) {
		handler.getR().drawStatic(texture,zDepth, offX + x, offY + y);
		handler.getR().drawText(name, offX + x + bounds.x + bounds.width/2 - nameLength/2 + 1, offY + y + bounds.y + bounds.height/2 - 4, 0xff000000);
		//handler.getR().drawRect(offX + x + bounds.y, offY + y + bounds.y, w, h, 0xffffffff, false);
	}

	@Override
	public int getInput() {
		// TODO Auto-generated method stub
		return 0;
	}

}
