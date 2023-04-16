//game class handles all the functionality of the game

package com.zombiegame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game extends Canvas implements Runnable{
	
	private boolean alive = true;
	private int restartTimer = 0;
	
	private static final long serialVersionUID = -5505267217615912489L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 15 * 11;
	private Thread thread;
	private boolean running = false;
	public Image ground = addImage(WIDTH, HEIGHT, "res/ground.png");
	
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	
	
	public Game () {
		Sound sound = new Sound();
		handler = new Handler();
		hud = new HUD();
		
		this.addKeyListener(new KeyInput(handler, hud));
		this.addMouseListener(new KeyInput(handler, hud));
		
		spawner = new Spawn(handler, hud);
		
		handler.addObject(new Player(ID.Player, handler));
		
		new Window(WIDTH, HEIGHT, "Z", this);
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				System.out.println("FPS: " + frames);
				timer += 1000;
				frames = 0;
			}
			
		}
		stop();
	}
	
	private void tick() {
		if(alive) {
			handler.tick();
			hud.tick();
			spawner.tick();
			if(HUD.HEALTH <= 0) {
				alive = false;
			}
		}
		else {
			restartTimer += 1;
			if(restartTimer > 300) {
				for(int i =0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					
					if(tempObject.getId() == ID.Zombie) {
						handler.removeObject(tempObject);
						i--;
					}
					if(tempObject.getId() == ID.Medpack) {
						handler.removeObject(tempObject);
						i--;
					}
					if(tempObject.getId() == ID.Player) {
						((Player) tempObject).setWeaponLevel(1);
					}
					if(tempObject.getId() == ID.BossZombie) {
						handler.removeObject(tempObject);
						i--;
					}
					
				}
				hud.setAmmoMax(8);
				hud.setAmmo(8);
				hud.setLevel(1);
				hud.setGun(addImage(150, 50, "res/m14.png"));
				HUD.HEALTH = 400;
				hud.score(0);
				handler.addObject(new Zombie(ID.Zombie, handler));
				alive = true;
				restartTimer = 0;
			}
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		if(alive) {
			g.drawImage(ground, 0, 0, WIDTH, HEIGHT, null);
			
			handler.render(g);
			hud.render(g);
		}
		else {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.red);
			g.drawString("Game Over", WIDTH / 2 - 30, HEIGHT / 2);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static BufferedImage addImage(int width, int height, String file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return image;
	}
}
