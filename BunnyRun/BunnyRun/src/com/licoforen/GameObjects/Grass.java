package com.licoforen.GameObjects;

import com.badlogic.gdx.math.Rectangle;

public class Grass extends Scrollable {

	private Rectangle bounds;

	public Grass(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		bounds = new Rectangle();
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		bounds.set(position.x, position.y + 10, width, height);
	}
	
	public void resetResize(float newX, int width) {
		reset(newX);
		this.width = width; 
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
