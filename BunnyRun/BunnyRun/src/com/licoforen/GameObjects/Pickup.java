package com.licoforen.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;

public class Pickup extends Scrollable {

	private Circle boundCircle;
	public boolean picked = false;
	private Random r = new Random();

	public enum types {
		CARROT, BOARD, SHIELD
	}

	private types type;

	public Pickup(float x, float y, int w, int h, float scrollSpeed) {
		super(x, y, w, h, scrollSpeed);
		boundCircle = new Circle();
		resetType();
		position.y -= height;
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		boundCircle.set(position.x + width / 2, position.y + height / 2, 28.0f);
	}

	public void reset(float newX, float newY) {
		resetType();
		position.y = newY - height;
		super.reset(newX);
	}

	private void resetType() {
		int t = r.nextInt(3);

		switch (t) {
		case 0:
			type = types.BOARD;
			width = 30;
			height = 60;
			break;
		case 1:
			type = types.SHIELD;
			width = height = 55;
			break;
		case 2:
			type = types.CARROT;
			width = 50;
			height = 25;
			break;
		}
	}

	public Circle getBounds() {
		return boundCircle;
	}

	public types getType() {
		return type;
	}
}
