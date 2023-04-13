package com.zombiegame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{
	
	private Color color = new Color(148, 141, 62);
	private double angle;
	private int speed = 20;
	private Handler handler;
	
	public Bullet(int x, int y, ID id, Handler handler, double angle) {
		super(x, y, id);
		this.handler = handler;
		this.angle = angle;
	}

	public void tick() {
		x += (int) Math.round(speed * Math.cos(angle));
		y += (int) Math.round(speed * Math.sin(angle));
		if(x > Game.WIDTH || x < 0) {
			handler.removeObject(this);
		}
		if(y > Game.HEIGHT || y < 0) {
			handler.removeObject(this);
		}
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, 20, 20);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x-10, y-10, 20, 20);
	}
}
