package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;       // For setting cell colors
import javafx.scene.layout.StackPane;   // For creating grid cells
import javafx.scene.shape.Rectangle;    // For drawing colored blocks
import javafx.scene.layout.GridPane;    // For placing cells in a grid
import javafx.scene.control.Label;      // For displaying text inside cells
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.scene.layout.VBox;


import java.io.File;
import java.io.IOException;

import pkg.Board;
import solver.BruteForce;
import util.FileParser;
import util.SaveData;

import java.io.File;

public class MainController {

	@FXML
	private TextField fileInput;

	@FXML
	private Button showDataButton;

	@FXML
	private Button solveButton;

	@FXML
	private Button saveAsImageButton;

	@FXML
	private Button saveAsTextButton;

	@FXML
	private TextArea textArea;

	@FXML
	private GridPane boardGrid;

	@FXML
	private Label totalCasesLabel, estimationLabel;

	@FXML
	private VBox Vbox;

	@FXML
	private ScrollPane scrollPane;

	private FileParser fileParser; // Store parsed data

	private boolean isSolved = false;

	private Board result;

	@FXML
	private void onBrowseFileButton() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a File");

		// Allow only .txt files
		FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(txtFilter);

		File selectedFile = fileChooser.showOpenDialog(new Stage());

		if (selectedFile != null) {
			fileInput.setText(selectedFile.getName());
			fileInput.setVisible(true);

			System.out.println("Selected File Path: " + selectedFile.getAbsolutePath()); // Debugging

			// Parse the file
			parseFile(selectedFile.getAbsolutePath());


		}
	}

	private void parseFile(String filePath) {
		try {
			fileParser = new FileParser();
			fileParser.parseFile(filePath);

			// Show buttons
			showDataButton.setVisible(true);
			solveButton.setVisible(true);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@FXML
	private void onShowData() {
		if (fileParser != null) {
			textArea.setText(fileParser.getParsedDataString());
			textArea.setEditable(false);
			textArea.setWrapText(true);
			textArea.setVisible(true);
			Vbox.setVisible(false);
			boardGrid.setVisible(false);
			estimationLabel.setVisible(false);
			totalCasesLabel.setVisible(false);
			saveAsImageButton.setVisible(false);
			saveAsTextButton.setVisible(false);
		}
	}

	@FXML
	private void onSolve() {
		if (fileParser != null) {
			System.out.println("Solving...");
			Board board = new Board(fileParser.getBoardRows(), fileParser.getBoardCols(), fileParser.getCaseType());
			BruteForce bf = new BruteForce(board, fileParser.getListBlocks());
			result = bf.solve();
			double time  = bf.getTime();
			int totalCase = bf.getTotalCase();
			if (result != null) {
				boardGrid.getChildren().clear();

				char[][] boardData = result.getAllData();
				int rows = boardData.length;
				int cols = boardData[0].length;

				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						char blockChar = boardData[i][j];
						StackPane cell = createBoardCell(blockChar);
						boardGrid.add(cell, j, i);
					}
				}


				Vbox.setVisible(true);
				scrollPane.setVisible(true);
				// Update total cases & estimation labels
				totalCasesLabel.setText("Total Cases: " + totalCase);
				estimationLabel.setText("Estimated Time: " + time + "ms");

				totalCasesLabel.setAlignment(Pos.CENTER);
				estimationLabel.setAlignment(Pos.CENTER);

				totalCasesLabel.setVisible(true);
				estimationLabel.setVisible(true);
				textArea.setVisible(false);
				scrollPane.setContent(boardGrid);

				boardGrid.setVisible(true);
				isSolved = true;
				saveAsImageButton.setVisible(true);
				saveAsTextButton.setVisible(true);
			} else {
				textArea.setText("There's no Solution!");
			}

		}
	}

	@FXML
	private void onSaveAsImage() {
		if (fileParser != null && isSolved) {
			if (Vbox != null) {

				WritableImage image = boardGrid.snapshot(null, null);

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Image");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png"));
				File file = fileChooser.showSaveDialog(new Stage());

				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
						System.out.println("Image saved successfully: " + file.getAbsolutePath());
					} catch (IOException e) {
						System.out.println("Error saving image: " + e.getMessage());
					}
				}
			}
		}

	}

	@FXML
	private void onSaveAsText() {
		if (fileParser != null && isSolved) {
			String fileName = showSaveDialog();

			if (fileName != null && !fileName.isEmpty()) {
				SaveData saveData = new SaveData(fileName + ".txt", result);
				saveData.save();
				showSaveConfirmation(fileName + ".txt");
			} else {
				System.out.println("File save canceled.");
			}
		}

	}

	@FXML
	private String showSaveDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SaveLoad.fxml"));
			Parent root = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Save File");
			dialogStage.setScene(new Scene(root));
			dialogStage.initModality(Modality.APPLICATION_MODAL);

			// Set the stage reference in the controller
			SaveLoadDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return SaveLoadDialogController.getFileName();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	private void showSaveConfirmation(String filePath) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("File Saved");
		alert.setHeaderText("File has been successfully saved!");
		alert.setContentText("Saved to: " + filePath);
		alert.showAndWait();
	}

	private StackPane createBoardCell(char blockChar) {
		StackPane cell = new StackPane();
		cell.setPrefSize(60, 60);

		Rectangle rect = new Rectangle(60, 60);
		rect.setArcWidth(10);
		rect.setArcHeight(10);
		rect.setFill(getColorForChar(blockChar));
		rect.setStroke(Color.BLACK);

		Label label = new Label(String.valueOf(blockChar));
		label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");

		cell.getChildren().addAll(rect, label);
		return cell;
	}

	private Color getColorForChar(char blockChar) {
		switch (blockChar) {
			case 'A': return Color.DARKBLUE;
			case 'B': return Color.BROWN;
			case 'C': return Color.GREEN;
			case 'D': return Color.ORANGE;
			case 'E': return Color.PURPLE;
			case 'F': return Color.RED;
			case 'G': return Color.DEEPSKYBLUE;
			case 'H': return Color.NAVY;
			case 'I': return Color.YELLOW;
			case 'J': return Color.MAGENTA;
			case 'K': return Color.PINK;
			case 'L': return Color.CRIMSON;
			case 'M': return Color.LIMEGREEN;
			case 'N': return Color.GOLD;
			case 'O': return Color.TEAL;
			case 'P': return Color.CORAL;
			case 'Q': return Color.DARKORCHID;
			case 'R': return Color.SADDLEBROWN;
			case 'S': return Color.SPRINGGREEN;
			case 'T': return Color.INDIGO;
			case 'U': return Color.DARKSLATEGRAY;
			case 'V': return Color.SKYBLUE;
			case 'W': return Color.FIREBRICK;
			case 'X': return Color.DARKGREEN;
			case 'Y': return Color.STEELBLUE;
			case 'Z': return Color.DARKRED;
			default: return Color.LIGHTGRAY;
		}
	}


}