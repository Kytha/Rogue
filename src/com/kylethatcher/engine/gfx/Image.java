package com.kylethatcher.engine.gfx;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {

	private int w,h;
	private int[] p;
	private boolean alpha = false;
	
	private int lightBlock = 0;
	private boolean ignoreLight;
	private int [] lb;
	
	//Constructor to create image from input file
	public Image(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (Exception e) {
			System.out.print("I/O Error: File at path - " + path + " not found ");
			e.printStackTrace();
		}
		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		lb = new int[p.length];
		ignoreLight = false;
		
		for(int i = 0; i < lb.length; i ++) {
			if(((p[i] >> 24) & 0xff) == 0){
				lb[i] = 0;
			}
			else {
				lb[i] = 2;
			}
		}
		image.flush();
	}
	
	//Overloaded constructor to create images from a pixel raster
	
	public Image(int[] p, int w,int h) {
		this.p = p;
		this.w = w;
		this.h = h;
		this.lb = new int[p.length];
		
		ignoreLight = false;
		for(int i = 0; i < lb.length; i ++) {
			if(((p[i] >> 24) & 0xff) == 0){
				lb[i] = 0;
			}
			else {
				lb[i] = 2;
			}
		}
	}

	//GETTERS AND SETTERS
	
	public int getW() {
		return w;
	}
	
	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}

	public boolean isAlpha() {
		return alpha;
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}


	public int getLightBlock() {
		return lightBlock;
	}


	public void setLightBlock(int lightBlock) {
		this.lightBlock = lightBlock;
	}


	public int[] getLb() {
		return lb;
	}


	public boolean isIgnoreLight() {
		return ignoreLight;
	}


	public void setIgnoreLight(boolean ignoreLight) {
		this.ignoreLight = ignoreLight;
	}
	
}
