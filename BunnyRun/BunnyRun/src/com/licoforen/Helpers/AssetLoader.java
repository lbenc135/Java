package com.licoforen.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture t[], buttonNormal, buttonPushed, switchOn, switchOff,
			hscores, ach;
	public static TextureRegion clouds, bg, grass, platform, wood, egg1[], eggsl,
			basket, carrot, carrot2, shield, shielded, board, bird, bomb,
			hedgehog, logo, brlogo, sun, first_instr, bunny1, bunny2, bunny3,
			bunny4, bunny5, hedgehog1, hedgehog2, bird1, bird2, bird3, bird4;
	public static Animation bunnyAnimation, hgAnimation, birdAnimation;

	public static BitmapFont font, shadow;

	public static Preferences prefs;

	public static Sound bombHit, click, die, hgHit, jump, shoot, hgDie,
			bombDie, birdDie, collectBasket, collectCarrot, collectShield,
			collectBoard;
	public static Music bgMusic;

	private static int check;
	public static boolean firstTime = false;

	public static void load() {

		t = new Texture[1];
		t[0] = new Texture(Gdx.files.internal("data/t1.png"));

		first_instr = new TextureRegion(t[0], 896, 800, 100, 155);
		bg = new TextureRegion(t[0], 512, 0, 512, 256);
		clouds = new TextureRegion(t[0], 0, 256, 1024, 512);
		sun = new TextureRegion(t[0], 512, 864, 64, 64);
		grass = new TextureRegion(t[0], 0, 768, 1024, 20);
		wood = new TextureRegion(t[0], 0, 992, 256, 32);
		basket = new TextureRegion(t[0], 640, 864, 64, 64);
		bomb = new TextureRegion(t[0], 704, 800, 64, 64);
		carrot = new TextureRegion(t[0], 256, 992, 64, 32);

		carrot2 = new TextureRegion(t[0], 576, 928, 32, 64);
		board = new TextureRegion(t[0], 608, 928, 32, 64);
		shield = new TextureRegion(t[0], 576, 864, 64, 64);
		shielded = new TextureRegion(t[0], 512, 928, 64, 64);

		logo = new TextureRegion(t[0], 0, 0, 512, 256);
		brlogo = new TextureRegion(t[0], 0, 864, 512, 128);

		egg1 = new TextureRegion[6];
		egg1[0] = new TextureRegion(t[0], 704, 864, 64, 64);
		egg1[1] = new TextureRegion(t[0], 704, 928, 64, 64);
		egg1[2] = new TextureRegion(t[0], 768, 800, 64, 64);
		egg1[3] = new TextureRegion(t[0], 768, 864, 64, 64);
		egg1[4] = new TextureRegion(t[0], 768, 928, 64, 64);
		egg1[5] = new TextureRegion(t[0], 832, 800, 64, 64);
		eggsl = new TextureRegion(t[0], 640, 928, 64, 64);

		bunny1 = new TextureRegion(t[0], 0, 800, 60, 46);
		bunny2 = new TextureRegion(t[0], 60, 800, 60, 46);
		bunny3 = new TextureRegion(t[0], 120, 800, 60, 46);
		bunny4 = new TextureRegion(t[0], 180, 800, 60, 46);
		bunny5 = new TextureRegion(t[0], 240, 800, 60, 46);

		bird1 = new TextureRegion(t[0], 445, 800, 64, 64);
		bird2 = new TextureRegion(t[0], 509, 800, 64, 64);
		bird3 = new TextureRegion(t[0], 573, 800, 64, 64);
		bird4 = new TextureRegion(t[0], 637, 800, 64, 64);

		hedgehog1 = new TextureRegion(t[0], 310, 800, 64, 64);
		hedgehog2 = new TextureRegion(t[0], 374, 800, 64, 64);

		TextureRegion[] bunnys = { bunny1, bunny2, bunny3, bunny4, bunny5 };
		bunnyAnimation = new Animation(0.08f, bunnys);
		bunnyAnimation.setPlayMode(Animation.LOOP_PINGPONG);

		TextureRegion[] hedgehogs = { hedgehog1, hedgehog2 };
		hgAnimation = new Animation(0.08f, hedgehogs);
		hgAnimation.setPlayMode(Animation.LOOP_PINGPONG);

		TextureRegion[] birds = { bird1, bird2, bird3, bird4 };
		birdAnimation = new Animation(0.08f, birds);
		birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);

		font = new BitmapFont(Gdx.files.internal("data/hobo.fnt"));
		font.setScale(1f, -1f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(1f, -1f);

		die = Gdx.audio.newSound(Gdx.files.internal("data/die.mp3"));
		jump = Gdx.audio.newSound(Gdx.files.internal("data/jump.mp3"));
		shoot = Gdx.audio.newSound(Gdx.files.internal("data/shoot.mp3"));
		hgHit = Gdx.audio.newSound(Gdx.files.internal("data/hedgehog_hit.mp3"));
		hgDie = Gdx.audio.newSound(Gdx.files.internal("data/hedgehog_die.mp3"));
		bombHit = Gdx.audio.newSound(Gdx.files.internal("data/bomb_hit.mp3"));
		click = Gdx.audio.newSound(Gdx.files.internal("data/click.mp3"));
		birdDie = Gdx.audio.newSound(Gdx.files.internal("data/bird_die.mp3"));
		bombDie = Gdx.audio.newSound(Gdx.files.internal("data/bomb_die.mp3"));
		collectBasket = Gdx.audio.newSound(Gdx.files
				.internal("data/collect_basket.mp3"));
		collectCarrot = Gdx.audio.newSound(Gdx.files
				.internal("data/collect_carrot.mp3"));
		collectBoard = Gdx.audio.newSound(Gdx.files
				.internal("data/collect_board.mp3"));
		collectShield = Gdx.audio.newSound(Gdx.files
				.internal("data/collect_shield.mp3"));

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music1.mp3"));

		buttonNormal = new Texture(Gdx.files.internal("data/button_normal.png"));
		buttonPushed = new Texture(Gdx.files.internal("data/button_pushed.png"));
		switchOn = new Texture(Gdx.files.internal("data/switchOn.png"));
		switchOff = new Texture(Gdx.files.internal("data/switchOff.png"));
		hscores = new Texture(Gdx.files.internal("data/highscores.png"));
		ach = new Texture(Gdx.files.internal("data/achievement.png"));

		prefs = Gdx.app.getPreferences("BunnyRun");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
			prefs.putBoolean("music", true);
			prefs.putBoolean("sound", true);
			prefs.putBoolean("swapControls", false);
			prefs.flush();
		}
		if (!prefs.contains("bombs")) {
			prefs.putInteger("played", 0);
			prefs.putInteger("powerups", 0);
			prefs.putInteger("eggs", 0);
			prefs.putInteger("birds", 0);
			prefs.putInteger("bombs", 0);
			prefs.putInteger("hedgehogs", 0);
			prefs.putBoolean("unlocked_runner", false);
			prefs.putBoolean("unlocked_srunner", false);
			prefs.putBoolean("unlocked_crunner", false);
			prefs.putBoolean("unlocked_ahunter", false);
			prefs.putBoolean("unlocked_hunter", false);
			prefs.putBoolean("unlocked_bkiller", false);
			prefs.putBoolean("unlocked_stock", false);
			prefs.putBoolean("unlocked_power", false);
			prefs.putBoolean("unlocked_master", false);
			prefs.flush();
		}

		if (!prefs.contains("val")) {
			updateCheck();
			firstTime = true;
		}
		checkValues();
	}

	public static boolean reload_music() {
		return (bgMusic = Gdx.audio.newMusic(Gdx.files
				.internal("data/music1.mp3"))) != null;
	}

	public static void dispose() {
		updateCheck();

		for (int i = 0; i < t.length; i++)
			t[i].dispose();

		font.dispose();
		shadow.dispose();

		die.dispose();
		jump.dispose();
		shoot.dispose();
		hgHit.dispose();
		hgDie.dispose();
		bombHit.dispose();
		click.dispose();
		birdDie.dispose();
		bombDie.dispose();
		collectBasket.dispose();
		collectBoard.dispose();
		collectCarrot.dispose();
		collectShield.dispose();
		bgMusic.dispose();

		buttonNormal.dispose();
		buttonPushed.dispose();
		switchOff.dispose();
		switchOn.dispose();
		hscores.dispose();
		ach.dispose();
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
		updateCheck();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void setMusic(boolean val) {
		prefs.putBoolean("music", val);
		prefs.flush();
	}

	public static boolean getMusic() {
		return prefs.getBoolean("music");
	}

	public static void setSound(boolean val) {
		prefs.putBoolean("sound", val);
		prefs.flush();
	}

	public static boolean getSound() {
		return prefs.getBoolean("sound");
	}

	public static void setSwap(boolean val) {
		prefs.putBoolean("swapControls", val);
		prefs.flush();
	}

	public static boolean getSwap() {
		return prefs.getBoolean("swapControls");
	}

	public static boolean checkValues() {
		check = prefs.getInteger("highScore") ^ prefs.getInteger("played")
				^ prefs.getInteger("powerups") ^ prefs.getInteger("eggs")
				^ prefs.getInteger("birds") ^ prefs.getInteger("bombs")
				^ prefs.getInteger("hedgehogs") ^ 1337;
		if (check != prefs.getInteger("val")) {
			prefs.putInteger("highScore", 0);
			prefs.putInteger("played", 0);
			prefs.putInteger("powerups", 0);
			prefs.putInteger("eggs", 0);
			prefs.putInteger("birds", 0);
			prefs.putInteger("bombs", 0);
			prefs.putInteger("hedgehogs", 0);
			prefs.flush();
			updateCheck();
			return false;
		}
		return true;

	}

	public static void updateCheck() {
		check = prefs.getInteger("highScore") ^ prefs.getInteger("played")
				^ prefs.getInteger("powerups") ^ prefs.getInteger("eggs")
				^ prefs.getInteger("birds") ^ prefs.getInteger("bombs")
				^ prefs.getInteger("hedgehogs") ^ 1337;
		prefs.putInteger("val", check);
		prefs.flush();
	}

}
