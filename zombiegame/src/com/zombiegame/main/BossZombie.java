package com.zombiegame.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class BossZombie extends GameObject{
	
	public Image zombie;
	
	private int speed = 3;
	private int health = 3;
	
	Handler handler;
	
	public BossZombie(ID id, Handler handler, int health) {
		super(0, 0, id);
		startingPosition();
		
		this.health = health;
		
		this.handler = handler;
		
		zombie = Game.addImage(100, 100, "res/bosszombie.png");
	}
	
	private void startingPosition() {
		int temp = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
		if(temp == 1) {
			x = -200;
			y = (int) Math.floor(Math.random() * (Game.HEIGHT - 1 + 1) + 1);
		}
		if(temp == 2) {
			x = Game.WIDTH+100;
			y = (int) Math.floor(Math.random() * (Game.HEIGHT - 1 + 1) + 1);
		}
		if(temp == 3) {
			y = -200;
			x = (int) Math.floor(Math.random() * (Game.WIDTH - 1 + 1) + 1);
		}
		if(temp == 4) {
			y = Game.HEIGHT+100;
			y = (int) Math.floor(Math.random() * (Game.WIDTH - 1 + 1) + 1);
		}
	}

	public void render(Graphics g) {
			
		g.drawImage(zombie, (int) Math.round(x), (int) Math.round(y), null);
	}
	
	public void tick() {
		for(int i =0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				double targetX = tempObject.getX();
				double targetY = tempObject.getY();
				double angle = Math.atan((targetY-y)/(targetX - x));
				//angle =- Math.PI;
				if(targetX-x < 0) {
					angle += Math.PI;
				}
				
				y += Math.round(speed * Math.sin(angle));
				x += Math.round(speed * Math.cos(angle));
			}
		}
		if(health == 0) {
			handler.removeObject(this);
		}
		collision();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())){
					Sound.zombieDeathSound();
					handler.removeObject(tempObject);
					health--;
				}
			}
			
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x + 10 , y + 10, 80, 80);
	}
	
	public void randomPosition() {
		y = randY() + 60;
		x = randX();
	}
	
	public int randX() {
		int max = Game.WIDTH - 50;
		int min = 50;
		int randInt = (int)Math.floor(Math.random() * (max - min + 1) + min);
		return randInt;
	}
	
	public int randY() {
		int max = Game.HEIGHT - 50;
		int min = 50;
		int randInt = (int)Math.floor(Math.random() * (max - min + 1) + min);
		return randInt;
	}
}
