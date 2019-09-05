package com.kylethatcher.engine;

//Abstract class for a game object
//All games written with this engine must be of this structure

public abstract class AbstractGame {
	
	public abstract void update(float dt);
	public abstract void render();
	public abstract void init(Handler handler);

}
