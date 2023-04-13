package com.zombiegame.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
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
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(op.filter(image, null), x, y, null);
		
	}
	
	public void tick() {
		x+=speedX;
		y+=speedY;
		x = Game.clamp(x, -20, Game.WIDTH-90);
		y = Game.clamp(y, -20, Game.HEIGHT-100);
		collision();
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
		if(HUD.getAmmo() > 0) {
			double angle = Math.atan( (mouseY - y) / (mouseX - x) );
			if(mouseX - x < 0) {
				angle += Math.PI;
			}
			HUD.setAmmo(HUD.getAmmo() - 1);
			if(weaponLevel == 1) {
				Sound.rifleSound();
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
			}
			if(weaponLevel == 2) {
				Sound.shotgunSound();
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle + Math.PI * (1.0/16)));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle - Math.PI * (1.0/16)));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
			}
			if(weaponLevel == 3) {
				Sound.sniperSound();
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
				handler.addObject(new Bullet(x + 50, y + 50, ID.Bullet, handler, angle));
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
		double mouseX = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX());
		double mouseY = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY());
		double temp = (mouseY-y) / (mouseX - x);
		System.out.println("player: " + x + ", " + y);
		System.out.println("mouse: " + mouseX + ", " + mouseY);
		System.out.println("direction " + Math.atan(temp));
		rotation = direction - Math.atan((mouseY-y) / (mouseX - x));
		direction = Math.atan((mouseY-y) / (mouseX - x));
		if(mouseX-x < 0) {
			direction += Math.PI;
		}
		tx = AffineTransform.getRotateInstance(direction, x+50, y-50);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
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
	