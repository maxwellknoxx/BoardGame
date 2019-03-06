package com.board.game.model;

public class Pit {

	private int stones;
	private String position;

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isEmpty() {
		if (this.getStones() == 0) {
			return true;
		}
		return true;
	}

	public int addStone() {
		this.stones = this.stones + 1;
		return this.stones;
	}

	public int collectStone() {
		this.stones = this.stones - 1;
		return this.stones;
	}

	public int collectAllStones() {
		int stonesToCollect = this.stones;
		this.stones = 0;
		return stonesToCollect;
	}

	public int addCollectedStones(int collectedStones) {
		this.stones = this.stones + collectedStones;
		return this.stones;
	}

}
