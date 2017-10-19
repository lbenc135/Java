package com.licoforen.Helpers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.licoforen.GameObjects.Bunny;
import com.licoforen.GameWorld.GameRenderer;
import com.licoforen.GameWorld.GameWorld;
import com.licoforen.ui.Button;
import com.licoforen.ui.Switch;

public class InputHandler implements InputProcessor {

	private GameWorld myWorld;
	private GameRenderer renderer;
	private Bunny mybunny;

	private ArrayList<Button> menuButtons;
	private ArrayList<Switch> optionsButtons;
	private Button playButton, optionsButton, tutoButton, scoresButton,
			achButton;
	private Switch musicButton, soundButton, swapButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public InputHandler(GameWorld myWorld, GameRenderer renderer,
			float scaleFactorX, float scaleFactorY) {
		this.myWorld = myWorld;
		mybunny = myWorld.getBunny();
		this.renderer = renderer;

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<Button>();
		optionsButtons = new ArrayList<Switch>();

		playButton = new Button(300, 120, 200, 100, AssetLoader.buttonNormal,
				AssetLoader.buttonPushed, "Play");
		optionsButton = new Button(300, 220, 200, 100,
				AssetLoader.buttonNormal, AssetLoader.buttonPushed, "Options");
		tutoButton = new Button(300, 320, 200, 100, AssetLoader.buttonNormal,
				AssetLoader.buttonPushed, "Instructions");
		scoresButton = new Button(650, 120, 80, 80, AssetLoader.hscores,
				AssetLoader.hscores, "");
		achButton = new Button(650, 220, 80, 80, AssetLoader.ach,
				AssetLoader.ach, "");
		menuButtons.add(playButton);
		menuButtons.add(optionsButton);
		menuButtons.add(tutoButton);
		menuButtons.add(scoresButton);
		menuButtons.add(achButton);

		musicButton = new Switch(500, 120, 100, 50, AssetLoader.switchOn,
				AssetLoader.switchOff, "Music", AssetLoader.getMusic());
		soundButton = new Switch(500, 220, 100, 50, AssetLoader.switchOn,
				AssetLoader.switchOff, "Sounds", AssetLoader.getSound());
		swapButton = new Switch(500, 320, 100, 50, AssetLoader.switchOn,
				AssetLoader.switchOff, "Swap controls", AssetLoader.getSwap());

		optionsButtons.add(musicButton);
		optionsButtons.add(soundButton);
		optionsButtons.add(swapButton);

		renderer.setupButtons(menuButtons, optionsButtons);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
			optionsButton.isTouchDown(screenX, screenY);
			tutoButton.isTouchDown(screenX, screenY);
			scoresButton.isTouchDown(screenX, screenY);
			achButton.isTouchDown(screenX, screenY);
			return true;
		} else if (myWorld.isOptions()) {
			if (musicButton.isTouchDown(screenX, screenY)) {
				AssetLoader.setMusic(!AssetLoader.getMusic());
				if (AssetLoader.getMusic() == false)
					AssetLoader.bgMusic.stop();
				else {
					AssetLoader.bgMusic.play();
					AssetLoader.bgMusic.setLooping(true);
				}
			} else if (soundButton.isTouchDown(screenX, screenY)) {
				AssetLoader.setSound(!AssetLoader.getSound());
			} else if (swapButton.isTouchDown(screenX, screenY)) {
				AssetLoader.setSwap(!AssetLoader.getSwap());
			}
			return true;
		} else if (myWorld.isReady()) {
			myWorld.start();
			AssetLoader.firstTime = false;
			return true;
		} else if (myWorld.isGameOver()) {
			myWorld.restart();
			renderer.restart();
			mybunny = myWorld.getBunny();
			return true;
		} else if (myWorld.isPaused()) {
			myWorld.resume();
			return true;
		} else if(myWorld.isRunning()) {
			if (AssetLoader.getSwap() == true)
				screenX = 800 - screenX;

			if (screenX < 400)
				mybunny.onClick();
			else if (mybunny.alive)
				renderer.addEgg();
		}

		return true;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
			if (myWorld.isMenu()) {
				Gdx.app.exit();
			} else if (myWorld.isRunning()) {
				myWorld.pause();
			} else {
				myWorld.menu();
			}
		} else if (keycode == Input.Keys.SPACE || keycode == Input.Keys.UP) {
			mybunny.onClick();
		} else if (keycode == Input.Keys.RIGHT
				|| keycode == Input.Keys.CONTROL_RIGHT) {
			renderer.addEgg();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.restart();
				renderer.restart();
				myWorld.ready();
			} else if (optionsButton.isTouchUp(screenX, screenY)) {
				myWorld.options();
			} else if (tutoButton.isTouchUp(screenX, screenY)) {
				myWorld.tutorial();
			} else if (scoresButton.isTouchUp(screenX, screenY)) {
				myWorld.showHighscores();
			} else if (achButton.isTouchUp(screenX, screenY)) {
				myWorld.showAchievements();
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public ArrayList<Button> getMenuButtons() {
		return menuButtons;
	}

	public ArrayList<Switch> getOptionsButtons() {
		return optionsButtons;
	}
}
