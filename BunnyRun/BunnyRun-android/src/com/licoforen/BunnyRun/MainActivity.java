package com.licoforen.BunnyRun;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class MainActivity extends AndroidApplication implements
		IActivityRequestHandler, GameHelperListener {

	private GameHelper gHelper;

	protected AdView adView;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (Build.VERSION.SDK_INT > 8) {
					adView.setVisibility(View.GONE);
					adView.pause();
				}
				break;
			case 1:
				if (Build.VERSION.SDK_INT > 8) {
					adView.setVisibility(View.VISIBLE);
					adView.resume();
				}
				break;
			case 2:
				Toast.makeText(getApplicationContext(),
						"Sign in to Google Services", Toast.LENGTH_SHORT)
						.show();
				;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		RelativeLayout layout = new RelativeLayout(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		if (Build.VERSION.SDK_INT > 8) {
			gHelper = new GameHelper(this, BaseGameActivity.CLIENT_GAMES);
			gHelper.setup(this);

			adView = new AdView(this);
			adView.setAdSize(AdSize.BANNER);
			adView.setAdUnitId("a1535e69940d511");
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);

			layout.setPadding(0, 1, 0, 0);
		}

		View gameView;
		if (Build.VERSION.SDK_INT > 8)
			gameView = initializeForView(new BRGame(this), false);
		else
			gameView = initializeForView(new BRGame_froyo(this), false);
		layout.addView(gameView);

		if (Build.VERSION.SDK_INT > 8) {
			RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layout.addView(adView, adParams);
		}

		setContentView(layout);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (Build.VERSION.SDK_INT > 8)
			gHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (Build.VERSION.SDK_INT > 8)
			gHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Build.VERSION.SDK_INT > 8)
			gHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void msg(int message) {
		handler.sendEmptyMessage(message);
	}

	@Override
	public void Login() {

		if (Build.VERSION.SDK_INT > 8)
			try {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gHelper.beginUserInitiatedSignIn();
					}
				});
			} catch (final Exception e) {

			}
	}

	@Override
	public void Logout() {

		if (Build.VERSION.SDK_INT > 8)
			try {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gHelper.signOut();
					}
				});
			} catch (final Exception e) {

			}
	}

	@Override
	public void submitScore(int score) {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Leaderboards.submitScore(gHelper.getApiClient(),
					getString(R.string.leaderboard_high_scores), score);
	}

	@Override
	public void getScores() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
					gHelper.getApiClient(),
					getString(R.string.leaderboard_high_scores)), 105);
		else
			handler.sendEmptyMessage(2);
	}

	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {

	}

	@Override
	public void getAchievements() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(gHelper
							.getApiClient()), 104);
		else
			handler.sendEmptyMessage(2);
	}

	@Override
	public void unlockRunner() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_runner));
	}

	@Override
	public void unlockSRunner() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_skilled_runner));
	}

	@Override
	public void unlockCRunner() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_champion_runner));
	}

	@Override
	public void unlockAHunter() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_amateur_hunter));
	}

	@Override
	public void unlockHunter() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_hunter));
	}

	@Override
	public void unlockBombKiller() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_bomb_killer));
	}

	@Override
	public void unlockStockRefill() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_stock_refill));
	}

	@Override
	public void unlockPower() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_all_about_power));
	}

	@Override
	public void unlockMaster() {
		if (Build.VERSION.SDK_INT > 8 && gHelper.isSignedIn())
			Games.Achievements.unlock(gHelper.getApiClient(),
					getString(R.string.achievement_master));
	}

}