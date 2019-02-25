package com.board.model;

import java.util.ArrayList;

public class Hand {

	private int player;
	private boolean active;
	private int bigPit;
	private ArrayList<Pits> pits = new ArrayList<>();

	public void initializePits() {
		bigPit = 0;
		for (int x = 0; x <= 5; x++) {
			pits.get(0).setPit1(0);
		}
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getBigPit() {
		return bigPit;
	}

	public void setBigPit(int bigPit) {
		this.bigPit = bigPit;
	}

}
