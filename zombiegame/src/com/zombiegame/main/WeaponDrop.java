package com.zombiegame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class WeaponDrop extends GameObject{
	
	Image image;
	
	public WeaponDrop(int x, int y, ID id, Handler handler, String file) {
		super(x, y, id);
		image = Game.addImage(225, 75, file);
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x+25, y, 25, 225);
	}

	
	public void tick() {}
}
