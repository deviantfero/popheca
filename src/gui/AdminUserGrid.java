package gui;

import dblib.*;
import data.User;

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


public class AdminUserGrid extends GridPane {

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
	private User activeUser;

	private ObservableList<User> data;
	private TableView<User> table_usuario;

	public AdminUserGrid( int width, int height, Stage mainStage, boolean translate ){
		super();
		this.mainScene = new Scene( this, width, height );

		Loader.loadCss( "search.css", this.mainScene );
		activeUser = SQLUser.getActive();

		this.container_top = new GridPane();
		this.container_top.setHgap( 10 );
		this.container_top.getStyleClass().add( "register_form" );
		this.button_add = new Button( "+" );
		this.button_add.setMinWidth( 40 );
		this.button_add.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				mainStage.setScene( new RegisterGrid( width, height, mainStage, translate, true ).getMainScene() );
			}
		});
		this.button_remove = new Button( "-" );
		this.button_remove.setMinWidth( 40 );
		this.button_remove.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				User temp_usuario = null;
				temp_usuario = table_usuario.getSelectionModel().getSelectedItem();
				if( temp_usuario != null  ){
					if( temp_usuario.getRole() != 0 && temp_usuario.getRole() != 2 &&
						temp_usuario.getId() != activeUser.getId() ){
						SQLUser.deleteUser( temp_usuario.getId() );
						make_search( translate );
					}
					else if( temp_usuario.getRole() == 0 && activeUser.getRole() == 2 ){
						SQLUser.deleteUser( temp_usuario.getId() );
						make_search( translate );
					}
				}
			}
		});
		this.button_edit = new Button( "Editar" );
		this.button_edit.setMinWidth( 80 );
		this.button_edit.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				User usuario_chosen = null;
				usuario_chosen = table_usuario.getSelectionModel().getSelectedItem();
				if( usuario_chosen  != null ){
					mainStage.setScene( new ProfileGrid( width, height, mainStage, 
								translate, usuario_chosen ).getMainScene());
				}
			}
		});
		this.txt_search = new TextField();
		this.txt_search.setMinWidth( 245 );
		ObservableList<String> opt = 
			FXCollections.observableArrayList( 
					"id",
					"nombre",
					"Apellido",
					"rol",
					"email"
			);

		ObservableList<String> opteng = 
			FXCollections.observableArrayList( 
					"id",
					"name",
					"lastname",
					"role",
					"email"
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

		table_usuario = new TableView<User>();
		table_usuario.setMaxHeight( 300 );
		table_usuario.setMaxWidth( 650 );
		table_usuario.setMinWidth( 650 );
		table_usuario.setEditable( false );
		this.data = FXCollections.observableArrayList();
		TableColumn<User, Integer> column_id = new TableColumn<>( "Id" );
		column_id.setMaxWidth( 40 );
		column_id.setCellValueFactory(
				new PropertyValueFactory<User, Integer>( "id" ));

		TableColumn<User, String> column_name = new TableColumn<>( "Nombre" );
		column_name.setMaxWidth( 150 );
		column_name.setCellValueFactory(
				new PropertyValueFactory<User, String>( "name" ));

		TableColumn<User, String> column_lastname = new TableColumn<>( "Apellido" );
		column_lastname.setMinWidth( 180 );
		column_lastname.setCellValueFactory(
				new PropertyValueFactory<User, String>("lastname"));

		TableColumn<User, Integer> column_role = new TableColumn<>( "Rol" );
		column_role.setMaxWidth( 30 );
		column_role.setCellValueFactory(
				new PropertyValueFactory<User, Integer>( "role" ));

		TableColumn<User, String> column_email = new TableColumn<>( "Email" );
		column_email.setMinWidth( 200 );
			column_email.setCellValueFactory(
					new PropertyValueFactory<User, String>("email" ));

		table_usuario.setItems( this.data );
		table_usuario.getColumns().addAll( column_id, column_name, 
				column_lastname, column_role, column_email );

		if( translate ) {
			column_name.setText( "Name" );
			column_lastname.setText( "Lastname" );
			column_role.setText( "Role" );
		}

		this.container_top.add( this.button_add, 0, 0 );
		this.container_top.add( this.button_remove, 1, 0 );
		this.container_top.add( this.button_edit, 2, 0 );
		this.container_top.add( this.txt_search, 3, 0 );
		this.container_top.add( this.combo_atribute, 4, 0 );
		this.container_top.add( this.button_search, 5, 0 );
		this.container_search.add( this.container_top, 0, 0 );
		this.container_search.add( this.table_usuario, 0, 1 );
		this.container_search.add( this.link_back, 0, 2 );
		super.setHalignment( this.link_back, HPos.CENTER );

		super.add( this.container_search, 0, 0 );
		super.setMargin( this.container_search, new Insets( 140, 0, 0, 100 ) );
		if( translate )
			translate_adminUser();

	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_adminUser() {
		this.button_search.setText( "Search" );
		this.button_edit.setText( "Edit" );
	}

	public void make_search(boolean translate) {
		String searchstring = txt_search.getText();
		ArrayList<User> temp_result = new ArrayList<>();
		data.clear();
		if( !txt_search.getText().equals("") && combo_atribute.getValue() != null )
			if( combo_atribute.getValue().toLowerCase().equals("nombre") ||
				combo_atribute.getValue().toLowerCase().equals("name")){
				temp_result = SQLUser.adminSearchUser( searchstring, translate, 0 );
				for( User result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "id" ) ){
				temp_result = SQLUser.adminSearchUser( searchstring, translate, 1 );
				for( User result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "apellido" ) ||
					  combo_atribute.getValue().toLowerCase().equals( "lastname" ) ){
				temp_result = SQLUser.adminSearchUser( searchstring, translate, 2 );
				for( User result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "email" ) ){
				temp_result = SQLUser.adminSearchUser( searchstring, translate, 3 );
				for( User result : temp_result )
					data.add( result );
			}else if( combo_atribute.getValue().toLowerCase().equals( "rol" ) 
					  || combo_atribute.getValue().toLowerCase().equals( "role" ) ){
				temp_result = SQLUser.adminSearchUser( searchstring, translate, 4 );
				for( User result : temp_result )
					data.add( result );
			}else
				System.err.println( "FX::Not a valid atribute to search" );

	}

}
