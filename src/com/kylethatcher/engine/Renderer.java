package com.kylethatcher.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.kylethatcher.engine.gfx.Font;
import com.kylethatcher.engine.gfx.Image;
import com.kylethatcher.engine.gfx.ImageRequest;
import com.kylethatcher.engine.gfx.ImageTile;
import com.kylethatcher.engine.gfx.Light;
import com.kylethatcher.engine.gfx.LightRequest;
import com.kylethatcher.engine.gfx.TextRequest;

//Handles ALL rendering for game object. Capable of layering, alpha channels, lighting, and shadowing
public class Renderer {
	
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>(); 
	private ArrayList<LightRequest> lightRequest = new ArrayList<LightRequest>(); 
	private ArrayList<TextRequest> textRequest = new ArrayList<TextRequest>(); 
	
	public int getpW() {
		return pW;
	}

	private int pW, pH;
	private int[] p;
	
	private int[] zb;
	private int zDepth = 0;
	
	private int[] lm;
	private int[] lb;
	private int ambientColor = 0xff1C1C1C;
	
	private Font font;
	private boolean proccessing = false; 

	public Renderer(GameContainer gc) {
		
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		zb = new int[p.length];
		
		lm = new int[p.length];
		lb = new int[p.length];
		
		font = Font.STANDARD_FONT;
	}
	
	public void clear() {
		for(int i = 0; i < p.length; i ++) {
			p[i] = 0;
			zb[i] = 0;
			lm[i] = ambientColor;
			lb[i] = 0;
		}
	}
	
	//Handles all request made during rendering. 
	//Lighting, text, GUIs, and Alpha channels are rendered in post processing  
	public void postProcessing() {
		proccessing = true;
		Collections.sort(imageRequest, new Comparator<ImageRequest>() {
			@Override
			public int compare(ImageRequest i0, ImageRequest i1) {
				if(i0.zDepth < i1.zDepth)
					return -1;
				if(i0.zDepth > i1.zDepth)
					return 1;
				return 0;
			}
		});
		
		for(int i = 0; i < imageRequest.size(); i ++) {
			ImageRequest ir = imageRequest.get(i);
			setzDepth(ir.zDepth);
			drawImage(ir.image,ir.offX, ir.offY);
		}
		
		for(int i = 0; i < lightRequest.size(); i ++) {
			
			LightRequest l = lightRequest.get(i);
			drawLightRequest(l.light,l.locX,l.locY);
		}
		
		for(int i = 0; i < textRequest.size(); i ++) {
			
			drawTextRequest(textRequest.get(i));
		}
		
		for(int i = 0; i < p.length; i ++) {
			float r = ((lm[i] >> 16)& 0xff) / 255f;
			float g = ((lm[i] >> 8)& 0xff) / 255f;
			float b = (lm[i]& 0xff) / 255f;
			
			p[i] = ((int)(((p[i] >> 16) & 0xff) * r) << 16 | (int)(((p[i] >> 8) & 0xff) * g) << 8 | (int)((p[i] & 0xff) * b)) ;
		}
		
		imageRequest.clear();
		lightRequest.clear();
		textRequest.clear();
		proccessing = false;
	}
	
	public void setPixel(int x, int y, int value) {
		
		int alpha = ((value >> 24) & 0xff);
		int index = x + y *pW;
		
		if((x < 0 || x>= pW || y < 0 || y >= pH) || alpha == 0){
			return;
		}
		
		if(zb[index] > zDepth)
			return;
		
		zb[index] = zDepth;
		
		if(alpha == 255){
			p[index] = value; 
		}
		else {
			int pixelColor = p[index];
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha/255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha/255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha/255f));
			p[index] = (newRed << 16 | newGreen << 8 | newBlue); 
		}
	}
	
	//Takes an image object and renders to the raster
	//*Note; Images containing an alpha channel get queued for post processing
	public void drawImage(Image image, int offX, int offY) {
		
		if(image.isAlpha() && !(proccessing)) {
			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}
		if(offX < -image.getW()) return;
		if(offY < -image.getH()) return;
		if(offY > pH) return;
		if(offX > pW) return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();
		
		if(offX < 0) {
			newX -= offX;
		}
		if(offY < 0) {
			newY -= offY;
		}
		if(newWidth + offX > pW) {
			newWidth -= newWidth + offX - pW;
		}
		if(newHeight + offY > pH) {
			newHeight -= newHeight + offY - pH;
		}
		for(int y = newY; y < newHeight; y ++) {
			for(int x = newX; x < newWidth; x ++) {
				setPixel(x + offX, y +offY, image.getP()[x + y *image.getW()]);

				if(image.isIgnoreLight() == true) {
					if(((image.getP()[x + y *image.getW()] >> 24) & 0xff) == 255) {
						setLightMap(x + offX, y + offY, 0xFFFFFFFF);
					}
				}
				if(image.getLightBlock() == 1) {
					if(((image.getP()[x + y *image.getW()] >> 24) & 0xff) == 255) {
					setLightBlock(x + offX, y + offY, Light.SOME);
					}
				}
			}
		}
	}
	
	//Renders an individual tile of a ImageTile object 
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		
		if(image.isAlpha() && !(proccessing)) {
			imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY), zDepth, offX, offY));
			return;
		}
		
		if(offX < -image.getTileW()) return;
		if(offY < -image.getTileH()) return;
		if(offY > pH) return;
		if(offX > pW) return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();
		
		if(offX < 0) {
			newX -= offX;
		}
		if(offY < 0) {
			newY -= offY;
		}
		if(newWidth + offX > pW) {
			newWidth -= newWidth + offX - pW;
		}
		if(newHeight + offY > pH) {
			newHeight -= newHeight + offY - pH;
		}
		for(int y = newY; y < newHeight; y ++) {
			for(int x = newX; x < newWidth; x ++) {
				setPixel(x + offX, y +offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
				if(image.getLightBlock() == 1) {
					setLightBlock(x + offX, y + offY, image.getLb()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
				}
			}
		}
	}
	
	//Called to queue a textRequest object
	public void drawText(String text, int offX, int offY, int color) {
		
		text = text.toUpperCase();
		textRequest.add(new TextRequest(text, offX, offY, color, 1));
	}
	
	//Called to render a textRequest object
	private void drawTextRequest(TextRequest textRequest) {
		
		Image fontImage = font.getFontImage();
		int offX = textRequest.offX;
		int offY = textRequest.offY;
		int color = textRequest.color;
		int fontSize = textRequest.size;
		String text = textRequest.text;
		
		int offset = 0;
		
		for(int i = 0; i < text.length(); i ++) {
			int unicode = text.codePointAt(i) - 32;
			
			for(int y =0; y < fontImage.getH(); y ++) {
				
				for(int x = 0; x < font.getWidths()[unicode]; x ++) {
					if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * fontImage.getW()] == 0xffffffff) {
						for(int j = 0; j < fontSize; j ++) {
							setPixel(x + offX +j+ offset, y + offY, color);
							setLightMap(offX + x + j + offset, offY + y, 0xffffff);
						}
					}
				}
			}
			
			offset += font.getWidths()[unicode];
		}
	}
	
	private void drawLightRequest(Light l, int offX, int offY) {
		for(int i = 0; i <= l.getDiameter(); i ++) {
			drawLightLine(l, l.getRadius(), l.getRadius(), i, 0, offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), i, l.getDiameter(), offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), 0, i, offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), l.getDiameter(), i, offX, offY);
		}
	}
		
	private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int err2;
		boolean hitObject = false;
		while(true) {
			
			int screenX = x0 - l.getRadius() + offX;
			int screenY = y0 - l.getRadius() + offY;
			int lightColor;
			
			if(screenX < 0 || screenX >= pW || screenY < 0 || screenY >= pH) {
				return;
			}
			if(hitObject == false) {
				lightColor = l.getLightValue(x0,  y0);
			}
			else {
				lightColor = l.getDimLightValue(x0, y0);;
			}
			if(lightColor == 0) {
				return;
			}
			
			if(lb[screenX + screenY * pW] == Light.FULL) {
				return;
			}
			
			if(lb[screenX + screenY * pW] == Light.SOME) {
				lightColor = l.getDimLightValue(x0, y0);
				hitObject = true;
			}
			
			setLightMap(screenX, screenY, lightColor);
			
			if(x0 == x1 && y0 == y1)
				break;
			err2 = 2 * err;
			
			if(err2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}
			
			if(err2 < dx) {
				err += dx;
				y0 += sy;
			}	
		}
	}
	
	public void drawStatic(Image image,int zDepth, int offX, int offY) {
		image.setIgnoreLight(true);
		imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color, boolean fill) {

		if(offX < -width) return;
		if(offY < -height) return;
		if(offY > pH) return;
		if(offX > pW) return;
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
		
		if(offX < 0) {
			newX -= offX;
		}
		if(offY < 0) {
			newY -= offY;
		}
		if(newWidth + offX > pW) {
			newWidth -= newWidth + offX - pW;
		}
		if(newHeight + offY > pH) {
			newHeight -= newHeight + offY - pH;
		}
		
		if(fill == false) {
			for(int y = newY; y < newHeight; y ++) {
				setPixel(offX, y + offY, color);
				setPixel(offX + width-1, y + offY, color);
			}
			for(int x = newX; x < newWidth; x ++) {
				setPixel(offX + x,offY, color);
				setPixel(offX + x,offY + height -1, color);
			}
		}
		if(fill == true) {
			for(int y = newY; y < newHeight; y ++) {
				for(int x = newX; x < newWidth; x ++) {
					setPixel(offX + x, y + offY, color);
				}
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setLightMap(int x, int y, int value) {
		if(x < 0 || x >= pW || y < 0 || y >= pH)
			return;
		
		int baseColor = lm[x+y*pW];
		
		int maxRed = Math.max(((baseColor >> 16) & 0xff),((value >> 16) & 0xff));
		int maxGreen = Math.max(((baseColor >> 8) & 0xff),((value >> 8) & 0xff));;
		int maxBlue = Math.max(((baseColor) & 0xff),((value) & 0xff));;
		
		lm[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue); 

	}
	
	public void setLightBlock(int x, int y, int value) {
		if(x < 0 || x >= pW || y < 0 || y >= pH)
			return;
		
		if(zb[x + y * pW] > zDepth)
			return;
		
		lb[x + y * pW] = value; 

	}
	
	public void drawLight(Light l, int offX, int offY) {
		lightRequest.add(new LightRequest(l, offX, offY));
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}

	public int getAmbientColor() {
		return ambientColor;
	}

	public int[] getLb() {
		return lb;
	}

	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}
}
