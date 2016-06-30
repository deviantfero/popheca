package gui;

import dblib.*;
import data.Food;

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


public class AdminFoodGrid extends GridPane {

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
	private Text lbl_nomplan;
	private Text lbl_idhotel;
	private Text lbl_precioplan;
	private TextField txt_nomplan;
	private TextField txt_idhotel;
	private TextField txt_precioplan;
	private TextArea txt_desrcplan;
	private TextArea txt_desrcplaneng;

	private ObservableList<Food> data;
	private TableView<Food> table_transport;

	public AdminFoodGrid( int width, int height, Stage mainStage, boolean translate ){
		super();
		this.mainScene = new Scene( this, width, height );

		Loader.loadCss( "search.css", this.mainScene );

		this.container_edit = new GridPane();
		this.lbl_nomplan = new Text("Nombre:");
		this.lbl_nomplan.setId( "lbl_register" );
		this.lbl_idhotel = new Text("Hotel:");
		this.lbl_idhotel.setId( "lbl_register" );
		this.lbl_precioplan = new Text("Precio:");
		this.lbl_precioplan.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_nomplan = new TextField();
		this.txt_precioplan = new TextField();
		this.txt_idhotel = new TextField();
		this.txt_desrcplan = new TextArea();
		this.txt_desrcplaneng = new TextArea();
		if( translate ){
			this.txt_desrcplaneng.setPromptText( "Ingrese descripcion en Ingles");
			this.txt_desrcplan.setPromptText( "Ingrese descripcion en Español");
		}else{
			this.txt_desrcplaneng.setPromptText( "Input English description");
			this.txt_desrcplan.setPromptText( "Input Spanish description");
		}

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
				Food temp_transport = null;
				temp_transport = table_transport.getSelectionModel().getSelectedItem();
				if( temp_transport != null )
					SQLFood.deleteFood( temp_transport.getCodplan() );
				make_search( translate );
			}
		});

		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				Food hotel_chosen = null;
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
					"nombre",
					"precio",
					"hotel"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"name",
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


		table_transport = new TableView<Food>();
		table_transport.setMaxHeight( 300 );
		table_transport.setMaxWidth( 650 );
		table_transport.setMinWidth( 650 );
		table_transport.setEditable( false );
		this.data = FXCollections.observableArrayList();

		TableColumn<Food, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 80 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Food, Integer>( "codplan" ));

		TableColumn<Food, String> column_nombre = new TableColumn<>( "Nombre" );
		column_nombre.setMaxWidth( 150 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<Food, String>( "nomplan" ));

		TableColumn<Food, Double> column_price = new TableColumn<>( "Precio" );
		column_price.setMaxWidth( 80 );
		column_price.setCellValueFactory(
				new PropertyValueFactory<Food, Double>( "precioplan" ));

		TableColumn<Food, String> column_hotel = new TableColumn<>( "Hotel" );
		column_hotel.setMinWidth( 180 );
		column_hotel.setCellValueFactory(
				new PropertyValueFactory<Food, String>("nomhotel"));

		TableColumn<Food, String> column_descr = new TableColumn<>( "Descripcion" );
		column_descr.setMinWidth( 180 );
		if( translate )
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Food, String>("descrplaneng"));
		else
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Food, String>("descrplan"));

		table_transport.setItems( this.data );
		table_transport.getColumns().addAll( column_id, column_nombre, 
				column_price, column_hotel, column_descr );

		if( translate ) {
			column_nombre.setText( "Name" );
			column_price.setText( "Price" );
			column_descr.setText( "Description" );
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

		this.container_edit.add( this.lbl_nomplan, 0, 0 );
		this.container_edit.add( this.txt_nomplan, 1, 0 );
		this.container_edit.add( this.lbl_idhotel, 0, 1 );
		this.container_edit.add( this.txt_idhotel, 1, 1 );
		this.container_edit.add( this.lbl_precioplan, 2, 1);
		this.container_edit.add( this.txt_precioplan, 3, 1);
		this.container_edit.add( this.txt_desrcplan, 0, 2, 2, 2 );
		this.container_edit.add( this.txt_desrcplaneng, 2, 2, 2, 2 );
		this.container_edit.add( this.button_accept, 0, 4, 2, 1);
		this.container_edit.add( this.button_cancel, 2, 4, 2, 1);
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
			translate_adminFood();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminFood() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_nomplan.setText( "Name:" );
		this.lbl_idhotel.setText( "Hotel:" );
	}

	public void setupAdd( Food source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setNomplan( txt_nomplan.getText() );
						source.setIdhotel( SQLHotel.searchHotelId( txt_idhotel.getText().toLowerCase() ));
						try{
							source.setPrecioplan( Double.parseDouble( txt_precioplan.getText() ));
						}catch( Exception er ) {
							source.setPrecioplan( 0.00 );
						}
						source.setDescrplan( txt_desrcplan.getText() );
						source.setDescrplaneng( txt_desrcplaneng.getText() );
						SQLFood.modifyFood( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_nomplan.setText( source.getNomplan() );
			txt_idhotel.setText( SQLHotel.searchHotelName(source.getIdhotel() ));
			txt_desrcplan.setText( source.getDescrplan() );
			txt_desrcplaneng.setText( source.getDescrplaneng() );
			txt_precioplan.setText( "" + source.getPrecioplan() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					Food new_food = new Food();
					if( validate_fields() ){
						new_food.setNomplan( txt_nomplan.getText() );
						new_food.setIdhotel( SQLHotel.searchHotelId( txt_idhotel.getText().toLowerCase() ));
						try{
							new_food.setPrecioplan( Double.parseDouble( txt_precioplan.getText() ));
						}catch( Exception er ) {
							new_food.setPrecioplan( 0.00 );
						}
						new_food.setDescrplan( txt_desrcplan.getText() );
						new_food.setDescrplaneng( txt_desrcplaneng.getText() );
						SQLFood.addFood( new_food );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_nomplan.setText( "" );
			txt_idhotel.setText("");
			txt_precioplan.setText("");
			txt_desrcplan.setText("");
			txt_desrcplaneng.setText("");
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<Food> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("hotel")){
				temp_result = SQLFood.adminSearchFood( searchstring, 0 );
				for( Food result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "precio" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "price" ) ){
				temp_result = SQLFood.adminSearchFood( searchstring, 1 );
				for( Food result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "nombre" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "name" ) ){
				temp_result = SQLFood.adminSearchFood( searchstring, 2 );
				for( Food result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLFood.adminSearchFood( searchstring, 3 );
				for( Food result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if(	!txt_nomplan.getText().equals("") &&
			!txt_idhotel.getText().equals("") &&
			!txt_precioplan.getText().equals("") &&
			!txt_desrcplan.getText().equals("") )
			returnvalue = true;
		if( returnvalue )
			System.out.println( "TRUE?" );
		return returnvalue;
	}

}
