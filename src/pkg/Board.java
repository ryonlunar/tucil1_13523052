package pkg;

public class Board {
	private char [][] data;
	private int rows;
	private int cols;
	private String caseType;

	//	Constructor
	public Board(int rows, int cols, String caseType) {
		if (rows <= 0 || cols <= 0) {
			throw new IllegalArgumentException("Board(): Invalid board rows and cols!");
		}

		if (caseType.equals("CUSTOM")) {
			System.out.println("On progress");
//			data = new char[rows][cols];

		} else if (caseType.equals("DEFAULT")) {
			this.rows = rows;
			this.cols = cols;
			this.data = new char [rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.data[i][j] = '.';
				}
			}
		}
	}

	//	Getter
	public int getRowsCount() { return rows; }
	public int getColsCount() { return cols; }
	public String getCaseType() { return caseType; }
	public char [] getRow(final int row) {return data[row];}
	public char [] getCol(final int col) {
		char [] colData = new char [this.rows];
		for(int i = 0; i < this.rows; i++) {
			colData[i] = data[i][col];
		}
		return colData;
	}
	public char [][] getAllData() { return data; }
	public char getData(int row, int col) { return data[row][col]; }


	//	Setter
	public void setAllData(char [][] data) {
		if(data.length == 0 || data[0].length == 0) {
			throw new IllegalArgumentException("Board(): Invalid board rows and cols!");
		}

		this.data = data;
		this.rows = data.length;
		this.cols = data[0].length;
	}

	public void setData(int row, int col, char value) { this.data[row][col] = value; }

	//	Show Board
	public void showBoard() {
		System.out.println("Board: ");
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.cols; j++){
				System.out.print(data[i][j]);
			}
			System.out.println();
		}
	}

	//	Solve
	public boolean isBoardSolved() {
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(data[i][j] == '.'){
					return false;
				}
			}
		}
		return true;
	}

	public String getBoardString() {
		StringBuilder output = new StringBuilder();
		output.append("Board:\n");

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				output.append(data[i][j]).append(" ");
			}
			output.append("\n");
		}

		return output.toString();
	}
}
