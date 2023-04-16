package com.zombiegame.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends GameObject{
	
	public BufferedImage image;
	
	private int speedX = 0;
	private int speedY = 0;
	
	private int gunX;
	private int gunY;
	
	private int gunX2;
	private int gunY2;
	
	private double mouseX = x;
	private double mouseY = y;
	
	private double direction = 0;
	private double rotation = 0;
	
	private int weaponLevel = 1;
	
	private AffineTransform tx = AffineTransform.getRotateInstance(rotation, Game.WIDTH/2 - 50, Game.HEIGHT/2 + 50);
	private AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	
	private Handler handler;
	
	public Player(ID id, Handler handler) {
		super(400, 200, id);
		
		this.handler = handler;
		
		image = Game.addImage(100, 100, "res/player.png");
		
		rotate();
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		Stroke temp = g2d.getStroke();
		g2d.setColor(new Color(54, 29, 2));
		g2d.setStroke(new BasicStroke(15));
		g2d.drawLine(x+40, y+40, gunX2, gunY2);
		g2d.setColor(Color.black);
	    g2d.setStroke(new BasicStroke(10));
		g2d.drawLine(x+40, y+40, gunX, gunY);
		g2d.setStroke(temp);
		g.setColor(Color.darkGray);
		g.fillOval(x, y, 80, 80);
		
	}
	
	public void tick() {
		x+=speedX;
		y+=speedY;
		gunX+=speedX;
		gunY+=speedY;
		gunX2+=speedX;
		gunY2+=speedY;
		//x = Game.clamp(x, -20, Game.WIDTH-90);
		//y = Game.clamp(y, -20, Game.HEIGHT-100);
		collision();
		//rotate();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.BossZombie) {
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= 2;
				}
			}
			if(tempObject.getId() == ID.Zombie) {
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= 1;
				}
			}
			if(tempObject.getId() == ID.Medpack) {
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH += 100;
					Sound.bandageSound();
					handler.removeObject(tempObject);
				}
			}
			if(tempObject.getId() == ID.shotgun) {
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.setGun(Game.addImage(150, 50, "res/shotgun.png"));
					setWeaponLevel(2);
					HUD.setAmmoMax(2);
					HUD.setAmmo(2);
					handler.removeObject(tempObject);
					Sound.pickupSound();
				}
			}
			if(tempObject.getId() == ID.sniper) {
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.setGun(Game.addImage(150, 50, "res/sniper.png"));
					setWeaponLevel(3);
					HUD.setAmmoMax(3);
					HUD.setAmmo(3);
					handler.removeObject(tempObject);
					Sound.pickupSound();
				}
			}
		}
	}
	
	public void shoot(double mouseX, double mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		rotate();
		if(HUD.getAmmo() > 0) {
			double angle = Math.atan( (mouseY - gunY) / (mouseX - gunX) );
			if(mouseX - gunX < 0) {
				angle += Math.PI;
			}
			HUD.setAmmo(HUD.getAmmo() - 1);
			if(weaponLevel == 1) {
				Sound.rifleSound();
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
			}
			if(weaponLevel == 2) {
				Sound.shotgunSound();
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle + Math.PI * (1.0/16)));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle - Math.PI * (1.0/16)));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
			}
			if(weaponLevel == 3) {
				Sound.sniperSound();
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(gunX, gunY, ID.Bullet, handler, angle));
			}
		}
	}
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	private void rotate() {
		direction = Math.atan((mouseY-gunY) / (mouseX - gunX));
		if(mouseX-gunX < 0) {
			direction += Math.PI;
		}
		System.out.println(Math.toDegrees(direction));
		gunX = (int) Math.round(80*Math.cos(direction)) + x+ 40;
		gunY = (int) Math.round(80*Math.sin(direction)) + y+ 40;
		
		gunX2 = (int) Math.round(50*Math.cos(direction)) + x+ 40;
		gunY2 = (int) Math.round(50*Math.sin(direction)) + y+ 40;
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x+20 , y + 20, 60, 60);
	}

	public int getWeaponLevel() {
		return weaponLevel;
	}

	public void setWeaponLevel(int weaponLevel) {
		this.weaponLevel = weaponLevel;
	}
}
	