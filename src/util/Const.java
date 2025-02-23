package util;

import java.math.*;

public class Const {
	public static final int WIDTH = 56;

	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[34m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";
	public static final String PURPLE = "\u001B[35m";

	public static final String RGB = RED + "R" + GREEN + "G" + BLUE + "B" + RESET;
	public static final String ARROW = PURPLE + "->" + RESET;

	public static final double ANGLE_45 = Math.PI / 4.0;
	public static final double[][] MatrixRotation45 = new double[2][2];

	static {
		MatrixRotation45[0][0] = Math.cos(ANGLE_45);
		MatrixRotation45[0][1] = -Math.sin(ANGLE_45);
		MatrixRotation45[1][0] = Math.sin(ANGLE_45);
		MatrixRotation45[1][1] = Math.cos(ANGLE_45);
	}

}
