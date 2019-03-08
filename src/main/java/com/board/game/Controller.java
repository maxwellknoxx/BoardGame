package com.board.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.game.model.Player;

@org.springframework.stereotype.Controller
public class Controller {

	Board boardGame = new Board();
	Player p = new Player();

	/**
	 * Move the stones in the pits
	 * 
	 * @param hand which hand and index is playing
	 */
	@PostMapping("/move")
	public ModelAndView move(@RequestParam("hand") String handAndIndex) {
		ModelAndView mv = new ModelAndView("/Index");

		String[] auxHandIndex = handAndIndex.split(";");

		String hand = auxHandIndex[0];
		int index = Integer.parseInt(auxHandIndex[1]);

		p = boardGame.move(hand, index);

		if (boardGame.isEndGame()) {
			if (boardGame.isGameOver())
				mv.addObject("message", boardGame.getWinner() + " is the winner");
		}

		mv.addObject("player1", drawBoardPlayingA());
		mv.addObject("player2", drawBoardPlayingB());
		mv.addObject("message", "Player " + p.getHandName() + " turn");

		return mv;
	}

	/**
	 * Create the board at the start of the game
	 * 
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView startBoard() {
		ModelAndView mv = new ModelAndView("/Index");
		boardGame.initialize();
		return mv;
	}

	/**
	 * Restart the game
	 * 
	 * @return
	 */
	@PostMapping("/restart")
	public ModelAndView restart() {
		ModelAndView mv = new ModelAndView("/Index");
		boardGame.initialize();
		return mv;
	}

	/**
	 * Create the board every time while playing
	 * 
	 * @return
	 */
	public String drawBoardPlayingA() {
		StringBuilder draw = new StringBuilder();
		Player player1 = boardGame.updatePlayer("A");
		draw.append("(").append(player1.getHand().get(0).getStones()).append(") [")
				.append(player1.getHand().get(1).getStones()).append("] [").append(player1.getHand().get(2).getStones())
				.append("] [").append(player1.getHand().get(3).getStones()).append("] [")
				.append(player1.getHand().get(4).getStones()).append("] [").append(player1.getHand().get(5).getStones())
				.append("] [").append(player1.getHand().get(6).getStones()).append("] \n");
		return draw.toString();
	}

	/**
	 * Create the board every time while playing
	 * 
	 * @return
	 */
	public String drawBoardPlayingB() {
		StringBuilder draw = new StringBuilder();
		Player player2 = boardGame.updatePlayer("B");
		draw.append("[").append(player2.getHand().get(0).getStones()).append("] [")
				.append(player2.getHand().get(1).getStones()).append("] [").append(player2.getHand().get(2).getStones())
				.append("] [").append(player2.getHand().get(3).getStones()).append("] [")
				.append(player2.getHand().get(4).getStones()).append("] [").append(player2.getHand().get(5).getStones())
				.append("] (").append(player2.getHand().get(6).getStones()).append(") \n");
		return draw.toString();
	}

}
