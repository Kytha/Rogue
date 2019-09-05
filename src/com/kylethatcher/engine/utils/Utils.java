package com.kylethatcher.engine.utils;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.game.tiles.Tile;

public class Utils {
	public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();
        
        try{
            
            //Creates a FileReader wrapped in a BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine())!= null){
                //Adds the information within the file to builder
                builder.append(line + "\n");
              
        }
            //closes the BufferedReader object
            br.close();
        }catch(IOException e){
        	System.out.print("I/O Error: Failed to read file at path - " + path + " ");
            e.printStackTrace();
        }
        //Converts StringBuilder object to String;
        return builder.toString();
    }
	
	public static void saveStringAsFile(String path, String string) {
		try{
            
            //Creates a FileReader wrapped in a BufferedReader
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(string);
            //closes the BufferedReader object
            bw.close();
        }catch(IOException e){
        	System.out.print("I/O Error: Failed to read file at path - " + path + " ");
            e.printStackTrace();
        }
	}
	
	public static int parseInt(String number) {
        try{
        	number = number.replace("\n", "").replaceAll("\r", "").trim();
            //Attempts to convert String to Integer
            return Integer.parseInt(number);
            
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
	
	public static int parseHexInt(String number) {
        try{
        	number = number.replace("\n", "").replaceAll("\r", "").trim();
            //Attempts to convert String to Integer
            return Integer.parseUnsignedInt(number,16);
            
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
	
	public static Image reflectImage(Image i, int mode) {
		int[] newP = new int[i.getP().length];
		
		if(mode == Tile.HORIZONTAL) {
			int indexY = 0;
			for(int y = i.getH() - 1; y >= 0; y -- ) {
				for(int x = i.getW() - 1; x >= 0; x -- ) {
					newP[x + y * i.getW()] = i.getP()[x + indexY * i.getW()];
				}
				indexY ++;
			}
		}
		
		if(mode == Tile.VERTICAL) {
			
			for(int y = 0; y < i.getH(); y ++ ) {
				int indexX = 0;
				for(int x = i.getW() - 1; x >= 0; x -- ) {
					newP[x + y * i.getW()] = i.getP()[indexX + y * i.getW()];
					indexX++;
				}
			}
		}
		
		if(mode == Tile.HORVER) {
			int indexY = 0;
			
			for(int y = i.getH() - 1; y >= 0; y -- ) {
				int indexX = 0;
				for(int x = i.getW() -1; x >= 0; x -- ) {
					newP[x + y * i.getW()] = i.getP()[indexX + indexY * i.getW()];
					indexX++;
				}
				indexY ++;
				
			}
		}
		
		return new Image(newP, i.getW(), i.getH());
		
	}
	
	public static int changeBrightness(int color, double power) {
		return ((int)(((color >> 16) & 0xff) * power) << 16 | (int)(((color >> 8) & 0xff) * power) << 8 | (int)((color & 0xff) * power));
	}
	
	public static Image scaleImage(Image image, int scale) {
		int newW = image.getW() * scale;
		int newH = image.getH() * scale;
		
		int[] newP = new int[newH * newW];
		
		int counterY = 0;
		int indexY = 0;
		for(int y = 0; y < newH; y ++) {
			int counterX = 0;
			int indexX = 0;
			for(int x = 0; x < newW; x ++) {
				newP[y * newW + x] = image.getP()[indexY * image.getW() + indexX];
				counterX ++;
				if(counterX >= scale) {
					indexX ++;
				}
			}
			counterY ++;
			if(counterY >= scale) {
				indexY ++;
			}
		}
		return new Image(newP, newW, newH);
	}
	
	public static Rectangle generateBounds(Image image) {

		int x = 0;
		int y= 0;
		
		boolean firstHit = true;
		
		int x2 = image.getW();
		int y2 = image.getH();
		
		for(int i = 0; i < image.getH(); i ++) {
			for(int j = 0; j < image.getW(); j ++){
				if(((image.getP()[j + i * image.getW()] >> 24) & 0xff) == 255) {
					if(firstHit) {
						y = i;
						x = j;
						firstHit = false;
					}
					if(j < x) {
						x = j;
					}
				}
			}
		}
		firstHit = true;
		for(int i = image.getH() - 1; i >= 0; i --) {
			for(int j = image.getW() - 1; j >= 0; j --){
				if(((image.getP()[j + i * image.getW()] >> 24) & 0xff) == 255) {
					if(firstHit) {
						y2 =  (i + 1);
						x2 = (j+1);
						firstHit = false;
					}
					if(j+1 > x2) {
							x2 = j+1;
					}
				}
			}
		}

		return new Rectangle(x, y, x2 - x, y2 - y);
	}

}
