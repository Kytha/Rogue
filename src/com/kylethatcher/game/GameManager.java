package com.kylethatcher.game;

import com.kylethatcher.engine.AbstractGame;
import com.kylethatcher.engine.GameContainer;
import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.Input;
import com.kylethatcher.engine.gui.GuiManager;
import com.kylethatcher.engine.jukebox.Jukebox;
import com.kylethatcher.game.assets.Assets;
import com.kylethatcher.game.states.GameState;
import com.kylethatcher.game.states.MenuState;
import com.kylethatcher.game.states.State;


public class GameManager extends AbstractGame{
	
	private GameState gameState;
	private GuiManager guiManager;
	private Jukebox jukebox;
	
	public GameManager() {
	}
	@Override
	public void update(float dt) {
		State.getState().update(dt);
		
	}
	public void reset() {
		
	}

	@Override
	public void render() {
		State.getState().render();
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
	
	public void init(Handler handler) {
		Assets.init();
		guiManager = new GuiManager();
		jukebox = new Jukebox();
		handler.setJukebox(jukebox);
		handler.setGuiManager(guiManager);
		gameState = new GameState(handler);
		State.setState(gameState);
	}

}
