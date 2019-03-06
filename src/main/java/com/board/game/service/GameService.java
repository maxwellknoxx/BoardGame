package com.board.game.service;

import com.board.game.model.Log;
import com.board.game.model.Player;

public class GameService {

	private Log log = new Log();

	/**
	 *  Checks whether the player is still in the same pit
	 * @param hand Current hand
	 * @param index Current index
	 * @param player Who is playing
	 * @return True or False
	 */
	public boolean isSamePit(String hand, int index, Player player) {
		if (player.getLastPosition().equals(hand + Integer.toString(index))) {
			return true;
		}
		return false;
	}

	/**
	 *  Checks whether the pit is empty and collect opposite stones 
	 * @param player1 Player 1
	 * @param player2 Player 2
	 * @param index Current index
	 * @throws Exception
	 */
	public void isPitEmpty(Player player1, Player player2, int index) throws Exception {
		if (player1.isPlaying()) {
			if (player1.getHand().get(index).getStones() == 0) {
				collectOppositeStones(player1, player2, index);
			}
		} else {
			if (player2.getHand().get(index).getStones() == 0) {
				collectOppositeStones(player1, player2, index);
			}
		}
	}

	/**
	 *  Collects the stones from the opposite side
	 * @param player1 Player 1
	 * @param player2 Player 2
	 * @param index Current index
	 * @throws Exception
	 */
	public void collectOppositeStones(Player player1, Player player2, int index) throws Exception {
		if (player1.isPlaying()) {
			if (player1.getHand().get(index).isEmpty()) {
				player1.getHand().get(index).addCollectedStones(player2.getHand().get(index).collectAllStones());
			}
		} else {
			if (player2.getHand().get(index).isEmpty()) {
				player2.getHand().get(index).addCollectedStones(player1.getHand().get(index).collectAllStones());
			}
		}
	}

	/**
	 * Checks whether any of both side has run out of stones
	 * 
	 * @param p1 Player 1
	 * @param p2 Player 2
	 * @return True or False 
	 */
	private boolean isEndGame(Player p1, Player p2) {
		if (p1.getHand().get(1).getStones() == 0 && p1.getHand().get(2).getStones() == 0
				&& p1.getHand().get(3).getStones() == 0 && p1.getHand().get(4).getStones() == 0
				&& p1.getHand().get(5).getStones() == 0 && p1.getHand().get(6).getStones() == 0) {
			p1.setNoStones(true);
			return true;
		} else if (p2.getHand().get(0).getStones() == 0 && p2.getHand().get(1).getStones() == 0
				&& p2.getHand().get(2).getStones() == 0 && p2.getHand().get(3).getStones() == 0
				&& p2.getHand().get(4).getStones() == 0 && p2.getHand().get(5).getStones() == 0) {
			p2.setNoStones(true);
			return true;
		}
		return false;
	}

	/**
	 * Checks whether is a game over and declare the winner
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean isGameOver(Player p1, Player p2) {
		if (isEndGame(p1, p2)) {
			if (p1.isNoStones()) {
				collectStonesToBigPit(p2);
			} else {
				collectStonesToBigPit(p1);
			}
			if (p1.getHand().get(0).getStones() > p2.getHand().get(7).getStones()) {
				p1.setWinner(true);
			} else {
				p2.setWinner(true);
			}
			return true;
		}
		return false;
	}

	/**
	 * Collects all stones left to the big pit
	 * 
	 * @param player Who is playing
	 */
	public void collectStonesToBigPit(Player player) {
		int totalStones = 0;
		if (player.getHandName().equals("A")) {
			totalStones += player.getHand().get(1).getStones() + player.getHand().get(2).getStones()
					+ player.getHand().get(3).getStones() + player.getHand().get(4).getStones()
					+ player.getHand().get(5).getStones() + player.getHand().get(6).getStones();
			player.getHand().get(0).addCollectedStones(totalStones);
		} else {
			totalStones += player.getHand().get(0).getStones() + player.getHand().get(1).getStones()
					+ player.getHand().get(2).getStones() + player.getHand().get(3).getStones()
					+ player.getHand().get(4).getStones() + player.getHand().get(5).getStones();
			player.getHand().get(7).addCollectedStones(totalStones);
		}
	}

	/**
	 *  Moves the stones among the pits
	 * @param player Who is playing
	 * @param index Which pit has been chosen
	 * @return Number of stones left
	 */
	public int move(Player player, int index) {
		int stones = 0;
		int indexStop = player.getBigPit();

		stones = player.getHand().get(index).collectAllStones();
		
		index = verifyIndex(index);
		while (stones > 0) {
			player.getHand().get(index).addStone();
			stones--;
			index = returnNextIndex(player, index);
			log.createLog(player, player.getHandName(), index, stones);
			if (index == indexStop) {
				player.getHand().get(index).addStone();
				stones--;
				player.setLastPosition(player.getHand().get(index).getPosition());
				log.createLog(player, player.getHandName(), index, stones);
				return stones;
			}
		}
		player.setLastPosition(player.getHand().get(index).getPosition());
		log.createLog(player, player.getHandName(), index, stones);
		return 0;
	}

	/**
	 *  Moves the stones to the pits from the other side when there are stones left
	 * @param player Who is playing
	 * @param stones Quantity of stones left
	 */
	public void moveToOtherPit(Player player, int stones) {
		int index = player.getPositionToStart();

		while (stones > 0) {
			player.getHand().get(index).addStone();
			stones--;
			index = returnNextIndex(player, index);
			log.createLog(player, player.getHandName(), index, stones);
		}
		player.setLastPosition(player.getHand().get(index).getPosition());
		log.createLog(player, player.getHandName(), index, stones);
	}

	/**
	 *  Decides which is the next index according with who is playing
	 * @param p Who is playing
	 * @param index Index at the moment
	 * @return Correct index
	 */
	public int returnNextIndex(Player p, int index) {
		if (p.getHandName().equals("A")) {
			index = index - 1;
			return index;
		}
		index = index + 1;
		return index;
	}

	/**
	 *  Verifies if the index is correct to avoid Null Pointer
	 * @param index Current index
	 * @return The properly index
	 */
	public int verifyIndex(int index) {
		if (index != 0) {
			index = index - 1;
		} else if (index == 0) {
			index = index + 1;
		}
		return index;
	}

}
