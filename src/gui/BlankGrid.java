package gui;

import gui.proc.Loader;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class BlankGrid extends GridPane {
	private Scene mainScene;

	public BlankGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "login.css", this.mainScene );
		mainStage.setScene( this.mainScene );
		mainStage.show();
	}
}
