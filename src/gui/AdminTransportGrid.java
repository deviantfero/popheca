package gui;

import dblib.*;
import data.Transport;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class AdminTransportGrid extends GridPane {

	private Scene mainScene;

	private Hyperlink link_back;

	private GridPane container_search;

	private GridPane container_top;
	private Button button_add;
	private Button button_remove;
	private TextField txt_search;
	private ComboBox<String> combo_atribute;
	private Button button_search;
	private Button button_edit;

	private GridPane container_edit;

	private Button button_accept;
	private Button button_cancel;
	private Text lbl_modelotransporte;
	private Text lbl_numpasajeros;
	private Text lbl_idhotel;
	private Text lbl_prectransporte;
	private TextField txt_modelotransporte;
	private TextField txt_numpasajeros;
	private TextField txt_idhotel;
	private TextField txt_prectransporte;

	private ObservableList<Transport> data;
	private TableView<Transport> table_transport;

	public AdminTransportGrid( int width, int height, Stage mainStage, boolean translate ){
		super();
		this.mainScene = new Scene( this, width, height );

		Loader.loadCss( "search.css", this.mainScene );

		this.container_top = new GridPane();
		this.container_top.setHgap( 10 );
		this.container_top.getStyleClass().add( "register_form" );
		this.button_add = new Button( "+" );
		this.button_add.setMinWidth( 40 );
		this.button_add.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				container_search.setVisible( false );
				container_edit.setVisible( true );
				setupAdd( null, translate );
			}
		});

		this.button_remove = new Button( "-" );
		this.button_remove.setMinWidth( 40 );
		this.button_remove.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				Transport temp_transport = null;
				temp_transport = table_transport.getSelectionModel().getSelectedItem();
				if( temp_transport != null )
					SQLTransport.deleteTransport( temp_transport.getCodtransporte() );
				make_search( translate );
			}
		});

		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				Transport hotel_chosen = null;
				hotel_chosen = table_transport.getSelectionModel().getSelectedItem();
				if( hotel_chosen  != null ){
					container_search.setVisible( false );
					container_edit.setVisible( true );
					setupAdd( hotel_chosen, translate );
				}
			}
		});
		this.txt_search = new TextField();
		this.txt_search.setMinWidth( 245 );
		ObservableList<String> opt = 
			FXCollections.observableArrayList( 
					"id",
					"modelo",
					"pasajeros",
					"precio",
					"hotel"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"model",
					"passengers",
					"price",
					"hotel"
			);
		this.combo_atribute = new ComboBox<>();
		if( translate )
			this.combo_atribute.setItems( opteng );
		else
			this.combo_atribute.setItems( opt );
		this.combo_atribute.setMinWidth( 80 );
		this.button_search = new Button( "Buscar" );
		this.button_search.setMinWidth( 80 );
		this.button_search.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				make_search( translate );
			}
		});

		this.container_search = new GridPane();
		this.container_search.setVgap( 10 );

		this.container_edit = new GridPane();
		this.lbl_modelotransporte = new Text("Modelo:");
		this.lbl_modelotransporte.setId( "lbl_register" );
		this.lbl_numpasajeros = new Text("Pasajeros:");
		this.lbl_numpasajeros.setId( "lbl_register" );
		this.lbl_idhotel = new Text("Hotel:");
		this.lbl_idhotel.setId( "lbl_register" );
		this.lbl_prectransporte = new Text("Precio:");
		this.lbl_prectransporte.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_modelotransporte = new TextField();
		this.txt_numpasajeros = new TextField();
		this.txt_prectransporte = new TextField();
		this.txt_idhotel = new TextField();

		if( translate )
			this.link_back = new Hyperlink( "Back" );
		else
			this.link_back = new Hyperlink( "Atras" );
		this.link_back.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				if(translate)
					mainStage.setTitle( "ADMINISTRATOR" );
				else
					mainStage.setTitle( "ADMINISTRADOR" );
				mainStage.setScene( new AdminGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_accept = new Button( "Aceptar" );
		this.button_cancel = new Button( "Cancelar" );
		this.button_cancel.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				container_edit.setVisible( false );
				container_search.setVisible( true );
				make_search( translate );
			}
		});


		table_transport = new TableView<Transport>();
		table_transport.setMaxHeight( 300 );
		table_transport.setMaxWidth( 650 );
		table_transport.setMinWidth( 650 );
		table_transport.setEditable( false );
		this.data = FXCollections.observableArrayList();

		TableColumn<Transport, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 80 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Transport, Integer>( "codtransporte" ));

		TableColumn<Transport, String> column_nombre = new TableColumn<>( "Modelo" );
		column_nombre.setMaxWidth( 150 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<Transport, String>( "modelotransporte" ));

		TableColumn<Transport, Integer> column_lvl = new TableColumn<>( "Pasajeros" );
		column_lvl.setMaxWidth( 80 );
		column_lvl.setCellValueFactory(
				new PropertyValueFactory<Transport, Integer>( "numpasajeros" ));

		TableColumn<Transport, Double> column_price = new TableColumn<>( "Precio" );
		column_price.setMaxWidth( 80 );
		column_price.setCellValueFactory(
				new PropertyValueFactory<Transport, Double>( "prectransporte" ));

		TableColumn<Transport, String> column_hotel = new TableColumn<>( "Hotel" );
		column_hotel.setMinWidth( 180 );
		column_hotel.setCellValueFactory(
				new PropertyValueFactory<Transport, String>("nomhotel"));

		table_transport.setItems( this.data );
		table_transport.getColumns().addAll( column_id, column_nombre, 
				column_lvl, column_price, column_hotel );

		if( translate ) {
			column_nombre.setText( "Name" );
			column_lvl.setText( "Passengers" );
			column_price.setText( "Price" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_transport, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		this.container_edit.add( this.lbl_modelotransporte, 0, 0 );
		this.container_edit.add( this.txt_modelotransporte, 1, 0 );
		this.container_edit.add( this.lbl_numpasajeros, 2, 0 );
		this.container_edit.add( this.txt_numpasajeros, 3, 0 );
		this.container_edit.add( this.lbl_idhotel, 0, 1 );
		this.container_edit.add( this.txt_idhotel, 1, 1 );
		this.container_edit.add( this.lbl_prectransporte, 2, 1);
		this.container_edit.add( this.txt_prectransporte, 3, 1);
		this.container_edit.add( this.button_accept, 0, 2, 2, 1);
		this.container_edit.add( this.button_cancel, 2, 2, 2, 1);
		super.setHalignment( this.button_accept, HPos.CENTER );
		super.setHalignment( this.button_cancel, HPos.CENTER );
		this.container_edit.setVisible( false );
		this.container_edit.setHgap( 10 );
		this.container_edit.setVgap( 10 );
		this.container_edit.getStyleClass().add( "register_form" );

		super.add( this.container_edit, 0, 0 );
		super.add( this.container_search, 0, 0 );
		super.setMargin( this.container_search, new Insets( 140, 0, 0, 100 ) );
		super.setMargin( this.container_edit, new Insets( 140, 100, 0, 100 ) );
		if( translate )
			translate_adminTransport();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminTransport() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_modelotransporte.setText( "Model:" );
		this.lbl_idhotel.setText( "Hotel:" );
		this.lbl_numpasajeros.setText( "Passengers:" );
	}

	public void setupAdd( Transport source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setModelotransporte( txt_modelotransporte.getText() );
						source.setNumpasajeros( Integer.parseInt(txt_numpasajeros.getText()));
						source.setIdhotel( SQLHotel.searchHotelId( txt_idhotel.getText().toLowerCase() ));
						source.setPrectransporte( Double.parseDouble( txt_prectransporte.getText() ));
						SQLTransport.modifyTransport( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_modelotransporte.setText( source.getModelotransporte() );
			txt_numpasajeros.setText( "" + source.getNumpasajeros() );
			txt_idhotel.setText( SQLHotel.searchHotelName(source.getIdhotel() ));
			txt_prectransporte.setText( "" + source.getPrectransporte() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					Transport new_transport = new Transport();
					if( validate_fields() ){
						new_transport.setModelotransporte( txt_modelotransporte.getText() );
						new_transport.setNumpasajeros( Integer.parseInt(txt_numpasajeros.getText()));
						new_transport.setIdhotel( SQLHotel.searchHotelId( txt_idhotel.getText().toLowerCase() ));
						new_transport.setPrectransporte( Double.parseDouble( txt_prectransporte.getText() ));
						SQLTransport.addTransport( new_transport );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_modelotransporte.setText( "" );
			txt_numpasajeros.setText( "" );
			txt_idhotel.setText( "" );
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<Transport> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("hotel")){
				temp_result = SQLTransport.adminSearchTransport( searchstring, 0 );
				for( Transport result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "pasajeros" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "passengers" ) ){
				temp_result = SQLTransport.adminSearchTransport( searchstring, 1 );
				for( Transport result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "modelo" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "model" ) ){
				temp_result = SQLTransport.adminSearchTransport( searchstring, 2 );
				for( Transport result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLTransport.adminSearchTransport( searchstring, 3 );
				for( Transport result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "precio" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "price" ) ){
				temp_result = SQLTransport.adminSearchTransport( searchstring, 4 );
				for( Transport result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if(	!txt_modelotransporte.getText().equals("") &&
			!txt_idhotel.getText().equals("") &&
			!txt_prectransporte.getText().equals("") &&
			!txt_numpasajeros.getText().equals("") )
			returnvalue = true;
		return returnvalue;
	}

}
