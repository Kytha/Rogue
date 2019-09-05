package com.kylethatcher.game.maps;

import com.kylethatcher.engine.Handler;
import com.kylethatcher.engine.utils.Utils;

public class Clock {
	
	private int time;
	private float timePast;
	private int hour;
	private int lastHour;
	private Handler handler;
	private static double[] dayLights = {1, 1.4, 1.8, 2.4, 3.0, 3.6, 4.4, 5.4, 6.4, 7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 6.4, 5.6, 4.8, 4, 3.2, 2.0, 1.0};
	
	public Clock(int time, Handler handler) {
		this.time = time;
		this.handler = handler;
		hour = 0;
		lastHour = 0;
	}
	
	public void tick(float dt) {
		timePast += dt;
		if(timePast >= 1) {
			time += 10;
			timePast = 0;
		}
		if(time > 1440) {
			time = 0;
		}
		hour = Math.floorDiv(time, 60);
		if(hour == 24) {
			hour =0;
		}
		if(handler.getMap().isOutside()) {
			if(hour > lastHour) {
					handler.getR().setAmbientColor(Utils.changeBrightness(handler.getMap().getAmbientColor(), dayLights[hour]));
			}
		}
		lastHour = hour;
	}

	public String getTime() {


		String minutes = Integer.toString(time % 60);
		if(minutes.length() == 1) {
			return Integer.toString(hour) + ":0" + minutes;
		}
		else {
			return Integer.toString(hour) + ":" + minutes;
		}
		
	}

	public void setTime(int hour, int minute) {
		this.time = hour * 60 + minute;
		if(handler.getMap().isOutside()) {
			handler.getR().setAmbientColor(Utils.changeBrightness(handler.getMap().getAmbientColor(), dayLights[hour]));
		}
	}
}
