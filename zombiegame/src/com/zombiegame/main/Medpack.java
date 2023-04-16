package com.zombiegame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Medpack extends GameObject{
	
	public Medpack(int x, int y, ID id, Handler handler) {
		super(x, y, id);
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 80, 60);
		g.setColor(Color.red);
		g.fillRect(x+35, y+15, 10, 30);
		g.fillRect(x+25, y+25, 30, 10);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 80, 60);
	}

	
	public void tick() {}
	
}
