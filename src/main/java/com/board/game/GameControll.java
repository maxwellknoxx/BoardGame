package com.board.game;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameControll {

	private Map<String, Integer> pits = new HashMap<String, Integer>();

	/**
	 * Initialize the pits
	 */
	public void initializePits() {
		pits.put("A1", 0);
		pits.put("A2", 6);
		pits.put("A3", 6);
		pits.put("A4", 6);
		pits.put("A5", 6);
		pits.put("A6", 6);
		pits.put("A7", 6);

		pits.put("B1", 6);
		pits.put("B2", 6);
		pits.put("B3", 6);
		pits.put("B4", 6);
		pits.put("B5", 6);
		pits.put("B6", 6);
		pits.put("B7", 0);
	}

	/**
	 * Controll the game
	 * 
	 * @param hand
	 * @param pit
	 */
	@PostMapping("/prepareToMove")
	public ModelAndView prepareToMove(@RequestParam("hand") String hand) {
		ModelAndView mv = new ModelAndView("/Index");
		String player = "";

		String[] handAndPit = hand.split(";");

		String currentHand = handAndPit[0];
		int pit = Integer.parseInt(handAndPit[1]);

		String position = currentHand + Integer.toString(pit);

		if (!position.equals("A1") && !position.equals("B7")) {
			if (currentHand.equals("A")) {
				try {
					player = moveHandA(position, pit, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					moveHandB(position, pit, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String playerNoStones = endGame();
			if (playerNoStones.equals("")) {
				mv.addObject("message", "Now is the " + player + " turn");
				mv.addObject("gameBoard", drawBoardPlaying());
			} else {
				moveAllStoneToBigPit(playerNoStones);
				player = verifyWinner();
				mv.addObject("message", player);
			}

		} else {
			mv.addObject("message", "Invalid Position, Try another one!");
		}

		return mv;
	}

	/**
	 * Move the stones through the pits
	 * 
	 * @param position
	 * @param iterator
	 * @param isCalledByHandB
	 */
	public String moveHandA(String position, int iterator, boolean isCalledByHandB) throws Exception {
		int stones = -1;
		boolean firstLoop = true;
		String player = "";

		if (isCalledByHandB) {
			int index = 7;
			for (int i = 0; i <= iterator; i++) {
				position = "A" + Integer.toString(index);
				int stonesInThisPosition = pits.get(position);
				pits.put(position, stonesInThisPosition + 1);
				System.out.println("Position: " + position + " " + pits.get(position));
				index--;
			}
			player = checkPosition("A", position);
			isPitEmpty("A", Integer.toString(index));
		} else {
			for (int i = iterator; i > 0; i--) {
				if (stones != 0) {
					position = "A" + i;
					if (!position.equals("A1")) {
						if (firstLoop) {
							stones = pits.get(position);
							pits.put(position, 0);
							System.out.println("Position: " + position + " " + pits.get(position));
							System.out.println(stones);
							firstLoop = false;
						} else {
							int stonesInThisPosition = pits.get(position);
							pits.put(position, stonesInThisPosition + 1);
							System.out.println("Position: " + position + " " + pits.get(position));
							stones--;
							System.out.println(stones);
						}
					} else {
						if (stones > 0) {
							int stonesInThisPosition = pits.get(position);
							pits.put(position, stonesInThisPosition + 1);
							stones--;
							System.out.println("Position: " + position + " " + pits.get(position));
							if (stones > 0) {
								player = moveHandB("B1", stones, true);
								return player;
							}
						}
						player = checkPosition("A", position);
						isPitEmpty("A", Integer.toString(i));
						return player;
					}
				} else {
					player = checkPosition("A", position);
					isPitEmpty("A", Integer.toString(i));
					break;
				}

			}
		}
		return player;
	}

	/**
	 * Move the stones through the pits
	 * 
	 * @param position
	 * @param iterator
	 * @param isCalledByHandA
	 */
	public String moveHandB(String position, int iterator, boolean isCalledByHandA) throws Exception {
		int stones = -1;
		String player = "";
		boolean firstLoop = true;
		if (isCalledByHandA) {
			int index = 1;
			for (int i = 0; i <= iterator; i++) {
				position = "B" + Integer.toString(index);
				int stonesInThisPosition = pits.get(position);
				pits.put(position, stonesInThisPosition + 1);
				System.out.println("Position: " + position + " " + pits.get(position));
				index++;
			}
			player = checkPosition("B", position);
			isPitEmpty("B", Integer.toString(index));
		} else {
			for (int i = iterator; i > 0; i++) {
				if (stones != 0) {
					position = "B" + i;
					if (!position.equals("B7")) {
						if (firstLoop) {
							stones = pits.get(position);
							pits.put(position, 0);
							System.out.println("Position: " + position + " " + pits.get(position));
							firstLoop = false;
						} else {
							int stonesInThisPosition = pits.get(position);
							pits.put(position, stonesInThisPosition + 1);
							System.out.println("Position: " + position + " " + pits.get(position));
							stones--;
							System.out.println(stones);
						}
					} else {
						if (stones > 0) {
							int stonesInThisPosition = pits.get(position);
							pits.put(position, stonesInThisPosition + 1);
							stones--;
							System.out.println("Position: " + position + " " + pits.get(position));
							if (stones > 0) {
								try {
									player = moveHandA("A7", stones, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
								return player;
							}
						}
						player = checkPosition("B", position);
						isPitEmpty("B", Integer.toString(i));
						return player;
					}
				} else {
					player = checkPosition("B", position);
					isPitEmpty("B", Integer.toString(i));
					break;
				}
			}
		}
		return player;
	}

	/**
	 * Checks if the pit is empty, if yes, captures the opposite stones if not, do
	 * nothing
	 * 
	 * @param hand
	 * @param pit
	 * @return
	 */
	public String isPitEmpty(String hand, String pit) {
		String currentPosition = "";
		String oppositePosition = "";
		String player = "";
		if (hand.equals("A")) {
			currentPosition = "A";
			oppositePosition = "B";
			player = "Player B";
		} else {
			currentPosition = "B";
			oppositePosition = "A";
			player = "Player A";
		}
		if (pits.get(currentPosition + pit) == 0) {
			int stonesFromOtherPit = pits.get(oppositePosition + pit);
			if (stonesFromOtherPit != 0) {
				pits.put(currentPosition + pit, stonesFromOtherPit);
			}
		}

		return player;
	}

	/**
	 * Check the last position to verify whose is the turn
	 * 
	 * @param whoCalls
	 * @param position
	 * @return
	 */
	public String checkPosition(String whoCalls, String position) {
		if (whoCalls.equals("A")) {
			if (position.equals("A1")) {
				return "Player A";
			} else {
				return "Player B";
			}
		} else {
			if (position.equals("B7")) {
				return "Player B";
			} else {
				return "Player A";
			}
		}
	}

	/**
	 * Verify if is game over
	 * 
	 * @return
	 */
	public String endGame() {
		if (pits.get("A2") == 0 && pits.get("A3") == 0 && pits.get("A4") == 0 && pits.get("A5") == 0
				&& pits.get("A6") == 0 && pits.get("A7") == 0) {
			return "Player A";
		} else if (pits.get("B1") == 0 && pits.get("B2") == 0 && pits.get("B3") == 0 && pits.get("B4") == 0
				&& pits.get("B5") == 0 && pits.get("B6") == 0) {
			return "Player B";
		}
		return "";
	}

	/**
	 * Move all stones left to the big pit
	 * 
	 * @param player
	 */
	public void moveAllStoneToBigPit(String player) {
		int stonesCount = 0;
		int bigPit = 0;
		if (player.contains("A")) {
			stonesCount += pits.get("B1") + pits.get("B2") + pits.get("B3") + pits.get("B4") + pits.get("B5")
					+ pits.get("B6");
			bigPit = pits.get("B7");
			pits.put("B7", bigPit + stonesCount);
		} else {
			stonesCount += pits.get("A2") + pits.get("A3") + pits.get("A4") + pits.get("A5") + pits.get("A6")
					+ pits.get("A7");
			bigPit = pits.get("A1");
			pits.put("A1", bigPit + stonesCount);
		}
	}

	/**
	 * Count the stones in the big pit and return the winner
	 * 
	 * @return
	 */
	public String verifyWinner() {
		if (pits.get("A1") > pits.get("B7")) {
			return "Player A is the winner!";
		} else {
			return "Player B is the winner!";
		}
	}

	/**
	 * Create the board at the start of the game
	 * 
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView startDrawBoard() {
		initializePits();
		StringBuilder draw = new StringBuilder();

		ModelAndView mv = new ModelAndView("/Index");

		draw.append("-----------------").append("\n");
		draw.append("Player A").append("\n");
		draw.append("Pits").append("\n");
		draw.append("A1 - A2 - A3 - A4 - A5 - A6 - A7");
		draw.append("(0) - [6] [6] [6] [6] [6] [6]").append("\n");
		draw.append("<----------").append("\n");
		draw.append("Player B").append("\n");
		draw.append("Pits").append("\n");
		draw.append("B1 - B2 - B3 - B4 - B5 - B6 - B7");
		draw.append("[6] [6] [6] [6] [6] [6] - (0)").append("\n");
		draw.append("---------->").append("\n");
		draw.append("-----------------").append("\n");

		mv.addObject("message", "Player 1 starts");
		mv.addObject("rule", "Hand must be separate by ';' ");
		mv.addObject("gameBoard", draw.toString());

		return mv;
	}

	/**
	 * Create the board every time while playing
	 * 
	 * @return
	 */
	public String drawBoardPlaying() {
		StringBuilder draw = new StringBuilder();

		draw.append("-----------------").append("\n");
		draw.append("Player A").append("\n");
		draw.append("Pits").append("\n");
		draw.append("A1 A2 A3 A4 A5 A6 A7").append("\n");
		draw.append("(").append(pits.get("A1")).append(") [").append(pits.get("A2")).append("] [")
				.append(pits.get("A3")).append("] [").append(pits.get("A4")).append("] [").append(pits.get("A5"))
				.append("] [").append(pits.get("A6")).append("] [").append(pits.get("A7")).append("] \n");
		draw.append("<----------").append("\n");
		draw.append("Player B").append("\n");
		draw.append("Pits").append("\n");
		draw.append("B1 B2 B3 B4 B5 B6 B7").append("\n");
		draw.append("[").append(pits.get("B1")).append("] [").append(pits.get("B2")).append("] [")
				.append(pits.get("B3")).append("] [").append(pits.get("B4")).append("] [").append(pits.get("B5"))
				.append("] [").append(pits.get("B6")).append("] (").append(pits.get("B7")).append(") \n");
		draw.append("---------->").append("\n");
		draw.append("-----------------").append("\n");

		return draw.toString();
	}

}
