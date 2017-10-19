package com.licoforen.BunnyRun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.licoforen.Helpers.AssetLoader;
import com.licoforen.Screens.SplashScreen;

public class BRGame extends Game implements ApplicationListener {
	
	private IActivityRequestHandler handler;
	
	public BRGame(IActivityRequestHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this, handler));
	}
	
	@Override
	public void dispose() {
		AssetLoader.dispose();
		super.dispose();
	}

}
