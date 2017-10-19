package com.licoforen.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;

public class Platform extends Scrollable {

	private static Random r = new Random();
	
	private Rectangle bounds;
	
	public Platform(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		bounds = new Rectangle();
	}
	
	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		bounds.set(position.x, position.y, width, height);
	}
	
	@Override
	public void reset(float newX) {
		width = r.nextInt(250)+250;
		super.reset(newX);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
