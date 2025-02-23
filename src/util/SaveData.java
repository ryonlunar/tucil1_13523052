package util;

import pkg.Board;
import pkg.Board.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveData {
	private static String fileName;
	private static Board board;
	public SaveData(String filename, Board board) {
		this.fileName = filename;
		this.board = board;
	}

	public static void save(){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			char[][] boardData = board.getAllData();
			for (char[] row : boardData) {
				writer.write(row);
				writer.newLine();
			}
			System.out.println("Data saved to " + fileName);
		} catch (IOException e) {
			System.err.println("Error saving file: " + e.getMessage());
		}
	}
}
