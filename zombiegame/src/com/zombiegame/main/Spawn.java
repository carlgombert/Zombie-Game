package com.zombiegame.main;

import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
		scoreKeep++;
		
		if(scoreKeep >= 500) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			if(hud.getLevel() % 10 > hud.getLevel() / 10) {
				for(int i = 0; i < Math.round(hud.getLevel() * 1.5); i++) {
					handler.addObject(new Zombie(ID.Zombie, handler));
				}
			}
			else if (hud.getLevel() % 10 == 0){
				for(int i = 0; i < hud.getLevel() / 10; i++) {
					handler.addObject(new BossZombie(ID.BossZombie, handler, 15));
				}
			}
			if(hud.getLevel() == 12) {
				handler.addObject(new WeaponDrop(Game.WIDTH / 2 - 113, Game.HEIGHT / 2 - 38, ID.shotgun, handler, "res/shotgun.png"));
			}
			if(hud.getLevel() == 23) {
				handler.addObject(new WeaponDrop(Game.WIDTH / 2 - 113, Game.HEIGHT / 2 - 38, ID.sniper, handler, "res/sniper.png"));
			}
		}
	}
}
