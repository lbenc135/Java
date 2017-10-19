package com.licoforen.GameWorld;

import java.util.ArrayList;

import com.badlogic.gdx.math.Intersector;
import com.licoforen.BunnyRun.IActivityRequestHandler;
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

public class GameWorld {

	private ScrollHandler scroller;
	private Bunny bunny;
	private Hedgehog hg1;
	private Bird bird1;
	private Grass g1, g2, g3;
	private Platform p1, p2, p3;
	private ArrayList<Egg> eggs;
	private Basket basket;
	private Bomb bomb;
	private Pickup pickup;

	public float runTime, killTime = -100;
	public int add = 0;
	private float gameHeight;

	private IActivityRequestHandler handler;
	private int score = 0, hgs = 0, birds = 0, bombs = 0, basketeggs = 0,
			carrots = 0, shields = 0, boards = 0;

	public enum GameState {
		MENU, OPTIONS, TUTORIAL, READY, RUNNING, PAUSED, GAMEOVER
	}

	private GameState currentState;

	public GameWorld(float gameHeight, IActivityRequestHandler handler) {
		this.gameHeight = gameHeight;

		this.handler = handler;

		bunny = new Bunny(120, gameHeight - 80, 80, 65);
		scroller = new ScrollHandler(gameHeight - 40, bunny);
		initGameObjects();

		currentState = GameState.MENU;

		handler.getHighScore();
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case RUNNING:
			updateRunning(delta);
			break;
		case PAUSED:
		case GAMEOVER:
			if (hgs != 0 || birds != 0 || bombs != 0 || basketeggs != 0
					|| carrots != 0 || shields != 0 || boards != 0) {
				updateAchievements();
			}
			AssetLoader.prefs.flush();
			AssetLoader.updateCheck();
			break;
		default:
			break;
		}
	}

	public void updateAchievements() {
		AssetLoader.prefs.putInteger("birds",
				AssetLoader.prefs.getInteger("birds") + birds);
		AssetLoader.prefs.putInteger("hedgehogs",
				AssetLoader.prefs.getInteger("hedgehogs") + hgs);
		AssetLoader.prefs.putInteger("bombs",
				AssetLoader.prefs.getInteger("bombs") + bombs);
		AssetLoader.prefs.putInteger("eggs",
				AssetLoader.prefs.getInteger("eggs") + basketeggs);
		AssetLoader.prefs.putInteger("powerups",
				AssetLoader.prefs.getInteger("powerups") + carrots + shields
						+ boards);
		birds = hgs = bombs = basketeggs = carrots = shields = boards = 0;
	}

	public void updateRunning(float delta) {

		bunny.update(delta);
		scroller.update(delta);

		checkAchievements();

		if (bunny.getY() > gameHeight) {
			bunny.die();
			scroller.stop();
			if (AssetLoader.getSound())
				AssetLoader.die.play();

			if (score + bunny.BonusPoints > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score + bunny.BonusPoints);
				if (handler != null)
					handler.submitScore(score + bunny.BonusPoints);
			}
			currentState = GameState.GAMEOVER;

			return;
		}

		checkCollisions();
	}

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bunny.reset((int) (gameHeight - 80));
		scroller = new ScrollHandler(gameHeight - 40, bunny);
		initGameObjects();
	}

	public void initGameObjects() {
		p1 = scroller.getP1();
		p2 = scroller.getP2();
		p3 = scroller.getP3();
		g1 = scroller.getG1();
		g2 = scroller.getG2();
		g3 = scroller.getG3();
		hg1 = scroller.getHG1();
		bird1 = scroller.getBird();
		pickup = scroller.getPickup();
		bomb = scroller.getBomb();
		basket = scroller.getBasket();
	}

	public void start() {
		currentState = GameState.RUNNING;
		AssetLoader.prefs.putInteger("played",
				AssetLoader.prefs.getInteger("played") + 1);
	}

	public void checkCollisions() {

		checkPlatforms();

		checkGround();

		checkDeath();

		checkPickups();
	}

	private void checkGround() {

		if (bunny.boardactive) {
			if (bunny.getY() + bunny.getHeight() > g1.getY() + 10) {
				bunny.setY((int) g1.getY() - 45);
				bunny.setYVelocity(0);
			}
			if (hg1.getY() + hg1.getHeight() > g1.getY() + 10) {
				hg1.setY((int) g1.getY() - 45);
			}
		}

		if (Intersector.overlaps(bunny.getBoundCircle(), g1.getBounds())) {
			if (bunny.getYVelocity() >= 0) {
				bunny.setYVelocity(0);
				bunny.setY((int) g1.getY() - 45);
			}
		}

		if (Intersector.overlaps(bunny.getBoundCircle(), g2.getBounds())) {
			if (bunny.getYVelocity() >= 0) {
				bunny.setYVelocity(0);
				bunny.setY((int) g2.getY() - 45);
			}
		}

		if (Intersector.overlaps(bunny.getBoundCircle(), g3.getBounds())) {
			if (bunny.getYVelocity() >= 0) {
				bunny.setYVelocity(0);
				bunny.setY((int) g3.getY() - 45);
			}
		}

		if (Intersector.overlaps(hg1.getBoundCircle(), g1.getBounds())) {
			hg1.setY((int) g1.getY() - 45);
		}

		if (Intersector.overlaps(hg1.getBoundCircle(), g2.getBounds())) {
			hg1.setY((int) g2.getY() - 45);
		}

		if (Intersector.overlaps(hg1.getBoundCircle(), g3.getBounds())) {
			hg1.setY((int) g3.getY() - 45);
		}
	}

	private void checkPlatforms() {

		// Bunny
		if (p1.getY() >= bunny.getY() + bunny.getHeight() - 20
				&& p1.getX() < bunny.getX() + bunny.getWidth()
				&& Intersector.overlaps(bunny.getBoundCircle(), p1.getBounds())
				&& bunny.getYVelocity() > 0) {
			bunny.setY((int) p1.getY() - (int) bunny.getHeight());
			bunny.setYVelocity(0);
		}

		if (p2.getY() >= bunny.getY() + bunny.getHeight() - 20
				&& p2.getX() < bunny.getX() + bunny.getWidth()
				&& Intersector.overlaps(bunny.getBoundCircle(), p2.getBounds())
				&& bunny.getYVelocity() > 0) {
			bunny.setY((int) p2.getY() - (int) bunny.getHeight());
			bunny.setYVelocity(0);
		}

		if (p3.getY() >= bunny.getY() + bunny.getHeight() - 20
				&& p3.getX() < bunny.getX() + bunny.getWidth()
				&& Intersector.overlaps(bunny.getBoundCircle(), p3.getBounds())
				&& bunny.getYVelocity() > 0) {
			bunny.setY((int) p3.getY() - (int) bunny.getHeight());
			bunny.setYVelocity(0);
		}

		// Hedgehog
		if (p1.getY() >= hg1.getY() + hg1.getHeight() - 20
				&& p1.getX() < hg1.getX() + hg1.getWidth() && hg1.isFalling()
				&& Intersector.overlaps(hg1.getBoundCircle(), p1.getBounds())) {
			hg1.setY((int) p1.getY() - (int) hg1.getHeight() + 3);
		}

		if (p2.getY() >= hg1.getY() + hg1.getHeight() - 20
				&& p2.getX() < hg1.getX() + hg1.getWidth() && hg1.isFalling()
				&& Intersector.overlaps(hg1.getBoundCircle(), p2.getBounds())) {
			hg1.setY((int) p2.getY() - (int) hg1.getHeight() + 3);

		}

		if (p3.getY() >= hg1.getY() + hg1.getHeight() - 20
				&& p3.getX() < hg1.getX() + hg1.getWidth() && hg1.isFalling()
				&& Intersector.overlaps(hg1.getBoundCircle(), p3.getBounds())) {
			hg1.setY((int) p3.getY() - (int) hg1.getHeight() + 3);
		}
	}

	private void checkDeath() {

		// Holes
		if (hg1.getY() > gameHeight) {
			hg1.die();
		}

		if (bird1.getY() > gameHeight) {
			bird1.die();
		}

		// Enemies
		if (hg1.isAlive()
				&& bunny.alive
				&& !bunny.shieldactive
				&& Intersector.overlaps(bunny.getBoundCircle(),
						hg1.getBoundCircle())) {
			bunny.die();
			if (AssetLoader.getSound())
				AssetLoader.hgHit.play();
		}

		if (bird1.isAlive()
				&& bunny.alive
				&& !bunny.shieldactive
				&& Intersector.overlaps(bunny.getBoundCircle(),
						bird1.getBoundCircle())) {
			bunny.die();
			if (AssetLoader.getSound())
				AssetLoader.hgHit.play();
		}

		if (scroller.getBomb().getX() < bunny.getX() + bunny.getWidth()
				&& bunny.alive
				&& !bunny.shieldactive
				&& Intersector.overlaps(bunny.getBoundCircle(),
						bomb.getBoundCircle())) {
			bunny.die();
			if (AssetLoader.getSound())
				AssetLoader.bombHit.play();
			bomb.reset(-100);
		}

		// Eggs
		eggs = scroller.getEggs();
		for (int i = 0; i < eggs.size(); i++) {
			// Hedgehog
			if (eggs.get(i).getX() + eggs.get(i).getWidth() > hg1.getX()
					&& hg1.isAlive()
					&& Intersector.overlaps(eggs.get(i).getBoundCircle(),
							hg1.getBoundCircle())) {
				hg1.fall();
				bunny.BonusPoints += 50;
				add = 50;
				killTime = runTime;
				hgs++;
				if (AssetLoader.getSound())
					AssetLoader.hgDie.play();

				scroller.removeEgg(i);
				continue;
			}

			// Bird
			if (eggs.get(i).getX() + eggs.get(i).getWidth() > bird1.getX()
					&& bird1.isAlive()
					&& Intersector.overlaps(eggs.get(i).getBoundCircle(),
							bird1.getBoundCircle())) {
				bird1.fall();
				bunny.BonusPoints += 80;
				add = 80;
				killTime = runTime;
				birds++;
				if (AssetLoader.getSound())
					AssetLoader.birdDie.play();
				scroller.removeEgg(i);
				continue;
			}

			// Bomb
			if (eggs.get(i).getX() + eggs.get(i).getWidth() > bomb.getX()
					&& Intersector.overlaps(eggs.get(i).getBoundCircle(),
							bomb.getBoundCircle())) {
				bomb.reset(-100, 0, 0);
				bunny.BonusPoints += 100;
				add = 100;
				killTime = runTime;
				bombs++;
				if (AssetLoader.getSound())
					AssetLoader.bombDie.play();
				scroller.removeEgg(i);
				continue;
			}
		}
	}

	private void checkPickups() {

		// Basket
		if (bunny.getX() + bunny.getWidth() > basket.getX()
				&& bunny.alive
				&& Intersector.overlaps(bunny.getBoundCircle(),
						basket.getBoundCircle())) {
			basket.picked = true;
			basketeggs += basket.getValue();
			if (AssetLoader.getSound())
				AssetLoader.collectBasket.play();
		}

		// Pickup
		if (bunny.getX() + bunny.getWidth() > pickup.getX()
				&& bunny.alive
				&& Intersector.overlaps(bunny.getBoundCircle(),
						pickup.getBounds())) {
			pickup.picked = true;
			switch (pickup.getType()) {
			case CARROT:
				carrots++;
				if (AssetLoader.getSound())
					AssetLoader.collectCarrot.play();
				break;
			case BOARD:
				boards++;
				bunny.boardactive = true;
				if (AssetLoader.getSound())
					AssetLoader.collectBoard.play();
				break;
			case SHIELD:
				shields++;
				bunny.shieldactive = true;
				if (AssetLoader.getSound())
					AssetLoader.collectShield.play();
				break;
			}
		}
	}

	private void checkAchievements() {

		// Runner
		if (score + bunny.BonusPoints >= 1000
				&& AssetLoader.prefs.getBoolean("unlocked_runner") == false) {
			AssetLoader.prefs.putBoolean("unlocked_runner", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockRunner();
		}

		// Skilled runner
		if (score + bunny.BonusPoints >= 2000
				&& AssetLoader.prefs.getBoolean("unlocked_srunner") == false) {
			AssetLoader.prefs.putBoolean("unlocked_srunner", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockSRunner();
		}

		// Champion runner
		if (score + bunny.BonusPoints >= 5000
				&& AssetLoader.prefs.getBoolean("unlocked_crunner") == false) {
			AssetLoader.prefs.putBoolean("unlocked_crunner", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockCRunner();
		}

		// Amateur hunter
		if (AssetLoader.prefs.getBoolean("unlocked_ahunter") == false
				&& AssetLoader.prefs.getInteger("hedgehogs") >= 100) {
			AssetLoader.prefs.putBoolean("unlocked_ahunter", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockAHunter();
		}

		// Hunter
		if (AssetLoader.prefs.getBoolean("unlocked_hunter") == false
				&& AssetLoader.prefs.getInteger("birds") >= 100) {
			AssetLoader.prefs.putBoolean("unlocked_hunter", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockHunter();
		}

		// Bomb killer
		if (AssetLoader.prefs.getBoolean("unlocked_bkiller") == false
				&& AssetLoader.prefs.getInteger("bombs") >= 100) {
			AssetLoader.prefs.putBoolean("unlocked_bkiller", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockBombKiller();
		}

		// Stock refill
		if (AssetLoader.prefs.getBoolean("unlocked_stock") == false
				&& AssetLoader.prefs.getInteger("eggs") >= 150) {
			AssetLoader.prefs.putBoolean("unlocked_stock", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockStockRefill();
		}

		// All about power
		if (AssetLoader.prefs.getBoolean("unlocked_power") == false
				&& AssetLoader.prefs.getInteger("powerups") >= 100) {
			AssetLoader.prefs.putBoolean("unlocked_power", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockPower();
		}

		// Master
		if (AssetLoader.prefs.getBoolean("unlocked_master") == false
				&& AssetLoader.prefs.getInteger("played") >= 1000) {
			AssetLoader.prefs.putBoolean("unlocked_master", true);
			AssetLoader.prefs.flush();
			if (handler != null)
				handler.unlockMaster();
		}

	}

	public Bunny getBunny() {
		return bunny;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int newscore) {
		score = newscore;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public void ready() {
		currentState = GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public void menu() {
		currentState = GameState.MENU;
		
		g1.resetResize(0, 1000);
	}

	public boolean isOptions() {
		return currentState == GameState.OPTIONS;
	}

	public void options() {
		currentState = GameState.OPTIONS;
	}

	public boolean isPaused() {
		return currentState == GameState.PAUSED;
	}

	public void pause() {
		if (currentState == GameState.RUNNING)
			currentState = GameState.PAUSED;
	}

	public void resume() {
		currentState = GameState.RUNNING;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isTutorial() {
		return currentState == GameState.TUTORIAL;
	}

	public void tutorial() {
		currentState = GameState.TUTORIAL;
	}

	public void showHighscores() {
		if (handler != null)
			handler.getScores();
	}

	public void showAchievements() {
		if (handler != null)
			handler.getAchievements();
	}
}
