package com.kylethatcher.game.maps;

import com.kylethatcher.game.entities.EntityManager;
import com.kylethatcher.game.entities.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.gfx.LightManager;
import com.kylethatcher.engine.jukebox.SoundClip;
import com.kylethatcher.engine.utils.Utils;
import com.kylethatcher.game.tiles.Tile;


public class Map {
	
	private Handler handler;
	
	public int width, height;
	
    private int spawnX;
    private int spawnY;
    private int upperDepth;
    private ArrayList<int[][]> layerList;
    
    private int[][] baseTiles;
    private int[][] layeredTiles;
    
    private int ambientColor;
    private boolean outside;
    private SoundClip ambientMusic;
    private EntityManager entityManager;
    private LightManager lightManager;
    
	public Map(Handler handler, String path) {
		loadWorld(path); 
		upperDepth = layerList.size() - 1;
		this.handler = handler;
		handler.getR().setAmbientColor(ambientColor);
		entityManager = new EntityManager();
		lightManager = new LightManager();
		handler.getJukebox().setAmbient(ambientMusic);
		this.ambientColor = ambientColor;
	}
	
	public void render(Player player) {
		float offX = handler.getGameCamera().getxOffset();
		float offY = handler.getGameCamera().getyOffset();
		handler.getR().setzDepth(0);
		Iterator<int[][]> it = layerList.iterator();
		while(it.hasNext()) {
			int[][] layer = it.next();
			for(int j = 0; j < height; j ++) {
				for(int i = 0; i < width; i ++) {
					if(layer[i][j] != 0 && Tile.tiles[layer[i][j]] != null)
					Tile.tiles[layer[i][j]].render(handler,(int)(Tile.TILEWIDTH * i - offX), (int)(Tile.TILEHEIGHT * j - offY));

				}
			}
			handler.getR().setzDepth(handler.getR().getzDepth() + 1);
		}

		handler.getR().setzDepth(1);	
		entityManager.render();
		lightManager.render(handler);
		
	}
	
	public LightManager getLightManager() {
		return lightManager;
	}

	public void update(float dt) {
		entityManager.update(dt);
		lightManager.update(dt);
	}
	
	private void loadWorld(String path) {
			layerList = new ArrayList<int[][]>();
			try {
				File inputFile = new File(path);

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile);
				doc.getDocumentElement().normalize();
				ambientColor = Utils.parseHexInt(doc.getDocumentElement().getAttribute("light"));
				ambientMusic = new SoundClip("/sounds/music/"+doc.getDocumentElement().getAttribute("sound").trim()+".wav");

				NodeList nList = doc.getElementsByTagName("layer");
				outside = true;
				for(int temp = 0; temp < nList.getLength(); temp ++) {
					Node nNode = nList.item(temp);
					
					
					if(nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						width = Utils.parseInt(eElement.getAttribute("width"));
						height = Utils.parseInt(eElement.getAttribute("height"));
						int[][] layer = new int[width][height];
						String[] tokens = eElement.getElementsByTagName("data").item(0).getTextContent().split(",");
						for(int y = 0; y < height; y ++) {
					    	for(int x = 0; x < width; x ++){
					            layer[x][y] = Utils.parseInt(tokens[(x + y * width)]);
					        }
					    } 
						layerList.add(layer);
					}
				}
				
				
			} catch(IOException | ParserConfigurationException | SAXException e) {
				e.printStackTrace();
		}
	}
	
	public boolean isOutside() {
		return outside;
	}
	
	public Tile getTile(int x, int y, int layer) {
		if(x < 0 || y < 0 || x/Tile.TILEWIDTH >= width || y/Tile.TILEHEIGHT >= height) return null;
		if(layer == 0) return Tile.tiles[(baseTiles[(x/Tile.TILEWIDTH)][(y/Tile.TILEHEIGHT)])];
		if(layer == 1) return Tile.tiles[(layeredTiles[(x/Tile.TILEWIDTH)][(y/Tile.TILEHEIGHT)])];
		else return null;
	}
	
	public int getAmbientColor() {
		return ambientColor;
	}
	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int getUpperDepth() {
		return upperDepth;
	}

}
