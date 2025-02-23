package cli;

import java.util.*;

import java.io.*;
import static util.UserInput.*;
import pkg.Block;
import pkg.Board;
import solver.BruteForce;
import util.FileParser;
import util.SaveData;

import static util.Const.*;

public class MainApp {
	public static void main(String[] args) {
		while (true){
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nSelamat datang di IQ PUZZLER PRO APP");
			System.out.println("Masukkan cara kamu memasukkan data:");
			System.out.println("1. Melalui file");
			System.out.println("2. Keluar");
			// Debugging Purpose
			System.out.print("-> ");
			int choice = getChoice(1,2);
			switch (choice) {
				case 1:
					try {
						System.out.println("Melalui file");
						String filename;
						filename = scanner.nextLine();
						FileParser fileParser = new FileParser();
						fileParser.parseFile(filename);
						Board board = new Board(fileParser.getBoardRows(), fileParser.getBoardCols(), fileParser.getCaseType());
						List<Block> blockList = fileParser.getListBlocks();

						// Solver
						BruteForce bf = new BruteForce(board, blockList);
						Board solvedBoard = bf.solve();

						// Save data
						System.out.println("Apakah mau di saved? (1 (yes) / 0 (no)");
						System.out.print("-> ");
						int choice2 = getChoice(1,2);

						switch (choice2) {
							case 1:
								System.out.println("Save file name: ");
								System.out.print("-> ");
								String fileSave;
								fileSave = scanner.nextLine();
								SaveData saveData = new SaveData(fileSave, solvedBoard);
								SaveData.save();
								break;
							case 2:
								break;
							default:
								break;
						}
					}
					catch (Exception e) {
						System.out.println("\n" + RED + e.getMessage() + RESET);
					}

					break;
				case 2:
					System.exit(0);
					break;
			}
		}

	}
}
