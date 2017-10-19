package com.licoforen.BunnyRun;

public interface IActivityRequestHandler {
	
	public void msg(int message);

	public void Login();

	public void Logout();

	public void submitScore(int score);

	public void getScores();
	
	public void getAchievements();
	
	public void unlockRunner();
	
	public void unlockSRunner();
	
	public void unlockCRunner();
	
	public void unlockAHunter();
	
	public void unlockHunter();
	
	public void unlockBombKiller();
	
	public void unlockStockRefill();
	
	public void unlockPower();
	
	public void unlockMaster();
}