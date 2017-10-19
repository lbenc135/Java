package com.licoforen.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bird extends Scrollable {

	private Vector2 acceleration;

	private Circle boundCircle;
	private boolean alive = true;
	private Random r;
	private float posy = 0;

	private int dropPos;
	public boolean dropped = false;

	public Bird(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		acceleration = new Vector2(0, 2000);
		boundCircle = new Circle();
		r = new Random();
		dropPos = r.nextInt(500) + 300;
		posy = y;
	}

	@Override
	public void update(float delta, float scrollSpeed) {
		super.update(delta, scrollSpeed);
		boundCircle.set(position.x + width / 2, position.y + height / 2, 25.0f);

		if (!alive) {
			velocity.add(acceleration.cpy().scl(delta));
			if (velocity.y > 500) {
				velocity.y = 500;
			}
		}
	}

	@Override
	public void reset(float newX) {
		super.reset(newX);
		dropPos = r.nextInt(500) + 300;
		dropped = false;
		alive = true;
		position.y = posy;
		velocity.y = 0;
	}

	public void fall() {
		alive = false;
	}

	public void die() {
		isScrolledLeft = true;
	}

	public boolean isAlive() {
		return alive;
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}

	public int getPos() {
		return dropPos;
	}
}
