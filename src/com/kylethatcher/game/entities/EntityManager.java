package com.kylethatcher.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityManager {
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	public EntityManager() {
		
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void update(float dt) {
		Iterator<Entity> it = entities.iterator();
        while(it.hasNext()){
            Entity e = it.next();
            e.update(dt);

        }
	}
	
	public void render() {
		for(Entity e: entities) {
			 e.render();
		 }
	}

}
