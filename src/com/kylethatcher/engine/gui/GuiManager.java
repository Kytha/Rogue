package com.kylethatcher.engine.gui;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;


public class GuiManager {
	
		
		private ArrayList<GuiFrame> guies;
		
		private ArrayList<GuiFrame> guiQueue;
		
		public GuiManager() {
			guies = new ArrayList<GuiFrame>();
			guiQueue = new ArrayList<GuiFrame>();
		}
		
		//Called to add an entity to be rendered and updated
		public void addGui(GuiFrame g) {
			guiQueue.add(g);
		}
		
		//Renders all entities
		public void render() {
			for(GuiFrame g: guies) {
				 g.render();
			 }
		}
		
		//Updates the fields of all entities
		public void update() {
			Iterator<GuiFrame> it = guies.iterator();
	        while(it.hasNext()){
	            GuiFrame g = it.next();
	            g.update();
	            if(!g.isActive()){
	                it.remove();
	            }

	        }
	        for(GuiFrame g: guiQueue){
	            guies.add(g);
	  
	        }
	        guiQueue.clear();
		}
		
		//Called to remove an entity from the tick and render queue 
		
		public void onMouseMove(MouseEvent e) { 
			Iterator<GuiFrame> it = guies.iterator();
	        while(it.hasNext()){
	            GuiFrame g = it.next();
	            g.onMouseMove(e);
	        }
		}
		
		public void onMouseRelease(MouseEvent e) {
			Iterator<GuiFrame> it = guies.iterator();
	        while(it.hasNext()){
	            GuiFrame g = it.next();
	            g.onMouseRelease(e);
	        }
		}
	}
