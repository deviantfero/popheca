package gui;

import dblib.SQLUser;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FinishGrid extends GridPane {
	private Scene mainScene;
	private GridPane container;

	private Button button_logut;
	private Button button_goback;
	private Text lbl_exito;

	public FinishGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "register.css", this.mainScene );
		this.container = new GridPane();
		this.container.setVgap( 10 );
		this.container.getStyleClass().add( "register_form" );
		this.button_logut = new Button( "Logout" );
		this.button_logut.setMinWidth( 160 );
		this.button_logut.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				SQLUser.closeUsers();
				mainStage.setTitle( "Login" );
				mainStage.setScene( new LoginGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});
		if( translate )
			this.button_goback = new Button( "Another reservation" );
		else
			this.button_goback = new Button( "Hacer otra reservacion" );
		this.button_goback.setMinWidth( 160 );
		this.button_goback.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				if( translate )
					mainStage.setTitle( "Search" );
				else
					mainStage.setTitle( "Busqueda" );
				mainStage.setScene( new SearchGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		if( translate )
			this.lbl_exito = new Text( "Succesfully made reservation");
		else
			this.lbl_exito = new Text( "Reserva guardada con exito");
		this.lbl_exito.setTextAlignment( TextAlignment.CENTER );
		this.lbl_exito.setId( "lbl_done" );
		super.setHalignment( this.lbl_exito, HPos.CENTER );

		this.container.add( this.button_logut, 0, 0 );
		this.container.add( this.button_goback, 0, 1 );
		super.setMargin( this.container, new Insets( 10, 500, 0, 310 ) );
		super.setMargin( this.lbl_exito, new Insets( 205, 500, 0, 310 ) );
		super.add( this.lbl_exito, 0, 0 );
		super.add( this.container, 0, 1 );
	}

	public Scene getMainScene() {
		return mainScene;
	}
}
