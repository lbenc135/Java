package com.licoforen.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bomb extends Scrollable {

	private Vector2 acceleration;
	private Circle boundCircle;

	public Bomb(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		acceleration = new Vector2(0, 150);
		boundCircle = new Circle();
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		boundCircle.set(position.x + width / 2, position.y + height / 2, 20.0f);

		if (position.x != -100) {
			velocity.add(acceleration.cpy().scl(delta));
			if (velocity.y > 500) {
				velocity.y = 500;
			}
		} else {
			velocity.y = 0;
		}

	}

	public void reset(float newX, float newY, float speed) {
		velocity.x = speed;
		position.y = newY;
		super.reset(newX);
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}
}
