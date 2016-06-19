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

public class AdminGrid extends GridPane {
	private GridPane frame;
	private Scene mainScene;

	private User activeUser;
	private Button button_manageUsers;
	private Button button_search;
	private Text lbl_welcomeMessage;
	private boolean translate;

	public AdminGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.frame = new GridPane();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "adminwin.css", this.mainScene );
		this.frame.getStyleClass().add( "frame" );
		this.translate = translate;
		this.activeUser = SQLInteractor.getActive();
		this.button_manageUsers = new Button( "Admin corner" );
		this.button_manageUsers.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to admin corner" );
			}
		});
		this.button_search = new Button( "Search" );
		this.button_search.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				if( translate ) 
					mainStage.setTitle( "Search" );
				else
					mainStage.setTitle( "Busqueda" );
				mainStage.setScene( new SearchGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});
		this.lbl_welcomeMessage = new Text( "Bienvenido" );

		this.frame.add( this.button_manageUsers, 0, 0 );
		this.frame.add( this.button_search, 0, 1 );
		super.add( this.lbl_welcomeMessage, 0, 0 );
		super.setHalignment( this.lbl_welcomeMessage, HPos.CENTER );
		this.lbl_welcomeMessage.setTextAlignment( TextAlignment.CENTER );
		super.add( this.frame, 0, 1 );
		super.setMargin( this.frame, new Insets( 100, 100, 100, 100 ));

		mainStage.setScene( this.mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}
}
