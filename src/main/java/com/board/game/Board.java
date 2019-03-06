package com.board.game;

import java.util.ArrayList;

import com.board.game.model.Pit;
import com.board.game.model.Player;
import com.board.game.service.GameService;

public class Board {

	private GameService service = new GameService();

	private Player player1 = new Player();
	private Player player2 = new Player();

	ArrayList<Pit> hand1 = new ArrayList<>();
	ArrayList<Pit> hand2 = new ArrayList<>();

	Pit h1p1 = new Pit();
	Pit h1p2 = new Pit();
	Pit h1p3 = new Pit();
	Pit h1p4 = new Pit();
	Pit h1p5 = new Pit();
	Pit h1p6 = new Pit();
	Pit h1p7 = new Pit();

	Pit h2p1 = new Pit();
	Pit h2p2 = new Pit();
	Pit h2p3 = new Pit();
	Pit h2p4 = new Pit();
	Pit h2p5 = new Pit();
	Pit h2p6 = new Pit();
	Pit h2p7 = new Pit();

	/**
	 * Initializes the player, hand and pits
	 */
	public void initialize() {
		player1.setHandName("A");
		player2.setHandName("B");

		player1.setPlayerName("Player 1");
		player2.setPlayerName("Player 2");

		player1.setPositionToStart(6);
		player2.setPositionToStart(0);

		player1.setBigPit(0);
		player2.setBigPit(6);

		h1p1.setPosition("A0");
		h1p2.setPosition("A1");
		h1p3.setPosition("A2");
		h1p4.setPosition("A3");
		h1p5.setPosition("A4");
		h1p6.setPosition("A5");
		h1p7.setPosition("A6");

		hand1.add(h1p1);
		hand1.add(h1p2);
		hand1.add(h1p3);
		hand1.add(h1p4);
		hand1.add(h1p5);
		hand1.add(h1p6);
		hand1.add(h1p7);

		for (int i = 1; i <= 6; i++) {
			hand1.get(i).setStones(6);
		}

		h2p1.setPosition("B0");
		h2p2.setPosition("B1");
		h2p3.setPosition("B2");
		h2p4.setPosition("B3");
		h2p5.setPosition("B4");
		h2p6.setPosition("B5");
		h2p7.setPosition("B6");

		hand2.add(h2p1);
		hand2.add(h2p2);
		hand2.add(h2p3);
		hand2.add(h2p4);
		hand2.add(h2p5);
		hand2.add(h2p6);
		hand2.add(h2p7);

		for (int i = 0; i <= 5; i++) {
			hand2.get(i).setStones(6);
		}

		player1.setHand(hand1);
		player2.setHand(hand2);
	}

	/**
	 *  Returns the object Player and set him as playing
	 * @param player Which player is required
	 * @return Object Player
	 */
	public Player getPlayer(String player) {
		if (player.equals("A")) {
			player1.setPlaying(true);
			player2.setPlaying(false);
			return player1;
		}
		player2.setPlaying(true);
		player1.setPlaying(false);
		return player2;
	}

	/**
	 *  Updates the information in object Player
	 * @param hand Required hand
	 * @return Object Player
	 */
	public Player updatePlayer(String hand) {
		if (hand.equals("A")) {
			return player1;
		}
		return player2;
	}

	/**
	 *  Gets the Player from the opposite hand and set him as playing
	 * @param player Required player
	 * @return Object Player
	 */
	public Player getOppositePlayerTurn(String player) {
		if (player.equals("A")) {
			player2.setPlaying(true);
			player1.setPlaying(false);
			return player2;
		}
		player1.setPlaying(true);
		player2.setPlaying(false);
		return player1;
	}

	/**
	 *  Gets the Player from the opposite hand
	 * @param player Required Player
	 * @return Object Player
	 */
	public Player getOppositePlayer(String player) {
		if (player.equals("A")) {
			return player2;
		}
		return player1;
	}

	/**
	 *  Checks who is the winner
	 * @return The winner
	 */
	public String getWinner() {
		if (isGameOver()) {
			if (player1.isWinner()) {
				return "Player 1";
			} else if (player2.isWinner()) {
				return "Player 2";
			}
		}
		return "";
	}

	/**
	 *  Checks whether is game over
	 * @return True or False
	 */
	public boolean isGameOver() {
		if (service.isGameOver(player1, player2)) {
			return true;
		}
		return false;
	}

	/**
	 *  Moves the stones among the pits
	 * @param hand Current hand
	 * @param index Current index
	 * @return Next Player
	 */
	public Player move(String hand, int index) {
		int stones = service.move(getPlayer(hand), index);
		if (stones != 0) {
			service.moveToOtherPit(getOppositePlayer(hand), stones);
			return nextPlayer(hand, index, getOppositePlayer(hand));
		}
		return nextPlayer(hand, index, getPlayer(hand));
	}

	/**
	 *  Checks who is the next player
	 * @param hand Current hand
	 * @param index Current index
	 * @param player Current player
	 * @return Next Player
	 */
	public Player nextPlayer(String hand, int index, Player player) {
		try {
			if (service.isSamePit(hand, index, player)) {
				service.isPitEmpty(player1, player2, index);
				return getPlayer(hand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getOppositePlayerTurn(hand);
	}

}
