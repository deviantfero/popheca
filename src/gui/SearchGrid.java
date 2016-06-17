package gui;

import dblib.SQLInteractor;
import data.User;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchGrid extends GridPane {
	private Scene mainScene;
	private GridPane top;
	private GridPane results;
	private Text lbl_activeUser;
	private TextField txt_state;
	private TextField txt_amount;
	private DatePicker date_in;
	private DatePicker date_out;
	private Button button_search;
	private ComboBox<String> combo_amount;
	private User activeUser;

	private boolean translate;

	public SearchGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "search.css", this.mainScene );
		this.top = new GridPane();
		this.top.setHgap( 10 );
		this.top.setVgap( 10 );
		this.top.getStyleClass().add( "top" );
		this.results = new GridPane();
		this.translate = translate;
		this.lbl_activeUser = new Text( "" );
		this.lbl_activeUser.getStyleClass().add( "welcome_message" );
		this.lbl_activeUser.setTextAlignment( TextAlignment.CENTER );
		activeUser = SQLInteractor.getActive();
		if( activeUser != null )
			this.lbl_activeUser.setText( "Bienvenid@, " + activeUser.getName() + " " + activeUser.getLastname() );
		this.txt_state = new TextField();
		this.txt_state.setPromptText( "Estado" );
		this.txt_amount = new TextField();
		this.txt_amount.setMaxWidth( 100 );
		this.date_in = new DatePicker();
		this.date_in.setPromptText( "Ingreso - mm/dd/aaaa" );
		this.date_out = new DatePicker();
		this.date_out.setPromptText( "Salida - mm/dd/aaaa" );
		ObservableList<String> options = FXCollections.observableArrayList( "1", "2", "3", "4", "5+" );
		this.combo_amount = new ComboBox<String>( options );
		this.combo_amount.setMinWidth( 104 );
		this.combo_amount.setPromptText( "Personas" );
		this.button_search = new Button( "Buscar" );
		this.button_search.setMinWidth( 104 );
		this.button_search.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				//search things
				System.out.println( date_in.getValue() );
				System.out.println( date_out.getValue() );
			}
		});

		this.top.add( this.lbl_activeUser, 0, 0, 3, 1 );
		this.top.add( this.txt_state, 0, 1, 2, 1 );
		this.top.add( this.button_search, 2, 1 );
		this.top.add( this.date_in, 0, 2 );
		this.top.add( this.date_out, 1, 2 );
		this.top.add( this.combo_amount, 2, 2 );
		super.setHalignment( this.lbl_activeUser, HPos.CENTER );
		super.setMargin( this.lbl_activeUser, new Insets( 0, 40, 0, 0 ) );
		super.setMargin( this.txt_state, new Insets( 0, 0, 0, 90 ) );
		super.setMargin( this.date_in, new Insets( 0, 0, 0, 90 ) );
		super.setMargin( this.combo_amount, new Insets( 0, 90, 10, 0 ) );
		super.add( this.top, 0, 0 );
		if( this.translate )
			this.translate_search();
		mainStage.setScene( this.mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_search() {
		this.lbl_activeUser.setText( "Welcome, " + activeUser.getName() + " " + activeUser.getLastname() );
		this.button_search.setText( "Search" );
		this.txt_state.setPromptText( "State" );
		this.combo_amount.setPromptText( "People" );
		this.date_in.setPromptText( "In - dd/mm/yyyy" );
		this.date_out.setPromptText( "Out - dd/mm/yyyy" );
	}
}
