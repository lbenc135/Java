package com.licoforen.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.licoforen.Helpers.AssetLoader;

public class Bunny {

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private int width;
	private int height;

	private Circle boundCircle;
	public boolean alive = true;
	public int jumpInAir = 0;
	private boolean doublejump = true;

	public boolean boardactive = false;
	public boolean shieldactive = false;

	public int BonusPoints = 0;

	public Bunny(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 2000);
		boundCircle = new Circle();
	}

	public void update(float delta) {

		velocity.add(acceleration.cpy().scl(delta));

		if (velocity.y > 650) {
			velocity.y = 650;
		}

		position.add(velocity.cpy().scl(delta));
		boundCircle.set(position.x + width / 2, position.y + height / 2, 35.0f);

	}

	public void setY(int y) {
		if (alive) {
			position.y = y;
		}
	}

	public void setYVelocity(int v) {
		if (alive) {
			velocity.y = 0;
		}
	}

	public float getYVelocity() {
		return velocity.y;
	}

	public void die() {
		alive = false;
	}
	
	public void reset(int y) {
		alive=true;
		setY(y);
		setYVelocity(0);
	}
	
	public boolean inAir() {
		if (velocity.y == 0) {
			doublejump = true;
			return false;
		}
		return true;
	}

	public void onClick() {
		if (alive) {
			if (!inAir()) {
				if (AssetLoader.getSound())
					AssetLoader.jump.play();
				velocity.y = -850;
			} else if (doublejump) {
				doublejump = false;
				jumpInAir = 10;
				velocity.y = -700;
				if (AssetLoader.getSound())
					AssetLoader.jump.play();
			}
		}
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Circle getBoundCircle() {
		return boundCircle;
	}
}
