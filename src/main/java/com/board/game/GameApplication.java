package com.board.game;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.board.controller.GameControll;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		String[] handAndPit = "A;2".split(";");
		String position = "A;2".replaceAll(";", "");
		
		System.out.println(position);
		
		
		if (!position.equals("A1") && !position.equals("B7")) {
			GameControll game = new GameControll();
			game.initializePits();
			game.prepareToMove(handAndPit[0], handAndPit[1]);
		} else {
			System.out.println("Posição invalida!");
		}

	}

}
