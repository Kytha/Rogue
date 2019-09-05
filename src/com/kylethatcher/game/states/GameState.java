package com.kylethatcher.game.states;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.GameCamera;
import com.kylethatcher.engine.gui.ComponentListener;
import com.kylethatcher.engine.gui.GuiActiveComponent;
import com.kylethatcher.engine.gui.GuiComponent;
import com.kylethatcher.engine.gui.GuiFrame;
import com.kylethatcher.engine.gui.GuiManager;
import com.kylethatcher.engine.gui.SliderListener;
import com.kylethatcher.game.entities.Player;
import com.kylethatcher.game.entities.Villager;
import com.kylethatcher.game.maps.Clock;
import com.kylethatcher.game.maps.Map;
import com.kylethatcher.game.tiles.Tile;
import com.kylethatcher.saver.SaveFile;

public class GameState extends State {

	private GameCamera gameCamera;
	private Clock clock;
	private Player player;
	private Map currentMap;
	
	//SUBSTATES
	private boolean paused = false;
	private boolean isDebug = false;
	
	
	//GUI FRAMES
	private GuiFrame pauseMenu;
	private GuiFrame optionsMenu;
	private GuiManager guiManager;
	private Debugger debugger;

	
	public GameState(Handler handler) {
		super(handler);
		
		debugger = new Debugger(handler);
		
		gameCamera = new GameCamera(handler, 0, 0);
		handler.setGameCamera(gameCamera);
		
		//Tile.init();
		currentMap = new Map(handler, "res/maps/map.txt");
		handler.setMap(currentMap);
		
		guiManager = handler.getGuiManager();
		generateGUI();
		
		player = new Player(handler, handler.getWidth()/2, handler.getHeight()/2);
		handler.setPlayer(player);
		
		clock = new Clock(0, handler);
		clock.setTime(1, 0);
		
	}

	@Override
	public void update(float dt) {
		
		guiManager.update();
		if(!paused) {
			clock.tick(dt);
			player.update(dt);
			currentMap.update(dt);
			if(isDebug == true) {
				debugger.update(dt);
			}
		}
		
		//INPUTS
		if(handler.getGc().getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
			if(!paused) {
				pauseMenu.setActive(true);
				paused = true;
			}
			else if(paused) {
				pauseMenu.setActive(false);
				optionsMenu.setActive(false);
				paused = false;
			}
		}
	}

	@Override
	public void render() {
		
		handler.getR().drawText("Time - " + clock.getTime(), 200, 0,0xffffffff);
		
		currentMap.render(player);
		player.render();
		guiManager.render();
		
		if(isDebug == true) {
			debugger.render();
		}
	}
	
	
	private void generateGUI() {

		pauseMenu = new GuiFrame(3,3,handler.getWidth()/2,handler.getHeight()/2,currentMap.getUpperDepth()+1,handler);
		optionsMenu = new GuiFrame(4,4,handler.getWidth()/2 - 64,handler.getHeight()/2 - 64,currentMap.getUpperDepth()+1,handler);
		
		pauseMenu.addText(0, 0, "Game Paused", 0xff000000);
		pauseMenu.addButton(0, 50, "Resume", new ComponentListener() {
			@Override
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().generateSound("/sounds/fx/click.wav");
				paused = false;
				c.getParent().setActive(false);
			}});
		pauseMenu.addButton(0, 130, "Quit", new ComponentListener() {
			@Override
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().generateSound("/sounds/fx/click.wav");
				SaveFile.SaveGame(handler.getPlayer(), handler.getMap());
				c.getParent().getHandler().getGc().stop();
			}});
		pauseMenu.addButton(0, 90, "Options", new ComponentListener() {
			@Override
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().generateSound("/sounds/fx/click.wav");
				c.getParent().setActive(false);
				optionsMenu.setActive(true);
			}});
		
		optionsMenu.addText(0, 0, "Options", 0xff000000);
		optionsMenu.addButton(44, 100, "Back", new ComponentListener() {
			@Override
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().generateSound("/sounds/fx/click.wav");
				c.getParent().setActive(false);
				pauseMenu.setActive(true);	
			}});
		optionsMenu.addSlider(10, 40, "Fx Volume",0xFFFFD700, new SliderListener() {
		
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().setAmbientVolume(((float)(c.getInput())/(float)(c.getBounds().width - 8))*46 - 40);
			}

			@Override
			public int getGameInput() {
				return (int)(handler.getJukebox().getFxVolume());
			}
		});
		
		optionsMenu.addSlider(10, 65, "Fx Volume",0xFF228B22, new SliderListener() {
			
			public void componentPressed(GuiActiveComponent c) {
				handler.getJukebox().setFxVolume(((float)(c.getInput())/(float)(c.getBounds().width - 8))*46 - 40);
			}

			@Override
			public int getGameInput() {
				return (int)(handler.getJukebox().getFxVolume());
			}
		});
		optionsMenu.addCheckBox(10, 85, "Mute", new ComponentListener(){

			@Override
			public void componentPressed(GuiActiveComponent c) {
					handler.getJukebox().setMuted(c.getInput());
		}});
		
		optionsMenu.addCheckBox(50, 85, "Debug Mode", new ComponentListener() {

			@Override
			public void componentPressed(GuiActiveComponent c) {
				if(c.getInput() == 1) {
					isDebug = true;
				}
				if(c.getInput() == 0){
					isDebug = false;
			}
		}});
	}
}
