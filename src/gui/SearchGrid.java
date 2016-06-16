package gui;

import dblib.SQLInteractor;
import data.User;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class SearchGrid extends GridPane {
	private Scene mainScene;
	private Text lbl_activeUser;
	private User activeUser;

	private boolean translate;

	public SearchGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.translate = translate;
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "login.css", this.mainScene );
		this.lbl_activeUser = new Text( "" );
		activeUser = SQLInteractor.getActive();
		if( activeUser != null ) {
			if( this.translate )
				this.lbl_activeUser.setText( "I am: " + activeUser.getName() + activeUser.getRole() );
			else
				this.lbl_activeUser.setText( "Soy: " + activeUser.getName() + activeUser.getRole() );
		}
		super.add( this.lbl_activeUser, 0, 0 );
		mainStage.setScene( this.mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}
}
