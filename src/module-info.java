module tucil1 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.swing;
//	requires atlantafx.base;

	opens gui.controller to javafx.fxml;

	exports gui;
	exports app;
}