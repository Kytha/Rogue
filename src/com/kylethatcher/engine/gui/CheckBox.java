package com.kylethatcher.engine.gui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.Font;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.utils.Utils;
import com.kylethatcher.game.assets.Assets;


public class CheckBox extends GuiActiveComponent {

	private int state;
	private Image checkBoxTexture;
	private Image checkMarkTexture;
	private String name;
	private ComponentListener listener;
	
	public CheckBox(int x, int y,String name,ComponentListener listener, GuiFrame parent) {
		super(x, y, 0, 0, parent);
		this.name = name;
		this.listener = listener;
		checkBoxTexture = Assets.checkBox;
		checkMarkTexture = Assets.checkMark;
		bounds = new Rectangle(Utils.generateBounds(checkBoxTexture));
		this.x -= bounds.x;
		this.y -= bounds.y;
		this.h = bounds.height;
		this.w = bounds.width;

		state = 0;
	}

	@Override
	public void render(Handler handler, int offX, int offY, int zDepth) {
		handler.getR().drawStatic(checkBoxTexture, zDepth, offX + x, offY + y);
		handler.getR().drawText(name, offX + x + bounds.width + bounds.x  + Font.STANDARD_FONTSIZE, offY + y + bounds.y, 0xFF000000);
		if(state == 1) {
			handler.getR().drawStatic(checkMarkTexture, zDepth+1, offX + x, offY + y);
		}
	}

	public void update() {
	
	}

	@Override
	public void onClick(MouseEvent e) {
		if(state == 0) {
			state = 1;
		}else {
			state = 0;
		}
		listener.componentPressed(this);
	}

	public int getInput() {

		return state;
	}
}
