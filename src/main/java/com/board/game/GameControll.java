package com.board.game;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameControll {

	Map<String, Integer> pits = new HashMap<String, Integer>();

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

		System.out.println("--------------------");
		System.out.println("Size: " + pits.size());
		for (String key : pits.keySet()) {
			Integer value = pits.get(key);
			System.out.println("Key = " + key + ", Value = " + value);
		}
		System.out.println("--------------------");

	}

	/**
	 * Controll the game
	 * 
	 * @param hand
	 * @param pit
	 */
	@PostMapping("/prepareToMove")
	public void prepareToMove(@RequestParam("hand") String hand) {
		String[] handAndPit = hand.split(";");

		String currentHand = handAndPit[0];
		int pit = Integer.parseInt(handAndPit[1]);

		String position = currentHand + Integer.toString(pit);
		initializePits();
		if (currentHand.equals("A")) {
			moveHandA(position, pit, false);
		} else {
			moveHandB(position, pit, false);
		}

	}

	/**
	 * Move the stones through the pits
	 * 
	 * @param position
	 * @param iterator
	 * @param isCalledByHandB
	 */
	public void moveHandA(String position, int iterator, boolean isCalledByHandB) {
		int stones = 0;

		if (isCalledByHandB) {
			int index = 7;
			for (int i = 0; i <= iterator; i++) {
				position = "A" + Integer.toString(index);
				int stonesInThisPosition = pits.get(position);
				pits.put(position, stonesInThisPosition + 1);
				System.out.println("Position: " + position + " " + pits.get(position));
				index--;
			}
		} else {
			for (int i = iterator; i > 0; i--) {
				position = "A" + i;
				if (!position.equals("A1")) {
					if (stones == 0) {
						stones = pits.get(position);
						pits.put(position, 0);
						System.out.println("Position: " + position + " " + pits.get(position));
					} else {
						int stonesInThisPosition = pits.get(position);
						pits.put(position, stonesInThisPosition + 1);
						System.out.println("Position: " + position + " " + pits.get(position));
					}
					System.out.println(stones);
					stones--;
				} else {
					if (stones > 0) {
						int stonesInThisPosition = pits.get(position);
						pits.put(position, stonesInThisPosition + 1);
						stones--;
						System.out.println("Position: " + position + " " + pits.get(position));

						moveHandB("B1", stones, true);
					}
					return;
				}

			}
		}

	}

	/**
	 * Move the stones through the pits
	 * 
	 * @param position
	 * @param iterator
	 * @param isCalledByHandA
	 */
	public void moveHandB(String position, int iterator, boolean isCalledByHandA) {
		int stones = 0;
		if (isCalledByHandA) {
			int index = 1;
			for (int i = 0; i <= iterator; i++) {
				position = "B" + Integer.toString(index);
				int stonesInThisPosition = pits.get(position);
				pits.put(position, stonesInThisPosition + 1);
				System.out.println("Position: " + position + " " + pits.get(position));
				index++;
			}
		} else {
			for (int i = iterator; i > 0; i++) {
				position = "B" + i;
				if (!position.equals("B8")) {
					if (stones == 0) {
						stones = pits.get(position);
						pits.put(position, 0);
						System.out.println("Position: " + position + " " + pits.get(position));
					} else {
						int stonesInThisPosition = pits.get(position);
						pits.put(position, stonesInThisPosition + 1);
						System.out.println("Position: " + position + " " + pits.get(position));
					}
					System.out.println(stones);
					stones--;
				} else {
					if (stones > 0) {
						int stonesInThisPosition = pits.get(position);
						pits.put(position, stonesInThisPosition + 1);
						stones--;
						System.out.println("Position: " + position + " " + pits.get(position));
						moveHandA("A7", stones, true);
					}
					return;
				}
			}
			checkPosition("B", position);
		}
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
			player = "Player 2";
		} else {
			currentPosition = "B";
			oppositePosition = "A";
			player = "Player 1";
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
		if (whoCalls.equals("A") && position.contains("A")) {
			return "Player 1";
		}
		return "Player 2";
	}

	/**
	 * Verify if is game over
	 * 
	 * @return
	 */
	public Boolean endGame() {
		if (pits.get("A2") == 0 && pits.get("A3") == 0 && pits.get("A4") == 0 && pits.get("A5") == 0
				&& pits.get("A6") == 0 && pits.get("A7") == 0) {
			return true;
		} else if (pits.get("B1") == 0 && pits.get("B2") == 0 && pits.get("B3") == 0 && pits.get("B4") == 0
				&& pits.get("B5") == 0 && pits.get("B6") == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Create the board at the start of the game
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String startDrawBoard() {
		StringBuilder draw = new StringBuilder();

		draw.append("-----------------").append("\n");
		draw.append("Player 1").append("\n");
		draw.append("Pits").append("\n");
		draw.append("A1 - A2 - A3 - A4 - A5 - A6 - A7");
		draw.append("(0) - [6] [6] [6] [6] [6] [6]").append("\n");
		draw.append("<----------").append("\n");
		draw.append("Player 2").append("\n");
		draw.append("Pits").append("\n");
		draw.append("B1 - B2 - B3 - B4 - B5 - B6 - B7");
		draw.append("[6] [6] [6] [6] [6] [6] - (0)").append("\n");
		draw.append("---------->").append("\n");
		draw.append("-----------------").append("\n");

		return draw.toString();
	}

	/**
	 * Create the board every time while playing
	 * 
	 * @return
	 */
	public String drawBoardPlaying() {
		StringBuilder draw = new StringBuilder();

		draw.append("-----------------").append("\n");
		draw.append("Player 1").append("\n");
		draw.append("Pits").append("\n");
		draw.append("A1 A2 A3 A4 A5 A6 A7").append("\n");
		draw.append("(").append(pits.get("A1")).append(") [").append(pits.get("A2")).append("] [")
				.append(pits.get("A3")).append("] [").append(pits.get("A4")).append("] [").append(pits.get("A5"))
				.append("] [").append(pits.get("A6")).append("] [").append(pits.get("A7")).append("] \n");
		draw.append("<----------").append("\n");
		draw.append("Player 2").append("\n");
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
