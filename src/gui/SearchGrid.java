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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SearchGrid extends GridPane {
	private Scene mainScene;
	private GridPane top;

	private Text lbl_activeUser;
	private TextField txt_state;
	private TextField txt_amount;
	private DatePicker date_in;
	private DatePicker date_out;
	private Button button_search;
	private ComboBox<String> combo_amount;
	private ComboBox<String> combo_amountkid;
	private User activeUser;

	private GridPane searchTerms;

	private Text lbl_indate;
	private Text lbl_outdate;
	private Text lbl_state;
	private Text lbl_people;
	private Text lbl_kids;
	private Button button_back;

	private ScrollPane resultscontainer;

	private GridPane results;
	private ArrayList<HotelGrid> resultGrids;

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
		this.results.setVgap( 10 );
		this.resultscontainer = new ScrollPane();
		this.resultscontainer.getStyleClass().add( "myscrollpane" );
		this.resultscontainer.setVisible( false );
		this.resultscontainer.setContent( this.results );
		this.resultscontainer.setHbarPolicy( ScrollBarPolicy.NEVER );
		this.resultscontainer.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		this.resultscontainer.setMinHeight( 300 );

		this.searchTerms = new GridPane();
		ColumnConstraints column = new ColumnConstraints( 320 );
		this.searchTerms.getColumnConstraints().add( column );
		this.searchTerms.setHgap( 10 );
		this.searchTerms.setVisible( false );
		this.searchTerms.getStyleClass().add( "search_terms" );
		this.init_searchterms();

		this.translate = translate;
		this.lbl_activeUser = new Text( "" );
		this.lbl_activeUser.getStyleClass().add( "welcome_message" );
		this.lbl_activeUser.setTextAlignment( TextAlignment.CENTER );
		activeUser = SQLInteractor.getActive();
		if( activeUser != null )
			this.lbl_activeUser.setText( "Bienvenid@, " + activeUser.getName() + " " + activeUser.getLastname() );
		this.txt_state = new TextField();
		this.txt_state.setPromptText( "Estado" );
		this.txt_state.setMinWidth( 380 );
		
		this.txt_amount = new TextField();
		this.txt_amount.setMaxWidth( 100 );

		this.date_in = new DatePicker();
		this.date_in.setPromptText( "Ingreso - mm/dd/aaaa" );
		this.date_in.minWidth( 180 );
		this.date_out = new DatePicker();
		this.date_out.setPromptText( "Salida - mm/dd/aaaa" );
		this.date_out.minWidth( 180 );

		ObservableList<String> options = FXCollections.observableArrayList( "1", "2", "3", "4", "5+" );
		ObservableList<String> optionskids = FXCollections.observableArrayList( "0", "1", "2", "3", "4", "5+" );
		this.combo_amount = new ComboBox<String>( options );
		this.combo_amount.setMinWidth( 188 );
		this.combo_amount.setPromptText( "Personas" );
		this.combo_amountkid = new ComboBox<String>( optionskids );
		this.combo_amountkid.setPromptText( "Niños" );
		this.combo_amountkid.setMinWidth( 180 );

		this.button_search = new Button( "Buscar" );
		this.button_search.setMinWidth( 104 );
		this.button_search.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				int i = 0;
				change_searchterms();
				top.setVisible( false );
				searchTerms.setVisible( true );
				resultGrids = SQLInteractor.searchHotel( txt_state.getText(), translate );
				for( HotelGrid hotel : resultGrids ) {
					results.add( hotel, 0, i );
					i++;
				}
				resultscontainer.setVisible( true );
			}
		});

		this.top.add( this.lbl_activeUser, 0, 0, 4, 1 );
		this.top.add( this.txt_state, 0, 1, 4, 1 );
		this.top.add( this.date_in, 0, 2, 2, 1 );
		this.top.add( this.date_out, 2, 2, 2, 1 );
		this.top.add( this.combo_amount, 0, 3, 2, 1 );
		this.top.add( this.combo_amountkid, 2, 3, 2, 1 );
		this.top.add( this.button_search, 0, 4, 4, 1 );
		super.setHalignment( this.button_search, HPos.CENTER );
		super.setHalignment( this.lbl_activeUser, HPos.CENTER );
		super.setHalignment( this.top, HPos.CENTER );
		super.setHalignment( this.searchTerms, HPos.CENTER );
		super.setHalignment( this.resultscontainer, HPos.CENTER );
		super.setMargin( this.lbl_activeUser, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.txt_state, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.date_in, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.combo_amount, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.button_search, new Insets( 0, 0, 0, 210 ) );
		super.add( this.top, 0, 0 );
		super.add( this.searchTerms, 0, 1 );
		super.add( this.resultscontainer, 0, 2 );
		super.setMargin( this.resultscontainer, new Insets( -90, 420, 20, 100 ) );
		super.setMargin( this.top, new Insets( 120, 0, 0, 0 ) );
		super.setHalignment( this.searchTerms, HPos.CENTER );
		super.setMargin( this.searchTerms, new Insets( -150, 420, 100, 100 ) );
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
		this.combo_amountkid.setPromptText( "Children" );
		this.date_in.setPromptText( "In - dd/mm/yyyy" );
		this.date_out.setPromptText( "Out - dd/mm/yyyy" );
	}

	private void init_searchterms(){
		GridPane searchTermsContainer = new GridPane();
		searchTermsContainer.setHgap( 15 );
		ColumnConstraints column = new ColumnConstraints( 94 );
		searchTermsContainer.getColumnConstraints().add( column );
		this.lbl_outdate = new Text();
		this.lbl_indate = new Text();
		this.lbl_people = new Text();
		this.lbl_kids = new Text();
		this.lbl_state = new Text();
		this.lbl_state.setId( "search_termstxt" );
		this.button_back = new Button( "⬅" );
		this.button_back.setMinWidth( 120 );
		searchTermsContainer.add( lbl_state, 0, 0 );
		searchTermsContainer.add( lbl_indate, 1, 0 );
		searchTermsContainer.add( lbl_outdate, 2, 0 );
		searchTermsContainer.add( lbl_people, 3, 0 );
		searchTermsContainer.add( lbl_kids, 4, 0 );
		searchTermsContainer.add( button_back, 5, 0 );
		GridPane.setMargin( this.button_back, new Insets( 0, 0, 0, 30 ) );
		this.searchTerms.add( searchTermsContainer, 0, 0 );
		GridPane.setMargin( searchTermsContainer, new Insets( 0, 0, 0, 30 ) );
		this.button_back.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				top.setVisible( true );
				searchTerms.setVisible( false );
				resultscontainer.setVisible( false );
				results.getChildren().clear();
			}
		});
	}

	private void change_searchterms() {

		if( this.date_in.getValue() == null )
			this.date_in.setValue( LocalDate.now().plusDays(7) );
		if( this.date_out.getValue() == null )
			this.date_out.setValue( this.date_in.getValue().plusDays(7) );
		if( this.combo_amount.getValue() == null )
			this.combo_amount.setValue( "1" );
		if( this.combo_amountkid.getValue() == null )
			this.combo_amountkid.setValue( "0" );
		if( this.txt_state.getText() == null || this.txt_state.getText().length() > 15 )
			this.txt_state.setText( "Any" );

		if( translate ){
			this.lbl_indate.setText( "From: " + this.date_in.getValue() );
			this.lbl_outdate.setText(  "To: " + this.date_out.getValue() );
			this.lbl_people.setText( this.combo_amount.getValue() + " P" );
			this.lbl_kids.setText( this.combo_amountkid.getValue() + " K" );
			this.lbl_state.setText( this.txt_state.getText() );
		}else{
			this.lbl_indate.setText( "Desde: " + this.date_in.getValue() );
			this.lbl_outdate.setText(  "Hasta: " + this.date_out.getValue() );
			this.lbl_people.setText( this.combo_amount.getValue() + " P" );
			this.lbl_kids.setText( this.combo_amountkid.getValue() + " N" );
			this.lbl_state.setText( this.txt_state.getText() );
		}
	}

}
