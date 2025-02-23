package pkg;

import java.io.*;
import java.util.*;
import static util.Const.*;

public class Block {
	private char id;
	private char [][] data;
	private int rows;
	private int cols;

	//	Constructor
	public Block (int rows, int cols, char id) {
		this.rows = rows;
		this.cols = cols;
		this.id = id;
		this.data = new char[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = '.';
			}
		}
	}

	public Block(Block other) {
		this.id = other.id;
		this.rows = other.rows;
		this.cols = other.cols;
		this.data = new char[rows][cols];

		for (int i = 0; i < rows; i++) {
			System.arraycopy(other.data[i], 0, this.data[i], 0, cols);
		}
	}


	//	Getter
	public char getId() { return id;}
	public char getData(int i , int j) { return data[i][j]; }
	public char[][] getAllData() { return data; }
	public int getRows() { return rows; }
	public int getCols() { return cols; }

	//	Setter
	public void setId(char id) { this.id = id;}
	public void setAllData(char[][] data) {
		if(data.length == 0 || data[0].length == 0){
			throw new IllegalArgumentException("Block() : Invalid cols and rows!");
		}
		this.rows = data.length;
		this.data = data;
		int maxCols = 0;
		for (char[] row : data) {
			maxCols = Math.max(maxCols, row.length);
		}
		this.cols = maxCols;

	}

	public void setData(int rows, int cols, char value) { this.data[rows][cols] = value; }

	//	Function
	public void rotateBlock(){
		char[][] newData = new char[cols][rows];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				newData[j][rows - 1 - i] = data[i][j];
			}
		}
		this.data = newData;
		this.rows = newData.length;
		this.cols = newData[0].length;
	}

	public Block getRotateBlock(){
		char[][] newData = new char[cols][rows];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				newData[j][rows - 1 - i] = data[i][j];
			}
		}
		Block newBlock = new Block(rows, cols, id);
		newBlock.setAllData(newData);
		return newBlock;
	}

	// Prototype
	public void rotateBlock45() {
		int newSize = rows + cols - 1;

		double oldCenterX = (cols - 1) / 2.0;
		double oldCenterY = (rows - 1) / 2.0;

		double offsetX = (newSize - 1) / 2.0;
		double offsetY = (newSize - 1) / 2.0;

		char[][] newData =  new char[newSize][newSize];

		// Init matrix
		for(int i = 0; i < newSize; i++){
			for(int j = 0; j < newSize; j++){
				newData[i][j] = '.';
			}
		}

		for(int i = 0 ; i < rows; i++ ){
			for(int j = 0 ; j < cols; j++){
				// Kali titik dengan matrix rotasi
				if(data[i][j] == '.') continue;

				// Ubah index menjadi koordinat
				double X = j - oldCenterX;
				double Y = oldCenterY - i;

				// Ubah koordinat menjadi index
				int tempX = (int) Math.round(MatrixRotation45[0][0] * X + MatrixRotation45[0][1] * Y + offsetX);
				int tempY = (int) Math.round(MatrixRotation45[1][0] * X + MatrixRotation45[1][1] * Y + offsetY);
				newData[tempX][tempY] = data[i][j];
			}
		}
		this.cols = newSize;
		this.rows = newSize;
		this.data = newData;
	}

	public void mirrorBlock(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols/2; j++){
				char temp = data[i][j];
				data[i][j] = data[i][cols -j -1];
				data[i][cols-j-1] = temp;
			}
		}
	}

	public Block getMirrorBlock(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols/2; j++){
				char temp = data[i][j];
				data[i][j] = data[i][cols -j -1];
				data[i][cols-j-1] = temp;
			}
		}
		Block newBlock = new Block(rows, cols, id);
		newBlock.setAllData(data);
		return newBlock;
	}

	//	Utilities
	public void printBlock(){
		System.out.println("Block ID: " + id);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	public String getBlockString() {
		StringBuilder output = new StringBuilder();

		output.append("Block ID: ").append(id).append("\n");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				output.append(data[i][j]).append(" ");
			}
			output.append("\n");
		}

		return output.toString();
	}
}
