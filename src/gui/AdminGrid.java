package gui;

import dblib.*;
import data.User;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
	private Button button_manageStates;
	private Button button_manageHotels;
	private Button button_manageRooms;
	private Button button_manageTransports;
	private Button button_managePlans;
	private Button button_manageDestiny;
	private Button button_search;
	private Text lbl_welcomeMessage;
	private boolean translate;

	public AdminGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.translate = translate;
		this.frame = new GridPane();
		this.frame.setVgap( 10 );
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "adminwin.css", this.mainScene );
		this.frame.getStyleClass().add( "register_form" );
		this.translate = translate;
		this.activeUser = SQLUser.getActive();

		this.button_manageUsers = new Button( "Editar usuarios" );
		this.button_manageUsers.setMinWidth( 150 );
		this.button_manageUsers.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to user corner" );
				if( translate )
					mainStage.setTitle( "ADMIN USER" );
				else
					mainStage.setTitle( "ADMIN USUARIO" );
				mainStage.setScene( new AdminUserGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_manageStates = new Button( "Editar estados" );
		this.button_manageStates.setMinWidth( 150 );
		this.button_manageStates.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to state corner" );
				mainStage.setScene( new AdminStateGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_manageHotels = new Button( "Editar hoteles" );
		this.button_manageHotels.setMinWidth( 150 );
		this.button_manageHotels.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to hotel corner" );
				mainStage.setTitle( "ADMIN HOTEL" );
				mainStage.setScene( new AdminHotelGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_manageRooms = new Button( "Editar habitaciones" );
		this.button_manageRooms.setMinWidth( 150 );
		this.button_manageRooms.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to room corner" );
				mainStage.setScene( new AdminRoomGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_manageTransports = new Button( "Editar transportes" );
		this.button_manageTransports.setMinWidth( 150 );
		this.button_manageTransports.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to transports corner" );
				mainStage.setScene( new AdminTransportGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_managePlans = new Button( "Editar plan alimenticio" );
		this.button_managePlans.setMinWidth( 150 );
		this.button_managePlans.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to food corner" );
				mainStage.setScene( new AdminFoodGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_manageDestiny = new Button( "Editar destinos" );
		this.button_manageDestiny.setMinWidth( 150 );
		this.button_manageDestiny.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Go to destiny corner" );
				mainStage.setScene( new AdminDestinyGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_search = new Button( "Salir" );
		this.button_search.setMinWidth( 150 );
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
		this.lbl_welcomeMessage.setId( "lbl_welcome" );

		this.frame.add( this.button_manageUsers, 0, 0 );
		this.frame.add( this.button_manageStates, 0, 1 );
		this.frame.add( this.button_manageHotels, 0, 2 );
		this.frame.add( this.button_manageRooms, 0, 3 );
		this.frame.add( this.button_manageTransports, 0, 4 );
		this.frame.add( this.button_managePlans, 0, 5 );
		this.frame.add( this.button_manageDestiny, 0, 6 );
		this.frame.add( this.button_search, 0, 7 );
		super.add( this.lbl_welcomeMessage, 0, 0 );
		super.setMargin( this.lbl_welcomeMessage, new Insets( 200, 0, 0, 10 ) );
		super.setHalignment( this.lbl_welcomeMessage, HPos.CENTER );
		this.lbl_welcomeMessage.setTextAlignment( TextAlignment.CENTER );
		super.add( this.frame, 0, 1 );
		super.setMargin( this.frame, new Insets( 10, 300, 100, 320 ));

		if( this.translate )
			this.translate_admin();

	}

	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_admin() {
		this.button_search.setText( "Exit" );
		this.button_manageUsers.setText( "Edit users" );
		this.button_manageStates.setText( "Edit states" );
		this.button_manageHotels.setText( "Edit hotels" );
		this.button_manageRooms.setText( "Edit rooms" );
		this.button_manageDestiny.setText( "Edit destinies" );
		this.button_manageTransports.setText( "Edit transports" );
		this.button_manageTransports.setText( "Edit foodplans" );
		this.lbl_welcomeMessage.setText( "Welcome" );
	}
}
