package com.zombiegame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class HUD {
	
	public static double HEALTH = 400.0;
	
	private int greenValue = 255;
	
	private int score = 0;
	private int level = 1;
	
	private static int ammo = 8;
	private static int ammoMax = 8;
	
	private static Image gun = Game.addImage(150, 50, "res/m14.png");
	
	public static int pubLevel = 1;
	
	Font trb = new Font("TimesRoman", Font.BOLD, 18);
	
	public void tick() {
		HEALTH = Game.clamp((int) Math.round(HEALTH), 0, 400);
		greenValue = Game.clamp(greenValue, 0, 255);
		
		greenValue = (int) Math.round(HEALTH / 2);
		
		score++;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		
		g.setColor(new Color(75, greenValue, 0));
		g.fillRect(15, 15, (int) Math.round(HEALTH / 2), 32);
		
		g.setColor(new Color(255, 255, 255, 50));
		g.fillRect(15, 15, (int) Math.round(HEALTH / 2), 10);
		
		
		g.setColor(new Color(0, 0, 0, 20));
		g.fillRect(15, 37, (int) Math.round(HEALTH / 2), 10);
		
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		
		g.setColor(Color.black);
		for(int i = 0; i < ammoMax; i++) {
			g.fillOval(480 + (20*i), 25, 10, 20);
		}
		
		g.setColor(new Color(148, 141, 62));
		for(int i = 0; i < ammo; i++) {
			g.fillOval(480 + (20*i), 25, 8, 16);
		}
		
		g.drawImage(gun, 480, 40, null);
		
		g.setColor(Color.black);
		
		g.drawString("Score: " + score, 10, 64);
		g.drawString("Level: " + level, 10, 80);
	}
	
	public void score(int score) {
		this.score = score;
	}
		
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int x) {
		level = x;
	}
	public static int getAmmo() {
		return ammo;
	}
	public static void setAmmo(int ammo) {
		HUD.ammo = ammo;
	}

	public static void setGun(Image newGun) {
		gun = newGun;
	}

	public static int getAmmoMax() {
		return ammoMax;
	}

	public static void setAmmoMax(int ammoMax) {
		HUD.ammoMax = ammoMax;
	}
}
