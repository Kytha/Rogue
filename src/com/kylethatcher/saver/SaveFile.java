package com.kylethatcher.saver;


import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.kylethatcher.engine.utils.Utils;
import com.kylethatcher.game.entities.Player;
import com.kylethatcher.game.maps.Map;

public class SaveFile {

	private PlayerSave player;
	private Map map;
	
	public SaveFile(Player player,Map map) {
		this.player = new PlayerSave(player.getX(), player.getY(), player.getHealth()); 
	}
	
	public static void SaveGame(Player player, Map map) {
		SaveFile saveFile = new SaveFile(player, map);
		String output = JsonWriter.objectToJson(saveFile.getPlayer());
		Utils.saveStringAsFile("res/saves/slot1/player.txt", output);
		PlayerSave saveFileTest= (PlayerSave) JsonReader.jsonToJava(output);
		System.out.print(saveFileTest.getX());
	}

	public PlayerSave getPlayer() {
		return player;
	}

	public Map getMap() {
		return map;
	}
}
