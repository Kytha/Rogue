package com.kylethatcher.engine.gfx;

public class Font {

	public static final int UNIT_WIDTH = 5;
	public static final int HEIGHT = 6;
	public static final Font STANDARD_FONT = new Font("/textures/fonts/font.png");
	public static final int STANDARD_FONTSIZE = 5;
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	
	public Font(String path) {
		
		fontImage = new Image(path);
		offsets = new int[59];
		widths = new int[59];
		
		int unicode = 0;
		
		for(int i = 0; i < fontImage.getW(); i ++) {
			if(fontImage.getP()[i] == 0xff0000ff) {
				offsets[unicode] = i;
			}
			
			if(fontImage.getP()[i] == 0xffffff00) {
				widths[unicode] = i - offsets[unicode];
				unicode ++;
			}
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public int[] getWidths() {
		return widths;
	}
}
