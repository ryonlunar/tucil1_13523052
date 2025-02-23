package solver;

import pkg.*;
import java.util.*;
import java.io.*;

public class BruteForce {
	private Board board;
	private List<Block> listBlocks;
	private int totalCase = 0;
	private long startTime;
	private long endTime;

	public BruteForce(Board board, List<Block> listBlocks) {
		this.board = board;
		this.listBlocks = listBlocks;
	}

	// Getter
	public int getTotalCase() {
		return totalCase;
	}

	public double getTime() {
		return endTime - startTime;
	}

	public Board solve() {
		System.out.println("Menggunakan algoritma Brute Force...");
		startTime = System.currentTimeMillis();
		int currentBlockIndex = 0;

		if(!checkBoxAndArea()){
			System.out.println("Total kotak tiap Block tidak sama dengan area Board");
			return null;
		}

		boolean result = tryAllPossibility(currentBlockIndex);
		endTime = System.currentTimeMillis();
		System.out.println("Waktu pencarian : " + (endTime - startTime) + " ms");
		System.out.println("Banyak kasus yang ditinjau : "+ totalCase);

		if (result) {
			System.out.println("Solusi ditemukan!");
			board.showBoard();
			return board;
		} else {
			System.out.println("Solusi tidak ditemukan!");
			board.showBoard();
			return null;
		}
	}

	public boolean tryAllPossibility(int blockIndex){
		// Jika semua block sudah ditempatkan
		if(board.isBoardSolved()){
			return true;
		}

		if(blockIndex >= listBlocks.size()){
			return false;
		}

				System.out.println("Test ke: " + totalCase);
				board.showBoard();
				System.out.println();

		Block currentBlock = listBlocks.get(blockIndex);
		Block originalState = currentBlock;
		for(int t = 0; t < 8 ; t++){
			for(int i = 0; i <= board.getRowsCount() - currentBlock.getRows(); i++){
				for(int j = 0; j <= board.getColsCount() - currentBlock.getCols(); j++){
					// Cek 8 transformasi 4 rotasi biasa dan 4 rotasi yang dicerminkan
						if(isValidPlace(currentBlock, i,j)){
							totalCase++;
							placeBlock(currentBlock, i, j);
							// Secara rekursif tempatkan blok lagi
							if(tryAllPossibility(blockIndex+1)){
								return true;
							}
							// Jika salah, hapus block sebelumnya
							removeBlock(currentBlock, i, j);
						}
					}
				}
			if(t == 3){
				currentBlock.mirrorBlock();
			}else {
				currentBlock.rotateBlock();
			}
		}

		currentBlock.setAllData(originalState.getAllData());
		listBlocks.set(blockIndex, currentBlock);
		return false;
	}

	public boolean isValidPlace(Block block, int startRow, int startCol) {
		for(int i = 0; i < block.getRows(); i++){
			for(int j = 0; j < block.getCols(); j++){
				if (block.getData(i,j) == '.') continue;

				if (startRow + i >= board.getRowsCount() || startCol + j >= board.getColsCount()) return false;

				if (board.getData(startRow + i, startCol + j) != '.') return false;
			}
		}
		return true;
	}

	public void placeBlock(Block block, int startRow, int startCol) {
		char value = block.getId();
		for(int i = 0; i < block.getRows(); i++){
			for(int j = 0; j < block.getCols(); j++){
				if (block.getData(i,j) != '.') {
					board.setData(i + startRow, j + startCol, value);
				}
			}
		}
	}

	public void removeBlock(Block block, int startRow, int startCol) {
		for(int i = 0; i < block.getRows(); i++){
			for(int j = 0; j < block.getCols(); j++){
				if (block.getData(i,j) != '.') {
					board.setData(i + startRow, j + startCol, '.');
				}
			}
		}

	}

	public boolean checkBoxAndArea(){
		int boardArea = board.getColsCount() * board.getRowsCount();
		int blockTiles = 0;
		for(int i = 0; i < listBlocks.size(); i++){
			Block currentBlock = listBlocks.get(i);
			for(int k = 0; k < currentBlock.getCols(); k++){
				for(int j = 0; j < currentBlock.getRows(); j++){
					if(currentBlock.getData(j,k) != '.'){
						blockTiles++;
					}
				}
			}
		}
		System.out.println("Board Area: " + boardArea);
		System.out.println("Block Tiles: " + blockTiles);
		return blockTiles == boardArea;
	}
}
