package gui;

import dblib.*;
import data.Destiny;

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


public class AdminDestinyGrid extends GridPane {

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
	private Text lbl_nombreentrada;
	private Text lbl_precioentrada;
	private Text lbl_estado;
	private TextField txt_nombreentrada;
	private TextField txt_precioentrada;
	private TextField txt_estado;
	private TextArea txt_tipoentradaeng;
	private TextArea txt_tipoentrada;

	private ObservableList<Destiny> data;
	private TableView<Destiny> table_entrada;

	public AdminDestinyGrid( int width, int height, Stage mainStage, boolean translate ){
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
				Destiny temp_entrada = null;
				temp_entrada = table_entrada.getSelectionModel().getSelectedItem();
				if( temp_entrada != null )
					SQLDestiny.deleteDestiny( temp_entrada.getCodentrada() );
				make_search( translate );
			}
		});
		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				Destiny entrada_chosen = null;
				entrada_chosen = table_entrada.getSelectionModel().getSelectedItem();
				if( entrada_chosen  != null ){
					container_search.setVisible( false );
					container_edit.setVisible( true );
					setupAdd( entrada_chosen, translate );
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
					"estado"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"name",
					"price",
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
		this.lbl_nombreentrada = new Text("Nombre:");
		this.lbl_nombreentrada.setId( "lbl_register" );
		this.lbl_precioentrada = new Text("Precio:");
		this.lbl_precioentrada.setId( "lbl_register" );
		this.lbl_estado = new Text("Estado:");
		this.lbl_estado.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_nombreentrada = new TextField();
		this.txt_precioentrada = new TextField();
		this.txt_tipoentrada = new TextArea();
		this.txt_tipoentradaeng = new TextArea();
		this.txt_estado = new TextField();

		if( !translate ){
			this.txt_tipoentradaeng.setPromptText( "Ingrese descripcion en Ingles");
			this.txt_tipoentrada.setPromptText( "Ingrese descripcion en Español");
		}else{
			this.txt_tipoentradaeng.setPromptText( "Input English description");
			this.txt_tipoentrada.setPromptText( "Input Spanish description");
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


		table_entrada = new TableView<Destiny>();
		table_entrada.setMaxHeight( 300 );
		table_entrada.setMaxWidth( 650 );
		table_entrada.setMinWidth( 650 );
		table_entrada.setEditable( false );
		this.data = FXCollections.observableArrayList();
		TableColumn<Destiny, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 40 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<Destiny, Integer>( "codentrada" ));

		TableColumn<Destiny, String> column_nombre = new TableColumn<>( "Nombre" );
		column_nombre.setMaxWidth( 150 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<Destiny, String>( "nomentrada" ));

		TableColumn<Destiny, String> column_descr = new TableColumn<>( "Descripcion" );
		column_descr.setMinWidth( 200 );
		if( translate )
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Destiny, String>("tipoentrada" ));
		else
			column_descr.setCellValueFactory(
					new PropertyValueFactory<Destiny, String>("tipoentradaeng" ));

		TableColumn<Destiny, Double> column_direc = new TableColumn<>( "Precio" );
		column_direc.setMinWidth( 180 );
		column_direc.setCellValueFactory(
				new PropertyValueFactory<Destiny, Double>("precioentrada"));

		TableColumn<Destiny, String> column_estado = new TableColumn<>( "Estado" );
		column_direc.setMinWidth( 80 );
		column_estado.setCellValueFactory(
				new PropertyValueFactory<Destiny, String>("nomestado"));

		table_entrada.setItems( this.data );
		table_entrada.getColumns().addAll( column_id, column_nombre, 
				column_descr, column_direc, column_estado );

		if( translate ) {
			column_nombre.setText( "Name" );
			column_descr.setText( "Description" );
			column_direc.setText( "Price" );
			column_estado.setText( "State" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_entrada, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		this.container_edit.add( this.lbl_nombreentrada, 0, 0 );
		this.container_edit.add( this.txt_nombreentrada, 1, 0 );
		this.container_edit.add( this.lbl_precioentrada, 2, 0 );
		this.container_edit.add( this.txt_precioentrada, 3, 0 );
		this.container_edit.add( this.lbl_estado, 2, 1 );
		this.container_edit.add( this.txt_estado, 3, 1 );
		this.container_edit.add( this.txt_tipoentrada, 0, 2, 2, 2 );
		this.container_edit.add( this.txt_tipoentradaeng, 2, 2, 2, 2 );
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
			translate_adminDestiny();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminDestiny() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_nombreentrada.setText( "Name:" );
		this.lbl_precioentrada.setText( "Address:" );
		this.lbl_estado.setText( "State:" );
	}

	public void setupAdd( Destiny source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setNomentrada( txt_nombreentrada.getText() );
						try{
							source.setPrecioentrada( Double.parseDouble(txt_precioentrada.getText()));
						}catch( Exception er ) {
							source.setPrecioentrada( 1.00 );
						}
						source.setTipoentrada( txt_tipoentrada.getText() );
						source.setTipoentradaeng( txt_tipoentradaeng.getText() );
						source.setIdestado( SQLInteractor.getStateID(txt_estado.getText().toLowerCase()));
						SQLDestiny.modifyDestiny( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_nombreentrada.setText( source.getNomentrada() );
			txt_precioentrada.setText( "" + source.getPrecioentrada() );
			txt_estado.setText( source.getNomestado() );
			txt_tipoentrada.setText( source.getTipoentrada() );
			txt_tipoentradaeng.setText( source.getTipoentradaeng() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					Destiny new_entrada = new Destiny();
					if( validate_fields() ){
						new_entrada.setNomentrada( txt_nombreentrada.getText() );
						try{
							new_entrada.setPrecioentrada( Double.parseDouble(txt_precioentrada.getText()));
						}catch( Exception er ) {
							new_entrada.setPrecioentrada( 1.00 );
						}
						new_entrada.setTipoentrada( txt_tipoentrada.getText() );
						new_entrada.setTipoentradaeng( txt_tipoentradaeng.getText() );
						new_entrada.setIdestado( SQLInteractor.getStateID(txt_estado.getText().toLowerCase()));
						SQLDestiny.addDestiny( new_entrada );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_nombreentrada.setText( "" );
			txt_precioentrada.setText( "" );
			txt_estado.setText( "" );
			txt_tipoentrada.setText( "" );
			txt_tipoentradaeng.setText("");
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<Destiny> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("estado") ||
				combo_atribute.getValue().toLowerCase().equals("state")){
				temp_result = SQLDestiny.adminSearchDestiny( searchstring, translate, 0 );
				for( Destiny result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "precio" ) 
					  || combo_atribute.getValue().toLowerCase().equals("price")){
				temp_result = SQLDestiny.adminSearchDestiny( searchstring, translate, 1 );
				for( Destiny result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "nombre" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "name" ) ){
				temp_result = SQLDestiny.adminSearchDestiny( searchstring, translate, 2 );
				for( Destiny result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLDestiny.adminSearchDestiny( searchstring, translate, 3 );
				for( Destiny result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if( !txt_tipoentrada.getText().equals("") &&
			!txt_nombreentrada.getText().equals("") &&
			!txt_estado.getText().equals("") &&
			!txt_tipoentrada.getText().equals("") && !txt_tipoentradaeng.getText().equals("") )
			returnvalue = true;
		return returnvalue;
	}

}
