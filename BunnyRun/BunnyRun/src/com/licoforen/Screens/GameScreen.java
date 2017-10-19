package com.licoforen.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.licoforen.BunnyRun.IActivityRequestHandler;
import com.licoforen.GameWorld.GameRenderer;
import com.licoforen.GameWorld.GameWorld;
import com.licoforen.Helpers.AssetLoader;
import com.licoforen.Helpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	private float gameHeight;

	public GameScreen(IActivityRequestHandler handler) {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 800;
		gameHeight = screenHeight / (screenWidth / gameWidth);

		world = new GameWorld(gameHeight, handler);
		renderer = new GameRenderer(world, (int) gameHeight);

		Gdx.input.setInputProcessor(new InputHandler(world, renderer,
				screenWidth / gameWidth, screenHeight / gameHeight));
		Gdx.input.setCatchBackKey(true);
		if (AssetLoader.getMusic()) {
			int i = 0;
			while (AssetLoader.bgMusic == null && i < 3) {
				AssetLoader.reload_music();
				i++;
			}
			if (AssetLoader.bgMusic != null) {
				AssetLoader.bgMusic.play();
				AssetLoader.bgMusic.setLooping(true);
			}

		}

	}

	@Override
	public void render(float delta) {
		if (delta > .03f) {
			delta = .03f;
		}

		if (!world.isReady() && !world.isPaused() && !world.isGameOver())
			runTime += delta;
		else if (!world.isPaused())
			runTime = 0;
		world.update(delta);
		renderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		world.pause();
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		// Leave blank
	}

	public float getGameHeight() {
		return gameHeight;
	}
}
