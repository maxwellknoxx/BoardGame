package com.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

@Controller
public class GameControll {

	Map<String, Integer> pits = new HashMap<String, Integer>();

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

	public void prepareToMove(String hand, String pit) {
		String position = hand + pit;

		if (hand.equals("A")) {
			moveHandA(position, Integer.parseInt(pit), false);
		} else {
			moveHandB(position, Integer.parseInt(pit), false);
		}
	}

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
					int stonesInThisPosition = pits.get(position);
					pits.put(position, stonesInThisPosition + 1);
					System.out.println("Position: " + position + " " + pits.get(position));
					moveHandB("B1", stones, true);
					return;
				}

			}
		}

	}

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
					int stonesInThisPosition = pits.get(position);
					pits.put(position, stonesInThisPosition + 1);
					System.out.println("Position: " + position + " " + pits.get(position));
					moveHandA("A7", stones, true);
					return;
				}

			}
		}

	}

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

}
