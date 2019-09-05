package com.kylethatcher.engine.gfx;

import java.util.ArrayList;
import java.util.Iterator;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.Renderer;
import com.kylethatcher.game.entities.Entity;

public class LightManager {
	ArrayList<Light> lights = new ArrayList<Light>();
	public LightManager() {
		
	}
	
	public void addLight(Light e) {
		lights.add(e);
	}
	
	public void update(float dt) {
		Iterator<Light> it = lights.iterator();
        while(it.hasNext()){
            Light l = it.next();
            l.update(dt);

        }
	}
	
	public void render(Handler handler) {
		for(Light l: lights) {
			 l.render(handler);
		 }
	}
}
