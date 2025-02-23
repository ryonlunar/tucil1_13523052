package util;

import java.util.*;
import java.io.*;
import static util.Const.*;

public class UserInput {

	public static int getChoice(int min, int max) {
		int choice;
		while (true) {
			try {
				Scanner scanner = new Scanner(System.in);
				choice = Integer.parseInt(scanner.nextLine());
				if (choice >= min && choice <= max) {
					break;
				} else {
					System.out.print(YELLOW + "!!!  Invalid choice! Please choose again (" + min + "-" + max + "): " + RESET);
				}
			} catch (NumberFormatException e) {
				System.out.print(YELLOW + "!!!  Not a valid number! Try again: " + RESET);
			} catch (Exception e) {
				System.out.print(RED + "!!!  ERROR! Try again: " + RESET);
			}
		}
		return choice;
	}

	public static String getInput() {
		Scanner scanner = new Scanner(System.in);
		boolean isValid = false;
		String input = "";

		while (!isValid) {
			try {
				input = scanner.nextLine();

				if (input.matches("[A-Z]+") && allSameCharacter(input)) {
					isValid = true;
				} else {
					System.out.println("Invalid input! Please enter only one repeated uppercase letter (e.g., AAA, BBB).");
				}
			} catch (Exception e) {
				System.out.println("Error! Please try again.");
			}
		}

		return input;
	}

	public static boolean allSameCharacter(String input) {
		char firstChar = input.charAt(0);
		for (char c : input.toCharArray()) {
			if (c != firstChar) {
				return false;
			}
		}
		return true;
	}
}
