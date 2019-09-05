package com.kylethatcher.game.states;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.game.assets.Assets;

public class MenuState extends State {

	public MenuState(Handler handler) {
		super(handler);
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render() {
		handler.getR().setAmbientColor(0xffDADADA);
	}

}
