package com.licoforen.GameObjects;

import java.util.ArrayList;
import java.util.Random;

public class ScrollHandler {

	private Grass g1, g2, g3;
	private Background bg1, bg2;
	private Platform p1, p2, p3;
	private Hedgehog hg1;
	private ArrayList<Egg> eggs = new ArrayList<Egg>();
	private Basket b;
	private Bird bird;
	private Bomb bomb;
	private Pickup pickup;

	public static int SCROLL_SPEED = -200;
	private float yPos;

	private static Random r = new Random();

	public ScrollHandler(float yPos, Bunny mybunny) {
		this.yPos = yPos;
		g1 = new Grass(0, yPos, r.nextInt(300) + 800, 40, SCROLL_SPEED);
		g2 = new Grass(g1.getTailX() + r.nextInt(50) + 150, yPos,
				r.nextInt(300) + 400, 40, SCROLL_SPEED);
		g3 = new Grass(g2.getTailX() + r.nextInt(50) + 150, yPos,
				r.nextInt(300) + 400, 40, SCROLL_SPEED);
		bg1 = new Background(0, 0, 1024, 600, SCROLL_SPEED + 150);
		bg2 = new Background(bg1.getTailX() - 1, 0, 1024, 600,
				SCROLL_SPEED + 150);
		p1 = new Platform(r.nextInt(800) + 1000, yPos - 110,
				r.nextInt(250) + 250, 30, SCROLL_SPEED);
		p2 = new Platform(r.nextInt(800) + 1400, yPos - 220,
				r.nextInt(250) + 250, 30, SCROLL_SPEED);
		p3 = new Platform(r.nextInt(800) + 1800, yPos - 330,
				r.nextInt(250) + 250, 30, SCROLL_SPEED);
		hg1 = new Hedgehog(r.nextInt(500) + 1400, 0, 64, 64, SCROLL_SPEED - 50);
		b = new Basket(r.nextInt(5000) + 5000, yPos - r.nextInt(4) * 110 - 50,
				50, 50, SCROLL_SPEED);
		bird = new Bird(r.nextInt(2000) + 3000, yPos - 415, 60, 50,
				SCROLL_SPEED - 150);
		bomb = new Bomb(-100, 0, 40, 40, 0);

		pickup = new Pickup(r.nextInt(8000) + 3500, yPos - r.nextInt(4) * 110,
				0, 0, SCROLL_SPEED);
	}

	public void update(float delta) {
		g1.update(delta, SCROLL_SPEED);
		g2.update(delta, SCROLL_SPEED);
		g3.update(delta, SCROLL_SPEED);
		bg1.update(delta, SCROLL_SPEED + 150);
		bg2.update(delta, SCROLL_SPEED + 150);
		p1.update(delta, SCROLL_SPEED);
		p2.update(delta, SCROLL_SPEED);
		p3.update(delta, SCROLL_SPEED);
		hg1.update(delta, SCROLL_SPEED - 50);
		b.update(delta, SCROLL_SPEED);
		bird.update(delta, SCROLL_SPEED - 150);
		bomb.update(delta, SCROLL_SPEED);
		pickup.update(delta, SCROLL_SPEED);
		for (int i = 0; i < eggs.size(); i++)
			eggs.get(i).update(delta, 300);

		if (g1.isScrolledLeft()) {
			g1.reset(g3.getTailX() + r.nextInt(50) + 150);
			g1.width = r.nextInt(300) + 400;
		} else if (g2.isScrolledLeft()) {
			g2.reset(g1.getTailX() + r.nextInt(50) + 150);
			g2.width = r.nextInt(300) + 400;
		} else if (g3.isScrolledLeft()) {
			g3.reset(g2.getTailX() + r.nextInt(50) + 150);
			g3.width = r.nextInt(300) + 400;
		}

		if (bg1.isScrolledLeft()) {
			bg1.reset(bg2.getTailX() - 1);
		} else if (bg2.isScrolledLeft()) {
			bg2.reset(bg1.getTailX() - 1);
		}

		if (p1.isScrolledLeft()) {
			p1.reset(r.nextInt(1000) + 800);
		}

		if (p2.isScrolledLeft()) {
			p2.reset(r.nextInt(1000) + 800);
		}

		if (p3.isScrolledLeft()) {
			p3.reset(r.nextInt(1000) + 800);
		}

		if (hg1.isScrolledLeft()) {
			hg1.reset(r.nextInt(1000) + 1150);
		}

		if (b.isScrolledLeft()) {
			b.reset(r.nextInt(5000) + 4500, yPos - r.nextInt(4) * 110 - 50);
		}

		if (bird.isScrolledLeft()) {
			bird.reset(r.nextInt(3000) + 3000);
		}

		if (bird.getX() < bird.getPos() && !bird.dropped && bird.isAlive()) {
			bomb.reset(bird.getX(), bird.getY() + bird.getHeight(),
					SCROLL_SPEED);
			bird.dropped = true;
		}

		if (bomb.isScrolledLeft()) {
			bomb.reset(-100, 0, 0);
		}

		if (pickup.isScrolledLeft()) {
			pickup.reset(r.nextInt(8000) + 3000, yPos - r.nextInt(4) * 110);
		}

		for (int i = 0; i < eggs.size(); i++) {
			if (eggs.get(i).getX() > 800) {
				eggs.remove(i);
			}
		}
	}

	public void stop() {
		bg1.stop();
		bg2.stop();
		g1.stop();
		g2.stop();
		g3.stop();
		p1.stop();
		p2.stop();
		p3.stop();
		hg1.stop();
		b.stop();
		bird.stop();
		pickup.stop();
	}

	public Grass getG1() {
		return g1;
	}

	public Grass getG2() {
		return g2;
	}

	public Grass getG3() {
		return g3;
	}

	public Background getBg1() {
		return bg1;
	}

	public Background getBg2() {
		return bg2;
	}

	public Platform getP1() {
		return p1;
	}

	public Platform getP2() {
		return p2;
	}

	public Platform getP3() {
		return p3;
	}

	public Hedgehog getHG1() {
		return hg1;
	}

	public Basket getBasket() {
		return b;
	}

	public Bird getBird() {
		return bird;
	}

	public Bomb getBomb() {
		return bomb;
	}

	public Pickup getPickup() {
		return pickup;
	}

	public ArrayList<Egg> getEggs() {
		return eggs;
	}

	public void removeEgg(int i) {
		eggs.remove(i);
	}
}
