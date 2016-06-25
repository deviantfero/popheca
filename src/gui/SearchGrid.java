package gui;

import dblib.*;
import data.User;
import data.Reserve;
import data.HotelChecker;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SearchGrid extends GridPane {
	private Scene mainScene;
	private GridPane top;
	private Reserve message;

	private Text lbl_activeUser;
	private TextField txt_state;
	private DatePicker date_in;
	private DatePicker date_out;
	private Button button_search;
	private TextField txt_amount;
	private TextField txt_amountkid;
	private Text lbl_error;
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
		this.lbl_error = new Text();
		this.lbl_error.getStyleClass().add( "lbl_error" );
		this.lbl_activeUser = new Text( "" );
		this.lbl_activeUser.getStyleClass().add( "welcome_message" );
		this.lbl_activeUser.setTextAlignment( TextAlignment.CENTER );
		activeUser = SQLUser.getActive();
		if( activeUser != null )
			this.lbl_activeUser.setText( "Bienvenid@, " + activeUser.getName() + " " + activeUser.getLastname() );
		this.txt_state = new TextField();
		this.txt_state.setPromptText( "Estado" );
		this.txt_state.setMinWidth( 380 );
		
		this.date_in = new DatePicker();
		this.date_in.setPromptText( "Ingreso - mm/dd/aaaa" );
		this.date_in.minWidth( 180 );
		this.date_out = new DatePicker();
		this.date_out.setPromptText( "Salida - mm/dd/aaaa" );
		this.date_out.minWidth( 180 );

		this.txt_amount = new TextField();
		this.txt_amount.setMinWidth( 188 );
		this.txt_amount.setPromptText( "Personas" );
		this.txt_amountkid = new TextField();
		this.txt_amountkid.setPromptText( "Niños" );
		this.txt_amountkid.setMinWidth( 180 );

		this.button_search = new Button( "Buscar" );
		this.button_search.setMinWidth( 104 );
		this.button_search.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				action_searchHotel();
			}
		});

		this.top.add( this.lbl_activeUser, 0, 0, 4, 1 );
		this.top.add( this.txt_state, 0, 1, 4, 1 );
		this.top.add( this.date_in, 0, 2, 2, 1 );
		this.top.add( this.date_out, 2, 2, 2, 1 );
		this.top.add( this.txt_amount, 0, 3, 2, 1 );
		this.top.add( this.txt_amountkid, 2, 3, 2, 1 );
		this.top.add( this.button_search, 0, 4, 4, 1 );
		super.setHalignment( this.button_search, HPos.CENTER );
		super.setHalignment( this.lbl_activeUser, HPos.CENTER );
		super.setHalignment( this.top, HPos.CENTER );
		super.setHalignment( this.searchTerms, HPos.CENTER );
		super.setHalignment( this.resultscontainer, HPos.CENTER );
		super.setMargin( this.lbl_activeUser, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.txt_state, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.date_in, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.txt_amount, new Insets( 0, 0, 0, 210 ) );
		super.setMargin( this.button_search, new Insets( 0, 0, 0, 210 ) );
		super.add( this.top, 0, 0 );
		super.add( this.searchTerms, 0, 1 );
		super.add( this.resultscontainer, 0, 2 );
		super.add( this.lbl_error, 0, 2 );
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
		this.txt_amount.setPromptText( "People" );
		this.txt_amountkid.setPromptText( "Children" );
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
		if( this.txt_amount.getText().equals("") )
			this.txt_amount.setText( "1" );
		if( this.txt_amountkid.getText().equals("") )
			this.txt_amountkid.setText( "0" );
		if( this.txt_state.getText().equals("") || this.txt_state.getText().length() > 15 )
			this.txt_state.setText( "Any" );

		if( translate ){
			this.lbl_indate.setText( "From: " + this.date_in.getValue() );
			this.lbl_outdate.setText(  "To: " + this.date_out.getValue() );
			this.lbl_people.setText( this.txt_amount.getText() + " P" );
			this.lbl_kids.setText( this.txt_amountkid.getText() + " K" );
		}else{
			this.lbl_indate.setText( "Desde: " + this.date_in.getValue() );
			this.lbl_outdate.setText(  "Hasta: " + this.date_out.getValue() );
			this.lbl_people.setText( this.txt_amount.getText() + " P" );
			this.lbl_kids.setText( this.txt_amountkid.getText() + " N" );
		}
		String stateNow = this.txt_state.getText();
		stateNow = stateNow.substring( 0, 1 ).toUpperCase() + stateNow.substring( 1 ).toLowerCase();
		this.lbl_state.setText( stateNow );
	}

	private boolean validate_dates() {
		LocalDate date1 = this.date_in.getValue();
		LocalDate date2 = this.date_out.getValue();
		if( date1.compareTo(LocalDate.now().plusDays(7)) < 0 ) {
			if( translate )
				this.lbl_error.setText( "Reserve with a week of anticipation" );
			else
				this.lbl_error.setText( "Reserve una fecha con una semana de anticipacion" );
			return false;
		}
		else if( date2.compareTo(date1) < 0  ) {
			if( translate )
				this.lbl_error.setText( "Out date is before In date" );
			else
				this.lbl_error.setText( "Fecha de salida es antes de fecha de entrada" );
			return false;
		}
		else if( date2.compareTo(date1) == 0) {
			if( translate )
				this.lbl_error.setText( "Same In and Out date" );
			else
				this.lbl_error.setText( "Misma fecha de entrada y salida" );
			return false;
		}else if( date2.compareTo(date1.plusDays(31)) > 0 ) {
			if( translate )
				this.lbl_error.setText( "Reserve time exceeded" );
			else
				this.lbl_error.setText( "Tiempo de reserva excedido" );
			return false;
		}else
			return true;
	}

	private boolean validate_amounts() {
		if( this.txt_amount.getText().matches("[0-9]*") && 
			this.txt_amountkid.getText().matches("[0-9]*") ) {

			int adults = Integer.parseInt( this.txt_amount.getText() );
			int children = Integer.parseInt( this.txt_amountkid.getText() );
			if( adults + children > 10 ) {
				if( translate )
					this.txt_amount.setText( "10 people limit" );
				else
					this.txt_amount.setText( "cantidad maxima 10 personas" );
				return false;
			}else
				return true;
		}else{
			return false;
		}
	}

	private void validate_location( HotelGrid hotel, ArrayList<HotelChecker> userStays ) {
		for( HotelChecker check : userStays ){
			if( date_in.getValue().compareTo( check.getIn_date() ) >= 0 && date_in.getValue().compareTo( check.getOut_date() ) <= 0 ){
				if( check.getId_hotel() != hotel.getHotel_id() )
					hotel.getButton_select().setDisable( true );
			}else if( date_out.getValue().compareTo( check.getIn_date() ) >= 0 && date_out.getValue().compareTo( check.getOut_date()) <= 0 ){
				System.out.println( "" + check.getId_hotel() + hotel.getHotel_id() );
				if( check.getId_hotel() != hotel.getHotel_id() )
					hotel.getButton_select().setDisable( true );
			}
		}
	}

	public void action_searchHotel() {
		int i = 0;
		change_searchterms();
		message = new Reserve( date_in, date_out );
		message.setKids(Integer.parseInt( txt_amountkid.getText() ));
		message.setAdults(Integer.parseInt( txt_amount.getText() ));
		message.setIdState( SQLInteractor.getStateID( txt_state.getText() ) );
		if( validate_dates() && validate_amounts() ){
			resultGrids = SQLHotel.searchHotel( txt_state.getText(), translate );
			ArrayList<HotelChecker> userStays = SQLHotel.getUserStays( activeUser.getId() );
			for( HotelGrid hotel : resultGrids ) {
				validate_location( hotel, userStays );
				hotel.getButton_select().setOnAction( new EventHandler<ActionEvent>() {
					@Override
					public void handle( ActionEvent e ) {
						action_searchRoom( hotel );
					}
				});
				results.add( hotel, 0, i );
				i++;
			}
			lbl_error.setVisible( false );
			top.setVisible( false );
			searchTerms.setVisible( true );
			resultscontainer.setVisible( true );
		}else{
			lbl_error.setVisible( true );
			System.err.println( "wrong date range" );
		}
	}

	public void action_searchRoom( HotelGrid hotel ) {
		int j = 0;
		if( true ){
			System.out.println( hotel.getTxt_hname() );
			results.getChildren().clear();
			ArrayList<RoomGrid> RoomList = SQLRoom.searchRoom( hotel.getTxt_hname(),
					translate, txt_state.getText(), message );
			for( RoomGrid room : RoomList ) {
				results.add( room, 0, j );
				j++;
			}
		}
	}

}
