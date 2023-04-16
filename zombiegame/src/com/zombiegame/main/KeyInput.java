package com.zombiegame.main;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyInput extends KeyAdapter implements MouseListener{
	
	private Handler handler;
	private HUD hud;
	private boolean mousePressed = false;
	
	public KeyInput(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mouseClicked(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		for(int i =0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				((Player) tempObject).shoot(mouseX, mouseY);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_R) {
			HUD.setAmmo(HUD.getAmmoMax());
			Sound.reloadSound();
		}
		
		for(int i =0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
		
				if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) tempObject.setSpeedY(-5);
				if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) tempObject.setSpeedY(5);
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) tempObject.setSpeedX(-5);
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) tempObject.setSpeedX(5);
			
			}
			
		}
			
		if(key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i =0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) tempObject.setSpeedY(0);
				if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) tempObject.setSpeedY(0);
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) tempObject.setSpeedX(0);
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) tempObject.setSpeedX(0);
			}
		}
	}

	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
