package com.licoforen.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class Egg extends Scrollable {

	private Circle boundCircle;
	public TextureRegion color;
	
	public Egg(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		boundCircle = new Circle();
	}
	
	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		boundCircle.set(position.x + width / 2, position.y + height / 2, 15.0f);
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}
}
