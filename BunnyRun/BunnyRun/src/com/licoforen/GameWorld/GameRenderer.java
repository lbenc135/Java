package com.licoforen.GameWorld;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.licoforen.GameObjects.Background;
import com.licoforen.GameObjects.Basket;
import com.licoforen.GameObjects.Bird;
import com.licoforen.GameObjects.Bomb;
import com.licoforen.GameObjects.Bunny;
import com.licoforen.GameObjects.Egg;
import com.licoforen.GameObjects.Grass;
import com.licoforen.GameObjects.Hedgehog;
import com.licoforen.GameObjects.Pickup;
import com.licoforen.GameObjects.Platform;
import com.licoforen.GameObjects.ScrollHandler;
import com.licoforen.Helpers.AssetLoader;
import com.licoforen.ui.Button;
import com.licoforen.ui.Switch;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private SpriteBatch batcher;

	// Game Objects
	private Bunny bunny;
	private ScrollHandler scroller;
	private Grass g1, g2, g3;
	private Background bg1, bg2;
	private Platform p1, p2, p3;
	private Hedgehog hg1;
	private ArrayList<Egg> eggs;
	private Basket b;
	private Bird bird1;
	private Bomb bomb1;
	private Pickup pickup;

	private Random r;
	private String highScore, score, eggsLeftS, val;

	public int eggsLeft = 5;
	public int carrotLeft = 0;
	private float carrotStartTime;
	public int boardLeft = 0;
	private float boardStartTime;
	public int shieldLeft = 0;
	private float shieldStartTime;

	private ArrayList<Button> menuButtons;
	private ArrayList<Switch> optionsButtons;

	public GameRenderer(GameWorld world, int gameHeight) {
		myWorld = world;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 800, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		initGameObjects();

		r = new Random();

		menuButtons = new ArrayList<Button>();
		optionsButtons = new ArrayList<Switch>();
	}

	public void render(float runTime) {

		batcher.begin();

		drawBg();

		if (boardLeft > 0) {
			batcher.disableBlending();
			batcher.draw(AssetLoader.wood, g1.getX() - 200, g1.getY() + 10,
					g1.getWidth(), 20);
			batcher.draw(AssetLoader.wood, g2.getX() - 200, g2.getY() + 10,
					g2.getWidth(), 20);
			batcher.draw(AssetLoader.wood, g3.getX() - 200, g3.getY() + 10,
					g3.getWidth(), 20);
			batcher.enableBlending();
		}

		drawGrass();

		if (myWorld.isMenu()) {
			drawMenu();
		} else if (myWorld.isOptions()) {
			drawOptions();
		} else if (myWorld.isTutorial()) {
			drawTutorial();
		} else {

			batcher.disableBlending();
			if (p1.getX() <= 800)
				batcher.draw(AssetLoader.wood, p1.getX(), p1.getY(),
						p1.getWidth(), p1.getHeight());
			if (p2.getX() <= 800)
				batcher.draw(AssetLoader.wood, p2.getX(), p2.getY(),
						p2.getWidth(), p2.getHeight());
			if (p3.getX() <= 800)
				batcher.draw(AssetLoader.wood, p3.getX(), p3.getY(),
						p3.getWidth(), p3.getHeight());

			batcher.enableBlending();

			drawPickups(runTime);

			drawEggs();

			drawBird(runTime);

			drawHedgehog(runTime);

			drawBunny(runTime);

			checkPickups(runTime);

			drawUI();
		}

		batcher.end();

		if (bunny.alive) {
			myWorld.setScore((int) (runTime * 10));
			ScrollHandler.SCROLL_SPEED = (int) (-200 - runTime);
		}

	}

	private void drawBg() {
		batcher.disableBlending();
		if (bg1.getX() <= 800)
			batcher.draw(AssetLoader.bg, bg1.getX(), bg1.getY(),
					bg1.getWidth(), bg1.getHeight());
		if (bg2.getX() <= 800)
			batcher.draw(AssetLoader.bg, bg2.getX(), bg2.getY(),
					bg2.getWidth(), bg2.getHeight());

		batcher.enableBlending();
		batcher.draw(AssetLoader.sun, 46, 35, 180, 180);

		if (bg1.getX() <= 800)
			batcher.draw(AssetLoader.clouds, bg1.getX(), bg1.getY(),
					bg1.getWidth(), bg1.getHeight());
		if (bg2.getX() <= 800)
			batcher.draw(AssetLoader.clouds, bg2.getX(), bg2.getY(),
					bg2.getWidth(), bg2.getHeight());

	}

	private void drawGrass() {
		if (g1.getX() <= 800)
			batcher.draw(AssetLoader.grass, g1.getX(), g1.getY(),
					g1.getWidth(), g1.getHeight());
		if (g2.getX() <= 800)
			batcher.draw(AssetLoader.grass, g2.getX(), g2.getY(),
					g2.getWidth(), g2.getHeight());
		if (g3.getX() <= 800)
			batcher.draw(AssetLoader.grass, g3.getX(), g3.getY(),
					g3.getWidth(), g3.getHeight());
	}

	private void drawPickups(float runTime) {

		// Basket
		if (!b.picked) {
			if (b.getX() <= 800) {
				batcher.draw(AssetLoader.basket, b.getX(), b.getY(),
						b.getWidth(), b.getHeight());
				val = b.getValue() + "";
				AssetLoader.shadow.draw(batcher, val,
						b.getX() + 15 - val.length() * 6, b.getY() - 30);
				AssetLoader.font.draw(batcher, val,
						b.getX() + 20 - val.length() * 6, b.getY() - 30);
			}
		} else {
			eggsLeft += b.getValue();
			b.isScrolledLeft = true;
			b.picked = false;
		}

		// Pickup
		if (!pickup.picked) {
			if (pickup.getX() <= 800) {
				switch (pickup.getType()) {
				case CARROT:
					batcher.draw(AssetLoader.carrot, pickup.getX(),
							pickup.getY(), pickup.getWidth(),
							pickup.getHeight());
					break;
				case BOARD:
					batcher.draw(AssetLoader.board, pickup.getX(),
							pickup.getY(), pickup.getWidth(),
							pickup.getHeight());
					break;
				case SHIELD:
					batcher.draw(AssetLoader.shield, pickup.getX(),
							pickup.getY(), pickup.getWidth(),
							pickup.getHeight());
					break;
				}
			}
		} else {
			pickup.isScrolledLeft = true;
			pickup.picked = false;

			switch (pickup.getType()) {
			case CARROT:
				carrotStartTime = runTime;
				carrotLeft = 20;
				break;
			case BOARD:
				boardStartTime = runTime;
				boardLeft = 20;
				break;
			case SHIELD:
				shieldStartTime = runTime;
				shieldLeft = 20;
				break;
			}
		}
	}

	private void checkPickups(float runTime) {

		// Carrot
		if (carrotLeft > 0) {
			if (runTime > 0)
				carrotLeft = 20 - (int) (runTime - carrotStartTime);
			batcher.draw(AssetLoader.carrot2, 20, 30, 15, 30);
			val = carrotLeft + "";
			if (carrotLeft <= 3)
				AssetLoader.font.setColor(1, 0.4f, 0.4f, 1);
			AssetLoader.shadow.draw(batcher, val, 75 - 17 * val.length(), 30);
			AssetLoader.font.draw(batcher, val, 80 - 17 * val.length(), 30);
			AssetLoader.font.setColor(Color.WHITE);
		}

		// Board
		if (boardLeft > 0) {
			if (runTime > 0)
				boardLeft = 20 - (int) (runTime - boardStartTime);
			batcher.draw(AssetLoader.board, 110, 30, 20, 30);
			val = boardLeft + "";
			if (boardLeft <= 3)
				AssetLoader.font.setColor(1, 0.4f, 0.4f, 1);
			AssetLoader.shadow.draw(batcher, val, 165 - 17 * val.length(), 30);
			AssetLoader.font.draw(batcher, val, 170 - 17 * val.length(), 30);
			AssetLoader.font.setColor(Color.WHITE);
			bunny.boardactive = true;
		} else {
			bunny.boardactive = false;
		}

		// Shield
		if (shieldLeft > 0) {
			if (runTime > 0)
				shieldLeft = 20 - (int) (runTime - shieldStartTime);
			batcher.draw(AssetLoader.shield, 200, 30, 30, 30);
			val = shieldLeft + "";
			if (shieldLeft <= 3)
				AssetLoader.font.setColor(1, 0.4f, 0.4f, 1);
			AssetLoader.shadow.draw(batcher, val, 260 - 17 * val.length(), 30);
			AssetLoader.font.draw(batcher, val, 265 - 17 * val.length(), 30);
			AssetLoader.font.setColor(Color.WHITE);
			batcher.draw(AssetLoader.shielded, bunny.getX() - 20,
					bunny.getY() - 25, bunny.getWidth() + 40,
					bunny.getHeight() + 50);
		} else {
			bunny.shieldactive = false;
		}
	}

	private void drawEggs() {
		for (int i = 0; i < eggs.size(); i++)
			batcher.draw(eggs.get(i).color, eggs.get(i).getX(), eggs.get(i)
					.getY(), eggs.get(i).getWidth(), eggs.get(i).getHeight());
	}

	private void drawBunny(float runTime) {
		if (bunny.jumpInAir > 0) {
			// mid air double jump
			bunny.jumpInAir--;
			batcher.draw(AssetLoader.bunny4, bunny.getX(), bunny.getY(),
					bunny.getWidth(), bunny.getHeight());
		} else if (bunny.inAir()) {
			batcher.draw(AssetLoader.bunny3, bunny.getX(), bunny.getY(),
					bunny.getWidth(), bunny.getHeight());

		} else {
			batcher.draw(AssetLoader.bunnyAnimation.getKeyFrame(runTime),
					bunny.getX(), bunny.getY(), bunny.getWidth(),
					bunny.getHeight());
		}
	}

	private void drawHedgehog(float runTime) {
		if (hg1.inAir()) {
			if (hg1.getX() <= 800)
				batcher.draw(AssetLoader.hedgehog2, hg1.getX(), hg1.getY(),
						hg1.getWidth(), hg1.getHeight());

		} else {
			if (hg1.getX() <= 800)
				batcher.draw(AssetLoader.hgAnimation.getKeyFrame(runTime),
						hg1.getX(), hg1.getY(), hg1.getWidth(), hg1.getHeight());
		}
	}

	private void drawBird(float runTime) {
		if (bird1.getX() <= 800)
			if (!bird1.isAlive())
				batcher.draw(AssetLoader.bird3, bird1.getX(), bird1.getY(),
						bird1.getWidth(), bird1.getHeight());
			else
				batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
						bird1.getX(), bird1.getY(), bird1.getWidth(),
						bird1.getHeight());

		batcher.draw(AssetLoader.bomb, bomb1.getX(), bomb1.getY(),
				bomb1.getWidth(), bomb1.getHeight());
	}

	private void drawUI() {
		if (myWorld.isReady()) {
			if (AssetLoader.firstTime) {
				AssetLoader.shadow.draw(batcher, "Touch here to jump", 45, 200);
				AssetLoader.font.draw(batcher, "Touch here to jump", 50, 200);
				batcher.draw(AssetLoader.first_instr, 130, 300, 130, 200);

				AssetLoader.shadow.draw(batcher, "Touch here to shoot", 475,
						200);
				AssetLoader.font.draw(batcher, "Touch here to shoot", 480, 200);
				batcher.draw(AssetLoader.first_instr, 580, 300, 130, 200);
			} else {
				AssetLoader.shadow.draw(batcher, "Touch to start", 305, 240);
				AssetLoader.font.draw(batcher, "Touch to start", 310, 240);
			}
		} else if (myWorld.isGameOver()) {
			AssetLoader.shadow.draw(batcher, "Game Over", 315, 150);
			AssetLoader.font.draw(batcher, "Game Over", 320, 150);

			AssetLoader.shadow.draw(batcher, "High Score:", 465, 300);
			AssetLoader.font.draw(batcher, "High Score:", 470, 300);
			highScore = AssetLoader.getHighScore() + "";
			AssetLoader.shadow.draw(batcher, highScore,
					550 - (7 * highScore.length()) - 5, 350);
			AssetLoader.font.draw(batcher, highScore,
					550 - (7 * highScore.length()), 350);

			AssetLoader.shadow.draw(batcher, "Score:", 215, 300);
			AssetLoader.font.draw(batcher, "Score:", 220, 300);
			highScore = (myWorld.getScore() + bunny.BonusPoints) + "";
			AssetLoader.shadow.draw(batcher, highScore,
					260 - (7 * highScore.length()) - 5, 350);
			AssetLoader.font.draw(batcher, highScore,
					260 - (7 * highScore.length()), 350);
		} else if (myWorld.isPaused()) {
			AssetLoader.shadow.draw(batcher, "Touch to resume", 305, 240);
			AssetLoader.font.draw(batcher, "Touch to resume", 310, 240);
		}

		score = (myWorld.getScore() + bunny.BonusPoints) + "";
		AssetLoader.shadow.draw(batcher, score, 750 - 17 * score.length() - 5,
				30);
		AssetLoader.font.draw(batcher, score, 750 - 17 * score.length(), 30);

		
		float alpha = max(1 - (myWorld.runTime - myWorld.killTime) / 2, 0);
		AssetLoader.shadow.setColor(1, 1, 1, alpha);
		AssetLoader.font.setColor(1, 1, 1, alpha);
		score = "+" + myWorld.add;
		AssetLoader.shadow.draw(batcher, score, 750 - 17 * score.length() - 5,
				65);
		AssetLoader.font.draw(batcher, score, 750 - 17 * score.length(), 65);
		AssetLoader.shadow.setColor(1, 1, 1, 1);
		AssetLoader.font.setColor(1, 1, 1, 1);

		eggsLeftS = eggsLeft + "";
		if (eggsLeft == 0)
			AssetLoader.font.setColor(1, 0.4f, 0.4f, 1);
		AssetLoader.shadow.draw(batcher, eggsLeftS, 545, 30);
		AssetLoader.font.draw(batcher, eggsLeftS, 550, 30);
		batcher.draw(AssetLoader.eggsl, 515, 30, 30, 30);
		AssetLoader.font.setColor(Color.WHITE);
	}

	private void drawMenu() {

		batcher.draw(AssetLoader.brlogo, 220, 30, 360, 90);
		
		for (Button button : menuButtons) {
			button.draw(batcher);
			AssetLoader.shadow.draw(batcher, button.text,
					400 - 7 * button.text.length() - 5, button.y
							+ (button.height / 2) - 15);
			AssetLoader.font.draw(batcher, button.text,
					400 - 7 * button.text.length(), button.y
							+ (button.height / 2) - 15);
		}
	}

	private void drawOptions() {
		for (Switch button : optionsButtons) {
			button.draw(batcher);
			AssetLoader.shadow.draw(batcher, button.text,
					250 - 8 * button.text.length() - 5, button.y
							+ (button.height / 2) - 15);
			AssetLoader.font.draw(batcher, button.text,
					250 - 8 * button.text.length(), button.y
							+ (button.height / 2) - 15);
		}
	}

	private void drawTutorial() {
		AssetLoader.shadow.draw(batcher,
				"(Double) jump - touch left side of the screen", 35, 50);
		AssetLoader.font.draw(batcher,
				"(Double) jump - touch left side of the screen", 40, 50);

		AssetLoader.shadow.draw(batcher,
				"Shoot - touch right side of the screen", 35, 100);
		AssetLoader.font.draw(batcher,
				"Shoot - touch right side of the screen", 40, 100);

		AssetLoader.shadow.draw(batcher, "- infinite eggs", 115, 220);
		AssetLoader.font.draw(batcher, "- infinite eggs", 120, 220);

		AssetLoader.shadow.draw(batcher, "- can't get hurt", 115, 300);
		AssetLoader.font.draw(batcher, "- can't get hurt", 120, 300);

		AssetLoader.shadow.draw(batcher, "- can't fall in the hole", 115, 380);
		AssetLoader.font.draw(batcher, "- can't fall in the hole", 120, 380);

		batcher.draw(AssetLoader.carrot2, 56, 200, 32, 64);
		batcher.draw(AssetLoader.shield, 40, 285, 64, 64);
		batcher.draw(AssetLoader.board, 56, 370, 32, 64);
	}

	private void initGameObjects() {
		bunny = myWorld.getBunny();
		scroller = myWorld.getScroller();
		g1 = scroller.getG1();
		g2 = scroller.getG2();
		g3 = scroller.getG3();
		bg1 = scroller.getBg1();
		bg2 = scroller.getBg2();
		p1 = scroller.getP1();
		p2 = scroller.getP2();
		p3 = scroller.getP3();
		hg1 = scroller.getHG1();
		eggs = scroller.getEggs();
		b = scroller.getBasket();
		bird1 = scroller.getBird();
		bomb1 = scroller.getBomb();
		pickup = scroller.getPickup();
	}

	public void restart() {
		initGameObjects();
		eggsLeft = 5;
		carrotLeft = 0;
		boardLeft = 0;
		shieldLeft = 0;
		bunny.BonusPoints = 0;
	}

	public void addEgg() {
		if (eggsLeft > 0 || carrotLeft > 0) {
			Egg eggt = new Egg(210, bunny.getY(), 30, 30, 300);
			eggt.color = AssetLoader.egg1[r.nextInt(6)];
			eggs.add(eggt);
			if (carrotLeft <= 0)
				eggsLeft--;
			if (AssetLoader.getSound())
				AssetLoader.shoot.play();
		}
	}

	public void setupButtons(ArrayList<Button> menuButtons,
			ArrayList<Switch> optionsButtons) {
		this.optionsButtons = optionsButtons;
		this.menuButtons = menuButtons;
	}

	private float max(float a, float b) {
		return a > b ? a : b;
	}
}
