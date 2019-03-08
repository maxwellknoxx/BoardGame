package com.board.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.board.game.model.Player;

public class BoardTest {

	Board board = new Board();

	@Before
	public void testInitialize() {
		board.initialize();
	}

	@Test
	public void testGetPlayer() {
		Player player = new Player();
		player = board.getPlayer("A");
		assertNotNull(player);
	}

	@Test
	public void testUpdatePlayer() {
		Player player = board.getPlayer("A");
		player.setLastPosition("A2");
		player = board.updatePlayer("A");
		assertEquals("Updated", "A2", player.getLastPosition());
	}

	@Test
	public void testGetOppositePlayerTurn() {
		Player player = board.getPlayer("A");
		assertEquals("Player", "A", player.getHandName());
		player = board.getOppositePlayerTurn("A");
		assertEquals("Player", "B", player.getHandName());
		assertEquals("IsPlaying", true, player.isPlaying());
	}

	@Test
	public void testGetOppositePlayer() {
		Player player = board.getPlayer("A");
		assertEquals("Player", "A", player.getHandName());
		player = board.getOppositePlayer("A");
		assertEquals("Player", "B", player.getHandName());
	}

	@Test
	public void testGetWinner() {
		Player player = board.getPlayer("A");
		player.setWinner(true);
		assertEquals("Winner", "Player 1", board.getWinner());
	}

	@Test
	public void testIsGameOver() {
		Player player = board.getPlayer("A");
		player.getHand().get(0).setStones(10);
		assertEquals("GameOver", true, board.isGameOver());
	}

	@Test
	public void testMove() {
		Player player = board.move("A", 2);
		player = board.getOppositePlayer("B");
		assertEquals("Stones", 0, player.getHand().get(2).getStones());
	}

	@Test
	public void testNextPlayer() {
		Player player = board.getPlayer("A");
		player.setLastPosition("B2");
		player = board.nextPlayer("A", 3, player);
		assertEquals("Player", "B", player.getHandName());
	}

}
