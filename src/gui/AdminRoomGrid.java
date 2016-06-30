package gui;

import dblib.*;
import data.Room;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
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


public class AdminRoomGrid extends GridPane {

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
	private Text lbl_maxperson;
	private Text lbl_prchabitacion;
	private Text lbl_nomhotel;
	private TextField txt_maxperson;
	private TextField txt_prchabitacion;
	private TextField txt_nomhotel;
	private TextArea txt_descreng;
	private TextArea txt_descr;

	private ObservableList<Room> data;
	private TableView<Room> table_habitacion;

	public AdminRoomGrid( int width, int height, Stage mainStage, boolean translate ){
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
				Room temp_habitacion = null;
				temp_habitacion = table_habitacion.getSelectionModel().getSelectedItem();
				if( temp_habitacion != null )
					SQLRoom.deleteRoom( temp_habitacion.getCodhabitacion() );
				make_search( translate );
			}
		});

		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				Room hotel_chosen = null;
				hotel_chosen = table_habitacion.getSelectionModel().getSelectedItem();
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
					"precio",
					"hotel",
					"capacidad"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"price",
					"hotel",
					"capacity"
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
		this.lbl_maxperson = new Text("Capacidad:");
		this.lbl_maxperson.setId( "lbl_register" );
		this.lbl_prchabitacion = new Text("Precio:");
		this.lbl_prchabitacion.setId( "lbl_register" );
		this.lbl_nomhotel = new Text("Hotel:");
		this.lbl_nomhotel.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_maxperson = new TextField();
		this.txt_prchabitacion = new TextField();
		this.txt_descr = new TextArea();
		this.txt_descreng = new TextArea();
		this.txt_nomhotel = new TextField();

		if( !translate ){
			this.txt_descreng.setPromptText( "Ingrese descripcion en Ingles");
			this.txt_descr.setPromptText( "Ingrese descripcion en Español");
		}else{
			this.txt_descreng.setPromptText( "Input English description");
			this.txt_descr.setPromptText( "Input Spanish description");
		}

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


		table_habitacion = new TableView<Room>();
		table_habitacion.setMaxHeight( 300 );
		table_habitacion.setMaxWidth( 650 );
		table_habitacion.setMinWidth( 650 );
		table_habitacion.setEditable( false );
		this.data = FXCollections.observableArrayList();
		TableColumn<Room, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 40 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Room, Integer>( "codhabitacion" ));

		TableColumn<Room, Integer> column_cap = new TableColumn<>( "Cap" );
		column_cap.setMaxWidth( 40 );
		column_cap.setCellValueFactory(
				new PropertyValueFactory<Room, Integer>( "maxperson" ));

		TableColumn<Room, String> column_nombre = new TableColumn<>( "Hotel" );
		column_nombre.setMinWidth( 250 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<Room, String>( "nomhotel" ));

		TableColumn<Room, Float> column_prc = new TableColumn<>( "Precio" );
		column_prc.setMaxWidth( 90 );
		column_prc.setCellValueFactory(
				new PropertyValueFactory<Room, Float>( "prchabitacion" ));

		TableColumn<Room, String> column_descr = new TableColumn<>( "Descripcion" );
		column_descr.setMinWidth( 280 );
		if( translate )
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Room, String>("dethabitacioneng" ));
		else
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Room, String>("dethabitacion" ));

		table_habitacion.setItems( this.data );
		table_habitacion.getColumns().addAll( column_id, column_cap, column_nombre, 
				column_prc, column_descr );

		if( translate ) {
			column_descr.setText( "Description" );
			column_prc.setText( "Price" );
			column_nombre.setText( "Name" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_habitacion, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		this.container_edit.add( this.lbl_maxperson, 0, 0 );
		this.container_edit.add( this.txt_maxperson, 1, 0 );
		this.container_edit.add( this.lbl_prchabitacion, 2, 0 );
		this.container_edit.add( this.txt_prchabitacion, 3, 0 );
		this.container_edit.add( this.lbl_nomhotel, 0, 1 );
		this.container_edit.add( this.txt_nomhotel, 1, 1 );
		this.container_edit.add( this.txt_descr, 0, 2, 2, 2 );
		this.container_edit.add( this.txt_descreng, 2, 2, 2, 2 );
		this.container_edit.add( this.button_accept, 0, 4, 2, 1 );
		this.container_edit.add( this.button_cancel, 2, 4, 2, 1 );
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
			translate_adminRoom();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminRoom() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_maxperson.setText( "Name:" );
		this.lbl_nomhotel.setText( "Level:" );
		this.lbl_prchabitacion.setText( "Address:" );
	}

	public void setupAdd( Room source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setMaxperson( Integer.parseInt(txt_maxperson.getText()));
						source.setPrchabitacion( Float.parseFloat(txt_prchabitacion.getText()));
						source.setIdhotel( SQLHotel.searchHotelId(txt_nomhotel.getText().toLowerCase() ));
						System.out.println( "DEBUG::" +  SQLHotel.searchHotelId(txt_nomhotel.getText().toLowerCase() ));
						source.setDethabitacion( txt_descr.getText() );
						source.setDethabitacioneng( txt_descreng.getText() );
						SQLRoom.modifyRoom( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_maxperson.setText( "" + source.getMaxperson() );
			txt_prchabitacion.setText( "" + source.getPrchabitacion() );
			txt_nomhotel.setText( source.getNomhotel() );
			txt_descr.setText( source.getDethabitacion() );
			txt_descreng.setText( source.getDethabitacioneng() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					Room new_hotel = new Room();
					if( validate_fields() ){
						new_hotel.setMaxperson( Integer.parseInt(txt_maxperson.getText()));
						new_hotel.setPrchabitacion( Float.parseFloat(txt_prchabitacion.getText()));
						new_hotel.setIdhotel( SQLHotel.searchHotelId(txt_nomhotel.getText().toLowerCase() ));
						new_hotel.setDethabitacion( txt_descr.getText() );
						new_hotel.setDethabitacioneng( txt_descreng.getText() );
						SQLRoom.addRoom( new_hotel );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_maxperson.setText( "" );
			txt_prchabitacion.setText( "" );
			txt_nomhotel.setText( "" );
			txt_descr.setText( "" );
			txt_descreng.setText( "" );
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<Room> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("precio") ||
				combo_atribute.getValue().toLowerCase().equals("price")){
				temp_result = SQLRoom.adminSearchRoom( searchstring, translate, 0 );
				for( Room result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "hotel" ) ){
				temp_result = SQLRoom.adminSearchRoom( searchstring, translate, 1 );
				for( Room result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "capacidad" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "capacity" ) ){
				temp_result = SQLRoom.adminSearchRoom( searchstring, translate, 2 );
				for( Room result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLRoom.adminSearchRoom( searchstring, translate, 3 );
				for( Room result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if( !txt_descr.getText().equals("") &&
			!txt_maxperson.getText().equals("") && txt_maxperson.getText().matches( "[1-3]" ) &&
			!txt_nomhotel.getText().equals("") &&
			!txt_descr.getText().equals("") && !txt_descreng.getText().equals("") )
			returnvalue = true;
		return returnvalue;
	}

}
