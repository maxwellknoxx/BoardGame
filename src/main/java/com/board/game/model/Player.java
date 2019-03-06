package com.board.game.model;

import java.util.ArrayList;

public class Player {

	private String playerName;
	private String handName;
	private boolean isPlaying;
	private boolean noStones;
	private boolean winner;
	private ArrayList<Pit> hand;
	private int bigPit;
	private int positionToStart;
	private String lastPosition;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getHandName() {
		return handName;
	}

	public void setHandName(String handName) {
		this.handName = handName;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public boolean isNoStones() {
		return noStones;
	}

	public void setNoStones(boolean noStones) {
		this.noStones = noStones;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public ArrayList<Pit> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Pit> hand) {
		this.hand = hand;
	}

	public int getBigPit() {
		return bigPit;
	}

	public void setBigPit(int bigPit) {
		this.bigPit = bigPit;
	}

	public int getPositionToStart() {
		return positionToStart;
	}

	public void setPositionToStart(int positionToStart) {
		this.positionToStart = positionToStart;
	}

	public String getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(String lastPosition) {
		this.lastPosition = lastPosition;
	}

}
