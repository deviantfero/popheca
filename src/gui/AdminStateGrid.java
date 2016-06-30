package gui;

import dblib.*;
import data.State;

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


public class AdminStateGrid extends GridPane {

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
	private Text lbl_nomestado;
	private TextField txt_nomestado;

	private ObservableList<State> data;
	private TableView<State> table_state;

	public AdminStateGrid( int width, int height, Stage mainStage, boolean translate ){
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
				State temp_state = null;
				temp_state = table_state.getSelectionModel().getSelectedItem();
				if( temp_state != null )
					SQLInteractor.deleteState( temp_state.getIdestado() );
				make_search( translate );
			}
		});

		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				State state_chosen = null;
				state_chosen = table_state.getSelectionModel().getSelectedItem();
				if( state_chosen  != null ){
					container_search.setVisible( false );
					container_edit.setVisible( true );
					setupAdd( state_chosen, translate );
				}
			}
		});

		this.txt_search = new TextField();
		this.txt_search.setMinWidth( 245 );
		ObservableList<String> opt = 
			FXCollections.observableArrayList( 
					"id",
					"nombre"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"name"
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
		this.lbl_nomestado = new Text("Nombre:");
		this.lbl_nomestado.setId( "lbl_register" );

		this.container_edit = new GridPane();
		this.txt_nomestado = new TextField();

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


		table_state = new TableView<State>();
		table_state.setMaxHeight( 300 );
		table_state.setMaxWidth( 650 );
		table_state.setMinWidth( 650 );
		table_state.setEditable( false );
		this.data = FXCollections.observableArrayList();

		TableColumn<State, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 40 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<State, Integer>( "idestado" ));

		TableColumn<State, String> column_nombre = new TableColumn<>( "Nombre" );
		column_nombre.setMaxWidth( 150 );
		column_nombre.setCellValueFactory(
				new PropertyValueFactory<State, String>( "nomestado" ));

		table_state.setItems( this.data );
		table_state.getColumns().addAll( column_id, column_nombre );

		if( translate ) {
			column_nombre.setText( "Name" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_state, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		this.container_edit.add( this.lbl_nomestado, 0, 0 );
		this.container_edit.add( this.txt_nomestado, 1, 0 );
		this.container_edit.add( this.button_accept, 2, 0 );
		this.container_edit.add( this.button_cancel, 3, 0 );
		this.container_edit.setVisible( false );
		this.container_edit.setHgap( 10 );
		this.container_edit.setVgap( 10 );
		this.container_edit.getStyleClass().add( "register_form" );

		super.add( this.container_edit, 0, 0 );
		super.add( this.container_search, 0, 0 );
		super.setMargin( this.container_search, new Insets( 140, 0, 0, 100 ) );
		super.setMargin( this.container_edit, new Insets( 140, 100, 0, 100 ) );
		if( translate )
			translate_adminState();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminState() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
		this.button_accept.setText( "Accept" );
		this.button_cancel.setText( "Cancel" );
		this.lbl_nomestado.setText( "Name:" );
	}

	public void setupAdd( State source, boolean translate ) {
		if( source != null ) {
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					System.out.println( "Edit" );
					if(validate_fields()){
						source.setNomestado( txt_nomestado.getText() );
						SQLInteractor.modifyState( source );
						make_search( translate );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
					//SQLedithere
				}
			});
			txt_nomestado.setText( source.getNomestado() );
		}else{
			button_accept.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					//SQLadd here
					System.out.println( "Add" );
					State new_hotel = new State();
					if( validate_fields() ){
						new_hotel.setNomestado( txt_nomestado.getText() );
						SQLInteractor.addState( txt_nomestado.getText() );
						container_edit.setVisible( false );
						container_search.setVisible( true );
					}
				}
			});
			txt_nomestado.setText( "" );
		}
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<State> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("nombre") ||
				combo_atribute.getValue().toLowerCase().equals("name")){
				temp_result = SQLInteractor.adminSearchState( searchstring, 0 );
				for( State result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLInteractor.adminSearchState( searchstring, 1 );
				for( State result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

	public boolean validate_fields() {
		boolean returnvalue = false;
		if( !txt_nomestado.getText().equals("") )
			returnvalue = true;
		return returnvalue;
	}

}
