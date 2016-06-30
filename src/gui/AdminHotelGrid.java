package gui;

import dblib.*;
import data.Hotel;

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


public class AdminHotelGrid extends GridPane {

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
	private Text lbl_nomhotel;
	private Text lbl_direchotel;
	private Text lbl_lvlhotel;
	private Text lbl_estado;
	private TextField txt_nomhotel;
	private TextField txt_direchotel;
	private TextField txt_lvlhotel;
	private TextField txt_estado;
	private TextArea txt_descreng;
	private TextArea txt_descr;

	private ObservableList<Hotel> data;
	private TableView<Hotel> table_hotel;

	public AdminHotelGrid( int width, int height, Stage mainStage, boolean translate ){
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
				Hotel temp_hotel = null;
				temp_hotel = table_hotel.getSelectionModel().getSelectedItem();
				if( temp_hotel != null )
					SQLHotel.deleteHotel( temp_hotel.getIdhotel() );
				make_search( translate );
			}
		});
		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				Hotel hotel_chosen = null;
				hotel_chosen = table_hotel.getSelectionModel().getSelectedItem();
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
					"nombre",
					"lvlhotel",
					"estado"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"name",
					"lvlhotel",
					"state"
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
		this.lbl_nomhotel = new Text("Nombre:");
		this.lbl_nomhotel.setId( "lbl_register" );
		this.lbl_direchotel = new Text("Direccion:");
		this.lbl_direchotel.setId( "lbl_register" );
		this.lbl_lvlhotel = new Text("Nivel:");
		this.lbl_lvlhotel.setId( "lbl_register" );
		this.lbl_estado = new Text("Estado:");
		this.lbl_estado.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_nomhotel = new TextField();
		this.txt_direchotel = new TextField();
		this.txt_descr = new TextArea();
		this.txt_descreng = new TextArea();
		this.txt_lvlhotel = new TextField();
		this.txt_estado = new TextField();

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


		table_hotel = new TableView<Hotel>();
		table_hotel.setMaxHeight( 300 );
		table_hotel.setMaxWidth( 650 );
		table_hotel.setMinWidth( 650 );
		table_hotel.setEditable( false );
		this.data = FXCollections.observableArrayList();
		TableColumn<Hotel, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 40 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Hotel, Integer>( "idhotel" ));

		TableColumn<Hotel, String> column_nombre = new TableColumn<>( "Nombre" );
		column_nombre.setMaxWidth( 150 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<Hotel, String>( "nomhotel" ));

		TableColumn<Hotel, Integer> column_lvl = new TableColumn<>( "Lvl" );
		column_lvl.setMaxWidth( 30 );
		column_lvl.setCellValueFactory(
				new PropertyValueFactory<Hotel, Integer>( "lvlhotel" ));

		TableColumn<Hotel, String> column_descr = new TableColumn<>( "Descripcion" );
		column_descr.setMinWidth( 200 );
		if( translate )
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Hotel, String>("descrhoteleng" ));
		else
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Hotel, String>("descrhoteleng" ));

		TableColumn<Hotel, String> column_direc = new TableColumn<>( "Direccion" );
		column_direc.setMinWidth( 180 );
		column_direc.setCellValueFactory(
				new PropertyValueFactory<Hotel, String>("direchotel"));

		TableColumn<Hotel, String> column_estado = new TableColumn<>( "Estado" );
		column_direc.setMinWidth( 80 );
		column_estado.setCellValueFactory(
				new PropertyValueFactory<Hotel, String>("nomestado"));

		table_hotel.setItems( this.data );
		table_hotel.getColumns().addAll( column_id, column_nombre, 
				column_lvl, column_descr, column_direc, column_estado );

		if( translate ) {
			column_nombre.setText( "Name" );
			column_descr.setText( "Description" );
			column_direc.setText( "Address" );
			column_estado.setText( "State" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_hotel, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		this.container_edit.add( this.lbl_nomhotel, 0, 0 );
		this.container_edit.add( this.txt_nomhotel, 1, 0 );
		this.container_edit.add( this.lbl_direchotel, 2, 0 );
		this.container_edit.add( this.txt_direchotel, 3, 0 );
		this.container_edit.add( this.lbl_lvlhotel, 0, 1 );
		this.container_edit.add( this.txt_lvlhotel, 1, 1 );
		this.container_edit.add( this.lbl_estado, 2, 1 );
		this.container_edit.add( this.txt_estado, 3, 1 );
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
			translate_adminHotel();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminHotel() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_nomhotel.setText( "Name:" );
		this.lbl_lvlhotel.setText( "Level:" );
		this.lbl_direchotel.setText( "Address:" );
		this.lbl_estado.setText( "State:" );
	}

	public void setupAdd( Hotel source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setNomhotel( txt_nomhotel.getText() );
						source.setDirechotel( txt_direchotel.getText() );
						source.setLvlhotel( Integer.parseInt(txt_lvlhotel.getText()));
						source.setDescrhotel( txt_descr.getText() );
						source.setDescrhoteleng( txt_descreng.getText() );
						source.setIdestado( SQLInteractor.getStateID(txt_estado.getText().toLowerCase()));
						SQLHotel.modifyHotel( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_nomhotel.setText( source.getNomhotel() );
			txt_direchotel.setText( source.getDirechotel() );
			txt_lvlhotel.setText( ""+source.getLvlhotel() );
			txt_estado.setText( source.getNomestado() );
			txt_descr.setText( source.getDescrhotel() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					Hotel new_hotel = new Hotel();
					if( validate_fields() ){
						new_hotel.setNomhotel( txt_nomhotel.getText() );
						new_hotel.setDirechotel( txt_direchotel.getText() );
						new_hotel.setLvlhotel( Integer.parseInt(txt_lvlhotel.getText()));
						new_hotel.setDescrhotel( txt_descr.getText() );
						new_hotel.setDescrhoteleng( txt_descreng.getText() );
						new_hotel.setIdestado( SQLInteractor.getStateID(txt_estado.getText().toLowerCase()));
						SQLHotel.addHotel( new_hotel );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_nomhotel.setText( "" );
			txt_direchotel.setText( "" );
			txt_lvlhotel.setText( "" );
			txt_estado.setText( "" );
			txt_descr.setText( "" );
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<Hotel> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("estado") ||
				combo_atribute.getValue().toLowerCase().equals("state")){
				temp_result = SQLHotel.adminSearchHotel( searchstring, translate, 0 );
				for( Hotel result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "lvlhotel" ) ){
				temp_result = SQLHotel.adminSearchHotel( searchstring, translate, 1 );
				for( Hotel result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "nombre" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "name" ) ){
				temp_result = SQLHotel.adminSearchHotel( searchstring, translate, 2 );
				for( Hotel result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLHotel.adminSearchHotel( searchstring, translate, 3 );
				for( Hotel result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if( !txt_descr.getText().equals("") &&
			!txt_nomhotel.getText().equals("") &&
			!txt_lvlhotel.getText().equals("") && txt_lvlhotel.getText().matches( "[0-5]" ) &&
			!txt_estado.getText().equals("") &&
			!txt_descr.getText().equals("") && !txt_descreng.getText().equals("") )
			returnvalue = true;
		return returnvalue;
	}

}
