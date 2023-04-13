package com.zombiegame.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	static Clip clip;
	static File[] soundFile = new File[30];
	
	public Sound() {
		
		soundFile[0] = new File("res/sound/rifle.wav").getAbsoluteFile();
		soundFile[1] = new File("res/sound/shotgun.wav").getAbsoluteFile();
		soundFile[2] = new File("res/sound/reload.wav").getAbsoluteFile();
		soundFile[3] = new File("res/sound/zombiedeath.wav").getAbsoluteFile();
		soundFile[4] = new File("res/sound/bandage.wav").getAbsoluteFile();
		soundFile[5] = new File("res/sound/pickup.wav").getAbsoluteFile();
		soundFile[6] = new File("res/sound/sniper.wav").getAbsoluteFile();
	}
	
	public static void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {}
	}
	
	public static void play() {
		clip.start();
	}
	
	public static void rifleSound() {
		setFile(0);
		play();
	}
	
	public static void shotgunSound() {
		setFile(1);
		play();
	}
	
	public static void zombieDeathSound() {
		setFile(3);
		play();
	}
	
	public static void reloadSound() {
		setFile(2);
		play();
	}
	
	public static void bandageSound() {
		setFile(4);
		play();
	}
	
	public static void pickupSound() {
		setFile(5);
		play();
	}
	
	public static void sniperSound() {
		setFile(6);
		play();
	}
}
