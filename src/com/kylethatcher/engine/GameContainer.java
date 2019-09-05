package com.kylethatcher.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

//Class to wrap the game object
//Highest level of control; regulates update and render speeds
//*Note: Game runs on a separate thread to throttle update speed 

public class GameContainer implements Runnable{

		
		private Thread thread;
		private AbstractGame game;
		private Window window;
		private Renderer renderer;
		private Input input;
		
		private boolean running;
		private boolean render;
		
		private Handler handler;
		
		
		private final double UPDATE_CAP = 1.0/60.0;
		private int width = 512,height = 384;
		public static float scale = 2f;
		private String title = "Rogue - Alpha v14.3";
		
		
		
		public GameContainer(AbstractGame game) {
			this.game = game;
		}
		
		public void start() {
			//Powering On
			window = new Window(this);
			renderer = new Renderer(this);
			handler = new Handler(this, renderer);
			input = new Input(this, handler);
			game.init(handler);

			//Launching Game
			thread = new Thread(this);
			thread.run();
		}
		
		//Method executed by the thread
		public void run() {
			running = true;
			
			double firstTime = 0;
			double lastTime = System.nanoTime()/ 1000000000.0;
			double passedTime = 0;
			double unprocessedTime = 0;
			
			double frameTime = 0;
			int frames = 0;
			int fps = 0;
			
			
			//Main game loop
			while(running) {
				render = false;
				firstTime = System.nanoTime()/ 1000000000.0;
				passedTime = firstTime - lastTime;
				lastTime = firstTime;
				
				unprocessedTime += passedTime;
				frameTime += passedTime;
				
				while(unprocessedTime >= UPDATE_CAP) {
					unprocessedTime -= UPDATE_CAP;
					render = true;
					
					//Update game
					game.update((float)UPDATE_CAP);
					input.update();
					if(frameTime  >= 1.0) {
						frameTime = 0;
						fps = frames;
						frames = 0;
					}
				}
				
				if(render) {
					//Render Game
					renderer.clear();
					game.render();
					renderer.postProcessing();
					renderer.drawText("FPS: " + fps, 0, 0,0xffffffff);
					window.update();
					frames ++;
				}
				else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
			}
			
			dispose();
		}
		
		//Disposes of game & exits
		public synchronized void stop(){
	        
	        //if the game has already stopped return
	        if(running == false)
	            return;
	        //else set running to false
	        running = false;
	        System.exit(0);
	        //try to end the thread. Also catches and handles any errors thrown during run-time
	        try {
	            thread.join();

	        } catch (InterruptedException ex) {
	            Logger.getLogger(GameContainer.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
		
		
		//GETTERS & SETTERS
		
		public void dispose() {
			
			
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public float getScale() {
			return scale;
		}

		public String getTitle() {
			return title;
		}

		public Window getWindow() {
			return window;
		}

		public Input getInput() {
			return input;
		}
}
