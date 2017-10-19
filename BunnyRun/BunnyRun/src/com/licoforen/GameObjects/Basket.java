package com.licoforen.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;

public class Basket extends Scrollable {

	private int value;
	private Circle boundCircle;
	private Random r;
	public boolean picked = false;

	public Basket(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		r = new Random();
		value = r.nextInt(3) + 1;
		boundCircle = new Circle();
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		boundCircle.set(position.x + width / 2, position.y + height / 2, 25.0f);
	}

	public void reset(float newX, float newY) {
		position.y = newY;
		value = r.nextInt(3) + 1;
		super.reset(newX);
	}

	public int getValue() {
		return value;
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}

}
