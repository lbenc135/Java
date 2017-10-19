package com.licoforen.BunnyRun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.licoforen.Helpers.AssetLoader;
import com.licoforen.Screens.GameScreen;

public class BRGame_froyo extends Game implements ApplicationListener {
	
	private IActivityRequestHandler handler;
	
	public BRGame_froyo(IActivityRequestHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen(handler));
	}
	
	@Override
	public void dispose() {
		AssetLoader.dispose();
		super.dispose();
	}

}
