package com.kylethatcher.engine.jukebox;


public class Jukebox {
	private float fxVolume = -17;
	private float ambientVolume = -17;
	private SoundClip currentAmbient = null;
	private boolean muted = false;
	
	public boolean isMuted() {
		return muted;
	}

	public void setMuted(int muted) {
		if(muted == 1) {
			this.muted = true;
			currentAmbient.stop();
		}
		else {
			this.muted = false;
			currentAmbient.loop();
		}
	}	

	public Jukebox() {
	}
	
	public void generateSound(String path){
		if(muted) return;
		SoundClip sc = new SoundClip(path);
		sc.setVolume(fxVolume);
		sc.play();
	}
	
	public float getAmbientVolume() {
		return ambientVolume;
	}

	public void setAmbient(SoundClip c) {
		
		if(currentAmbient != null) {
			System.out.println("Null");
			currentAmbient.stop();
			currentAmbient.close();
		}
		c.setVolume(ambientVolume);
		if(!muted) c.loop();
		currentAmbient = c;
	}
	
	public void setAmbientVolume(float decibel) {
		ambientVolume = decibel;
		currentAmbient.setVolume(decibel);
	}
	
	public void setFxVolume(float decibel) {
		fxVolume = decibel;
	}

	public float getFxVolume() {
		return fxVolume;
	}
}
