package com.kylethatcher.game.entities;

public class Tracer {
	
	private double theta;
	private int offY;
	private int offX;
	private Path path;
	
	public Tracer(double shift, Path path) {
		this.path = path;
		theta = shift;
	}
	
	public void update(float dt) {
		theta+= dt;
		if(theta > 2*Math.PI) {
			theta = 0;
		}
		offX = path.calcX(theta);
		offY = path.calcY(theta);
	}

	public int getOffY() {
		return offY;
	}

	public int getOffX() {
		return offX;
	}
}
