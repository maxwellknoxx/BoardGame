package com.board.game.model;

public class Log {

	private String playerName;
	private String position;
	private int stones;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	/**
	 *  Creates a log
	 * @param p Current player
	 * @param hand Current hand
	 * @param index Current index
	 * @param stones Quantity of stones
	 */
	public void createLog(Player p, String hand, int index, int stones) {
		System.out.println(p.getPlayerName() + " " + p.getHandName() + " " + p.isPlaying() + " " + p.getLastPosition());
		System.out.println("Hand: " + hand);
		System.out.println("Index: " + index);
		System.out.println("Stones: " + stones);
		System.out.println("---------------------------------------------");
		StringBuilder draw = new StringBuilder();
		draw.append("[").append(p.getHand().get(0).getStones()).append("] [")
				.append(p.getHand().get(1).getStones()).append("] [").append(p.getHand().get(2).getStones())
				.append("] [").append(p.getHand().get(3).getStones()).append("] [")
				.append(p.getHand().get(4).getStones()).append("] [").append(p.getHand().get(5).getStones())
				.append("] [").append(p.getHand().get(6).getStones()).append("] \n");
		System.out.println(draw.toString());
	}
	
	@Override
	public String toString() {
		return "Log [playerName=" + playerName + ", position=" + position + ", stones=" + stones + "]";
	}

}
