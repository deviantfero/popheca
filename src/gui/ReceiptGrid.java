package gui;

import dblib.*;
import data.Reserve;
import data.Register;
import data.PDFPrinter;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.DatePicker;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReceiptGrid extends GridPane {
	private int width;
	private int height;
	private double total;
	private Scene mainScene;
	private Reserve message;
	private ObservableList<Register> data;
	private Register temp_register;
	private Register register_food;
	private Register register_transport;
	private Register register_destiny;
	private ArrayList<FoodGrid> result_food;
	private ArrayList<DestinyGrid> result_destiny;
	private ArrayList<TransportGrid> result_transport;
	private boolean translate;

	private GridPane container;

	private Text lbl_header;
	private DatePicker datein_destiny;
	private GridPane container_button;
	private Button button_accept;
	private Button button_cancel;
	private Button button_food;
	private Button button_transport; 
	private Button button_destiny; 
	private Button button_back; 
	private TableView<Register> table_receipt;
	private GridPane container_date_button;

	private ScrollPane resultscontainer;
	private GridPane results;


	public ReceiptGrid( Stage mainStage, boolean translate, Reserve message ) {
		super();
		this.message = message;
		this.translate = translate;
		this.register_food = null;
		this.register_transport = null;
		this.register_destiny = null;

		super.setVgap( 10 );
		super.setHgap( 10 );
		this.container = new GridPane();
		this.container.setVgap( 10 );
		this.container.setHgap( 10 );
		this.container_button = new GridPane();
		this.container_button.setHgap( 10 );
		this.container_date_button = new GridPane();
		this.container_date_button.setHgap( 10 );
		this.container_date_button.setVisible( false );
		super.setHalignment( this.container_date_button, HPos.CENTER );
		super.setMargin( this.container_date_button, new Insets( -30, 0, 0, 250 ) );

		this.results = new GridPane();
		this.results.setVgap( 10 );
		this.resultscontainer = new ScrollPane();
		this.resultscontainer.getStyleClass().add( "myscrollpane" );
		this.resultscontainer.setVisible( false );
		this.resultscontainer.setContent( this.results );
		this.resultscontainer.setHbarPolicy( ScrollBarPolicy.NEVER );
		this.resultscontainer.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		this.resultscontainer.setMaxHeight( 300 );

		this.temp_register = this.message.makeRegister( translate );
		this.total = temp_register.getPrice();

		super.setMargin( this.container, new Insets( 10, 0, 0, 100 ) );
		super.setMargin( this.resultscontainer, new Insets( -40, 0, 0, 100 ) );
		super.setMargin( this.results, new Insets( 0, 0, 0, 20 ) );
		super.setHalignment( this.container_button, HPos.CENTER );
		this.width = 850;
		this.height = 600;
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "search.css", this.mainScene );

		if( translate )
			this.lbl_header = new Text( "Choose your plan and confirm the reservation  Total: $" + String.format("%.2f",total) );
		else
			this.lbl_header = new Text( "Elije tus planes y confirma la reserva  Total: $" + String.format("%.2f",total) );
		this.lbl_header.setTextAlignment( TextAlignment.CENTER );
		super.setHalignment( this.lbl_header, HPos.CENTER );
		this.lbl_header.setId( "welcome_message" );

		this.datein_destiny = new DatePicker();
		this.datein_destiny.setPromptText( "Escoje un dia para evento" );
		this.datein_destiny.setDisable( true );

		this.button_cancel = new Button( "Cancelar" );
		this.button_cancel.setMinWidth( 120 );
		this.button_cancel.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				handle_cancel( mainStage );
			}
		});
		
		this.button_food = new Button( "Plan Comida" );
		this.button_food.setMinWidth( 120 );
		this.button_food.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				handle_food();
			}
		});

		this.button_transport = new Button( "Transporte" );
		this.button_transport.setMinWidth( 120 );
		this.button_transport.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				handle_transport();
			}
		});

		this.button_destiny = new Button( "Reservar Entradas" );
		this.button_destiny.setMinWidth( 120 );
		this.button_destiny.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				handle_destiny();
			}
		});

		this.button_accept = new Button( "Aceptar" );
		this.button_accept.setMinWidth( 120 );
		this.button_accept.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				SQLInteractor.makeReservation( message );
				if( register_food != null ){
					SQLInteractor.addFoodReservation( register_food );
					System.out.println( "SQL::Reservacion comida completa" );
				}
				if( register_destiny != null ) {
					SQLInteractor.addDestinyReservation( register_destiny );
					System.out.println( "SQL::Reservacion destino completa" );
				}
				if( register_transport != null ) {
					SQLInteractor.addTransportReservation( register_transport );
					System.out.println( "SQL::Reservacion trasnporte completa" );
				}
				new PDFPrinter( message, translate, 
						register_food, register_transport, register_destiny, temp_register );
				if( translate )
					mainStage.setTitle( "What now?" );
				else
					mainStage.setTitle( "Y ahora?" );
				mainStage.setScene( new FinishGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_back = new Button( "⬅");
		this.button_back.setMinWidth( 120 );
		this.button_back.getStyleClass().add( "button_back" );
		this.container_date_button.setVisible( false );
		this.button_back.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				if( translate )
					lbl_header.setText( "Choose your plan and confirm the reservation  Total: $" + String.format("%.2f",total) );
				else
					lbl_header.setText( "Elije tus planes y confirma la reserva  Total: $" + String.format("%.2f",total) );
				resultscontainer.setVisible( false );
				container_date_button.setVisible( false );
				datein_destiny.setDisable( true );
				container.setVisible( true );
				results.getChildren().clear();
			}
		});


		table_receipt = new TableView<Register>();
		table_receipt.setMaxHeight( 300 );
		table_receipt.setMaxWidth( 650 );
		table_receipt.setMinWidth( 650 );
		table_receipt.setEditable( false );
		this.data = FXCollections.observableArrayList( this.temp_register );

		TableColumn<Register, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Register, Integer>( "itemid" ));

		TableColumn<Register, String> column_item = new TableColumn<>( "Item" );
		column_item.setMinWidth( 100 );
		column_item.setCellValueFactory(
				new PropertyValueFactory<Register, String>( "item" ));

		TableColumn<Register, String> column_desc = new TableColumn<>( "Descripcion" );
		column_desc.setMinWidth( 320 );
		column_desc.setCellValueFactory(
				new PropertyValueFactory<Register, String>( "description" ));

		TableColumn<Register, Integer> column_amount = new TableColumn<>( "Adultos" );
		column_amount.setCellValueFactory(
				new PropertyValueFactory<Register, Integer>("adults" ));

		TableColumn<Register, Integer> column_amountkids = new TableColumn<>( "Niños" );
		column_amountkids.setCellValueFactory(
				new PropertyValueFactory<Register, Integer>("kids"));

		TableColumn<Register, Double> column_price = new TableColumn<>( "Precio" );
		column_price.setCellValueFactory(
				new PropertyValueFactory<Register, Double>("price"));

		table_receipt.setItems( this.data );
		table_receipt.getColumns().addAll( column_id, column_item, 
				column_desc, column_amount, column_amountkids, column_price );

		if( translate ) {
			column_desc.setText( "Description" );
			column_amount.setText( "Adults" );
			column_amountkids.setText( "Kids" );
			column_price.setText( "Price" );
			translate_receipt();
		}

		this.container_button.add( this.button_cancel, 0, 0 );
		this.container_button.add( this.button_food, 1, 0 );
		this.container_button.add( this.button_transport, 2, 0 );
		this.container_button.add( this.button_destiny, 3, 0 );
		this.container_button.add( this.button_accept, 4, 0 );
		this.container.add( this.container_button, 0, 1 );
		this.container.add( this.table_receipt, 0, 0 );
		this.container_date_button.add( this.button_back, 0, 2 );
		this.container_date_button.add( this.datein_destiny, 1, 2 );
		super.add( this.lbl_header, 0, 0 );
		super.setMargin( this.lbl_header, new Insets( 120, 0, 0, 90 ) );
		super.add( this.container, 0, 1 );
		super.add( this.resultscontainer, 0, 1 );
		super.add( this.container_date_button, 0, 2 );
		//super.setHalignment( this.button_back, HPos.CENTER );
	}

	public Reserve getMessage() {
		return message;
	}

	public void setMessage(Reserve message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public ObservableList<Register> getData() {
		return data;
	}

	private void translate_receipt() {
		this.button_cancel.setText( "Cancel" );
		this.button_food.setText( "Food plan" );
		this.button_destiny.setText( "Destiny" );
		this.button_transport.setText( "Choose transport" );
		this.button_accept.setText( "Accept" );
		this.datein_destiny.setPromptText( "Pick a Date for event" );
	}

	public void handle_cancel( Stage mainStage ){
		mainStage.setScene( new SearchGrid( 850, 600, mainStage, translate ).getMainScene() );
	}

	public void handle_food() {
		results.getChildren().clear();
		container.setVisible( false );	
		if( translate )
			lbl_header.setText( "Choos a food plan" );
		else
			lbl_header.setText( "Elije un plan de comida" );
		result_food = SQLFood.searchFood( message.getIdHotel(), translate );
		int i = 0;
		for( FoodGrid food : result_food ) {
			food.getButton_reserve().setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					handle_reserve_food( food );
				}
			});
			results.add( food,  0, i );
			i++;
		}
		resultscontainer.setVisible( true );
		container_date_button.setVisible( true );
	} 

	public void handle_reserve_food( FoodGrid results){
		register_food = results.getFood_register();
		register_food.setAdults( message.getAdults() );
		register_food.setKids( message.getKids() );
		double price_kids = register_food.getKids() * 0.5 * register_food.getPrice();
		register_food.setPrice( register_food.getPrice() * register_food.getAdults() + price_kids );
		data.add( register_food );
		resultscontainer.setVisible( false );
		container.setVisible( true );
		button_food.setDisable( true );
		container_date_button.setVisible( false );
		total += register_food.getPrice();
		if( translate )
			lbl_header.setText( "Choose your plan and confirm the reservation  Total: $" + String.format("%.2f",total) );
		else
			lbl_header.setText( "Elije tus planes y confirma la reserva  Total: $" + String.format("%.2f",total) );
		results.getChildren().clear();
	}

	public void handle_destiny() {
		if( translate )
			lbl_header.setText( "Choose a destination and a date" );
		else
			lbl_header.setText( "Elije un destino y una fecha" );
		results.getChildren().clear();
		container.setVisible( false );	
		result_destiny = SQLDestiny.searchDestiny( message.getIdState(), translate );
		System.out.println( "idstate::"+ message.getIdState() );
		int i = 0;
		for( DestinyGrid destiny : result_destiny ) {
			destiny.getButton_reserve().setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					handle_reserve_destiny( destiny );
				}
			});
			results.add( destiny,  0, i );
			i++;
		}
		resultscontainer.setVisible( true );
		container_date_button.setVisible( true );
		datein_destiny.setDisable( false );
	}

	public void handle_reserve_destiny( DestinyGrid results){
		if( validate_destiny_date() ){
			register_destiny = results.getDestiny_register();
			register_destiny.setIn_date( datein_destiny.getValue() );
			register_destiny.setAdults( message.getAdults() );
			register_destiny.setKids( message.getKids() );
			double price_kids = register_destiny.getKids() * 0.5 * register_destiny.getPrice();
			register_destiny.setPrice( register_destiny.getPrice() * register_destiny.getAdults() + price_kids );
			data.add( register_destiny );
			resultscontainer.setVisible( false );
			container.setVisible( true );
			button_destiny.setDisable( true );
			container_date_button.setVisible( false );
			datein_destiny.setDisable( true );
			total += register_destiny.getPrice();
			if( translate )
				lbl_header.setText( "Choose your plan and confirm the reservation  Total: $" + String.format("%.2f",total) );
			else
				lbl_header.setText( "Elije tus planes y confirma la reserva  Total: $" + String.format("%.2f",total) );
			results.getChildren().clear();
		}else{
			if( translate )
				lbl_header.setText( "Please Choose a valid date" );
			else
				lbl_header.setText( "Elija una fecha valida porfavor" );
		}
	}

	public void handle_transport() {
		if( translate )
			lbl_header.setText( "Choose a transportation method" );
		else
			lbl_header.setText( "Elije un metodo de transporte" );
		results.getChildren().clear();
		container.setVisible( false );	
		result_transport = SQLTransport.searchTransport( message.getIdHotel(), translate, message );
		int i = 0;
		for( TransportGrid transport : result_transport ) {
			transport.getButton_reserve().setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					handle_reserve_transport( transport );
				}
			});
			results.add( transport,  0, i );
			i++;
		}
		resultscontainer.setVisible( true );
		container_date_button.setVisible( true );
	}

	public void handle_reserve_transport( TransportGrid results){
		register_transport = results.getTransport_register();
		register_transport.setAdults( message.getAdults() );
		register_transport.setKids( message.getKids() );
		register_transport.setPrice( register_transport.getPrice() );
		data.add( register_transport );
		resultscontainer.setVisible( false );
		container.setVisible( true );
		button_transport.setDisable( true );
		container_date_button.setVisible( false );
		total += register_transport.getPrice();
		if( translate )
			lbl_header.setText( "Choose your plan and confirm the reservation  Total: $" + String.format("%.2f",total) );
		else
			lbl_header.setText( "Elije tus planes y confirma la reserva  Total: $" + String.format("%.2f",total) );
		results.getChildren().clear();
	}

	private boolean validate_destiny_date() {
		if( datein_destiny.getValue() != null )
			if( datein_destiny.getValue().compareTo(message.getIn_date()) >= 0
				&& datein_destiny.getValue().compareTo(message.getOut_date()) <= 0 )
				return true;
			else
				return false;
		else
			return false;
	}

}
