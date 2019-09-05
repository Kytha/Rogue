package com.kylethatcher.game.assets;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.gfx.ImageTile;
import com.kylethatcher.engine.jukebox.SoundClip;
import com.kylethatcher.engine.utils.Utils;
import com.kylethatcher.game.tiles.Tile;

public class Assets {
	
	public static ImageTile gui, forest_tileset, player;
	
	public static Image checkBox, checkMark, playerUp, button, slider,
						playerDown, playerLeft, playerRight,guiFrame;
						
	public static Image[] guiTiles,forest_assets;
	
	public static final String[] tileMetaData = {"forest.tsx"};
	
	public static void init() {
		
		importTilesets(tileMetaData[0]);
		gui = new ImageTile("/textures/gui/gui.png", 32, 32);
		player = new ImageTile("/textures/entities/player.png",16,16);

		guiFrame = gui.getTileImage(0, 4, 6, 6);

		button = gui.getTileImage(6, 0, 4, 1);
		slider = gui.getTileImage(2,2,2,1);
		checkBox = gui.getTileImage(2, 3);
		checkMark = gui.getTileImage(3, 3);
		
	}
	
	static private Node getFirstChildElement(Node n) {
		NodeList nodes = n.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i ++) {
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				return node;
			}
		}
		return null;
	}
	
	static private void importTilesets(String path) {
		try {
			File inputFile = new File("res/textures/tiles/" + path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Node n = doc.getFirstChild().getFirstChild().getNextSibling();
			
			String source = "/textures/tiles/"+((Element) n).getAttribute("source").trim();
			ImageTile tileset = new ImageTile(source, 32, 32);
			int width = tileset.getW()/Tile.TILEWIDTH;
			
			NodeList nList = doc.getElementsByTagName("tile");
			
			for(int temp = 0; temp < nList.getLength(); temp ++) {
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int id = Utils.parseInt(eElement.getAttribute("id"));
					Image texture = tileset.getTileImage(id%width, id/width);
					id++;
					if(nNode.hasChildNodes()) {
						ArrayList<Rectangle> objectList = new ArrayList<Rectangle>();
						Node objectGroup = getFirstChildElement(nNode);
						NodeList objects = objectGroup.getChildNodes();
						for(int i = 0; i < objects.getLength(); i ++) {
							Node node = objects.item(i);
							if(node.getNodeType() == Node.ELEMENT_NODE) {
								Element object = (Element)objects.item(i);
								int x = Utils.parseInt(object.getAttribute("width"));
								int y = Utils.parseInt(object.getAttribute("height"));
								int offX = Utils.parseInt(object.getAttribute("x"));
								int offY = Utils.parseInt(object.getAttribute("y"));
								objectList.add(new Rectangle(offX,offY,x,y));
							}
						}
						new Tile(id, texture, objectList);
						
					}
					else {
						Tile tile = new Tile(id, texture);
					}
				}
			}	
		} catch(IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

}
