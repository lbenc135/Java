package com.licoforen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Switch {

	public float x, y, width, height;

	public Texture on, off;

	private Rectangle bounds;
	public boolean state;
	public String text;

	public Switch(float x, float y, float width, float height, Texture on,
			Texture off, String text, boolean state) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.on = on;
		this.off = off;
		this.text = text;
		this.state = state;

		bounds = new Rectangle(x, y, width, height);
	}

	public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (state) {
            batcher.draw(on, x, y, width, height);
        } else {
            batcher.draw(off, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {

        if (bounds.contains(screenX, screenY)) {
            state = !state;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        return false;
    }
	
}
