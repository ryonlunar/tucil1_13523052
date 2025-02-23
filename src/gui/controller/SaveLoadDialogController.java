package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveLoadDialogController {

	@FXML
	private TextField fileNameInput;

	@FXML
	private Button saveButton, cancelButton;

	private static String fileName = null;
	private Stage dialogStage;

	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}

	public static String getFileName() {
		return fileName;
	}

	@FXML
	private void onSave() {
		fileName = fileNameInput.getText().trim();
		dialogStage.close();
	}

	@FXML
	private void onCancel() {
		fileName = null;
		dialogStage.close();
	}
}
