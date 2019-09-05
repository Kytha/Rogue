package com.kylethatcher.engine;

import com.kylethatcher.engine.gfx.GameCamera;
import com.kylethatcher.engine.gui.GuiManager;
import com.kylethatcher.engine.jukebox.Jukebox;
import com.kylethatcher.game.entities.Player;
import com.kylethatcher.game.maps.Map;

//Handler stores and distributes essential game information 
public class Handler {

	private GameContainer gc;
	private Renderer r;
	public  Map map;
	private GameCamera gameCamera;
	private GuiManager guiManager;
	private Jukebox jukebox;
	private Player player;
	
	public Handler(GameContainer gc, Renderer r) {
		this.gc = gc;
		this.r = r;
		guiManager = null;
		player = null;
	}

	
	//GETTERS & SETTERS
	
	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public Renderer getR() {
		return r;
	}

	public void setR(Renderer r) {
		this.r = r;
	}
	
	public int getWidth() {
		return  gc.getWidth();
	}
	
	public int getHeight() {
		return  gc.getHeight();
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public void setGameCamera(GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	public Map getMap() {
		return map;
	}

	public GuiManager getGuiManager() {
		return guiManager;
	}

	public void setGuiManager(GuiManager guiManager) {
		this.guiManager = guiManager;
	}

	public Jukebox getJukebox() {
		return jukebox;
	}

	public void setJukebox(Jukebox jukebox) {
		this.jukebox = jukebox;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	 
}
