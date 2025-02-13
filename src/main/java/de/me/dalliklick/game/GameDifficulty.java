package de.me.dalliklick.game;

public enum GameDifficulty {

	EASY(12f),
	MEDIUM(8f),
	HARD(5f);

	private final float percentage;

	GameDifficulty(float percentage) {
		this.percentage = percentage / 100;
	}

	public float getPercentage() {
		return this.percentage;
	}
}