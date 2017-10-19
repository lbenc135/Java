package com.licoforen.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hedgehog extends Scrollable {

	private Vector2 acceleration;

	private Circle boundCircle;
	private boolean alive = true;

	private Random r;

	public Hedgehog(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		acceleration = new Vector2(0, 2000);
		boundCircle = new Circle();
		r = new Random();
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);

		velocity.add(acceleration.cpy().scl(delta));
		if (velocity.y > 500) {
			velocity.y = 500;
		}

		boundCircle.set(position.x + width / 2, position.y + height / 2, 35.0f);
	}

	@Override
	public void reset(float newX) {
		position.y = 0;
		alive = true;
		super.reset(newX);
	}

	public boolean inAir() {
		return velocity.y != 0;
	}

	public void setY(int y) {
		if (alive) {
			position.y = y;
			velocity.y = 0;
		}
	}

	public void fall() {
		alive = false;
	}

	public void die() {
		reset(r.nextInt(600) + 1000);
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isFalling() {
		return velocity.y > 0;
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}
}
