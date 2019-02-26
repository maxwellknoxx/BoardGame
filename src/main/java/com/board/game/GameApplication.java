package com.board.game;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		/*
		 * GameControll game = new GameControll(); game.startDrawBoard();
		 * 
		 * Scanner sc = new Scanner(System.in);
		 * 
		 * System.out.println("Player 1"); System.out.println("Choose the pit:"); String
		 * position = sc.nextLine();
		 * 
		 * sc.close();
		 * 
		 * String[] handAndPit = position.split(";"); String positionFixed =
		 * position.replaceAll(";", "");
		 * 
		 * 
		 * if (!positionFixed.equals("A1") && !positionFixed.equals("B7")) {
		 * game.initializePits(); game.prepareToMove(handAndPit[0], handAndPit[1]);
		 * System.out.println(game.drawBoardPlaying()); } else {
		 * System.out.println("Posição invalida!"); }
		 */

		SpringApplication.run(GameApplication.class, args);

	}

}
