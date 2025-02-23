package util;

import pkg.Block;
import java.io.*;
import java.util.*;

public class FileParser {
	private int boardRows;
	private int boardCols;
	private int numBlocks;
	private String caseType;
	private List<Block> listBlocks;

	// Constructor
	public FileParser() {
		listBlocks = new ArrayList<>();
	}

	// Getter
	public int getBoardRows() { return boardRows; }
	public int getBoardCols() { return boardCols; }
	public String getCaseType() { return caseType; }
	public List<Block> getListBlocks() { return listBlocks; }

	public void parseFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			List<String> lines = new ArrayList<>();
			String line;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();

			String[] firstLine = lines.get(0).split("\\s+");
			boardRows = Integer.parseInt(firstLine[0]);
			boardCols = Integer.parseInt(firstLine[1]);
			numBlocks = Integer.parseInt(firstLine[2]);
			caseType = lines.get(1);

			if (caseType.equals("CUSTOM") || caseType.equals("PYRAMID")) {
				System.out.println("On progress");
				throw new IllegalArgumentException("CUSTOM & PYRAMID not validate :<");
			}

			if(boardRows == 0 || boardCols == 0) {
				throw new IllegalArgumentException("Invalid board rows OR cols argument");
			}

			if(numBlocks == 0) {
				throw new IllegalArgumentException("Invalid number of blocks argument");
			}

			if (!caseType.equals("DEFAULT")) {
				throw new IllegalArgumentException("Invalid case type argument");
			}

			List<String[]> tempList = new ArrayList<>();
			Character prevChar = null;
			int i = 2;

			while (i < lines.size()) {
				line = lines.get(i);

				if (line.trim().isEmpty()) {
					i++;
					continue;
				}

				String trimmedLine = line.trim();
				char currentChar = trimmedLine.charAt(0);

				Set<Character> uniqueChars = new HashSet<>();
				for (char c : trimmedLine.toCharArray()) {
					if (c != ' ') {
						uniqueChars.add(c);
					}
				}

				if (uniqueChars.size() > 1) {
					throw new IllegalArgumentException("Line " + (i + 1) + " contains multiple characters: " + line);
				}

				if (prevChar != null && prevChar != currentChar) {
					if (!tempList.isEmpty()) {
						createAndStoreBlock(tempList, prevChar);
						tempList.clear();
					}
				}

				tempList.add(line.split(""));

				prevChar = currentChar;
				i++;
			}

			if (!tempList.isEmpty()) {
				createAndStoreBlock(tempList, prevChar);
			}

			if (listBlocks.size() != numBlocks) {
				System.out.println("Total Blocks : " + listBlocks.size());
				System.out.println("Read Blocks TXT : " + numBlocks);
				throw new IllegalArgumentException("Invalid number of blocks argument");
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + fileName);
			throw new IllegalArgumentException("File not found");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createAndStoreBlock(List<String[]> tempList, char blockId) {
		int maxRows = tempList.size();
		int maxCols = 0;

		// Find max columns
		for (String[] row : tempList) {
			maxCols = Math.max(maxCols, row.length);
		}

		char[][] blockData = new char[maxRows][maxCols];

		//	Init the blockData
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				blockData[i][j] = '.';
			}
		}

		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < tempList.get(i).length; j++) {
				if(tempList.get(i)[j].charAt(0) == ' '){
					blockData[i][j] = '.';
				}else {
					blockData[i][j] = tempList.get(i)[j].charAt(0);
				}
			}
		}

		// Create Block and store
		Block block = new Block(maxRows, maxCols, blockId);
		block.setAllData(blockData);
		listBlocks.add(block);
	}

	// Utilities
	public void printParsedData() {
		System.out.println("Board Size: " + boardRows + "x" + boardCols);
		System.out.println("Case Type: " + caseType);
		System.out.println("Blocks:");
		for (Block block : listBlocks) {
			block.printBlock();
		}
	}
	public String getParsedDataString() {
		StringBuilder output = new StringBuilder();

		output.append("Board Size: ").append(boardRows).append("x").append(boardCols).append("\n");
		output.append("Case Type: ").append(caseType).append("\n");
		output.append("Blocks:\n");

		for (Block block : listBlocks) {
			output.append(block.getBlockString()).append("\n"); // Ensure Block has getBlockString()
		}

		return output.toString();
	}

}
