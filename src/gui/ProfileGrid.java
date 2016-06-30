package gui;

import dblib.*;
import data.User;

//import java.io.File;

import gui.proc.Loader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ProfileGrid extends GridPane {
	private GridPane container;
	private GridPane container_phase1;
	private Scene mainScene;
	
	private User activeUser;

	private Text lbl_labeln;
	private Text lbl_labellastn;
	private Text lbl_labele;
	private Text lbl_labelp;

	private Text lbl_name;
	private Text lbl_lastname;
	private Text lbl_email;
	private Text lbl_role;

	private TextField txt_name;
	private TextField txt_lastname;
	private TextField txt_email;

	private GridPane container_change;
	private GridPane container_button;

	private Button button_name;
	private Button button_lastname;
	private Button button_email;
	private Button button_cancel;
	
	private Button button_password;
	private Button button_reserves;
	private Button button_change;

	private GridPane container_exit;

	private Button button_translate;
	private Button button_search;

	private GridPane container_password;
	private Button button_changePassword;
	private Button button_cancelPassword;
	private PasswordField txt_oldPass;
	private PasswordField txt_newPass;
	private int offset=0;

	private TextField txt_role;
	private Button button_role;
	private User secretUser;

	private boolean translatel;

	public ProfileGrid( int width, int height, Stage mainStage, boolean translate, User adminSent ) {
		super();
		this.mainScene = new Scene( this, width, height );
		this.translatel = translate;
		super.setVgap( 10 );
		super.setHgap( 10 );
		Loader.loadCss( "profile.css", this.mainScene );
		this.container = new GridPane();
		this.container.setVgap( 10 );
		this.container.setHgap( 10 );
		this.container.getStyleClass().add( "register_form" );

		this.container_button = new GridPane();
		this.container_button.setHgap( 10 );
		this.container_button.getStyleClass().add( "register_form" );

		this.container_change = new GridPane();
		this.container_change.setVgap( 10 );
		this.container_change.setHgap( 10 );
		this.container_change.setVisible( false );

		this.container_phase1 = new GridPane();
		this.container_phase1.setHgap( 10 );
		this.container_phase1.setVgap( 10 );

		this.container_password = new GridPane();
		this.container_password.setHgap( 10 );
		this.container_password.getStyleClass().add( "register_form" );
		this.container_password.setVisible( false );

		this.container_exit = new GridPane();
		this.container_exit.setHgap( 10 );
		this.container_exit.getStyleClass().add( "register_form" );

		this.txt_newPass = new PasswordField();
		this.txt_newPass.setPromptText( "Nueva pass" );
		this.txt_newPass.setMaxWidth( 115 );
		this.txt_oldPass = new PasswordField();
		this.txt_oldPass.setPromptText( "Contraseña" );
		this.txt_oldPass.setMaxWidth( 115 );

		this.txt_role = new TextField();
		this.txt_role.setMaxWidth( 115 );
		this.txt_role.setVisible( false );

		this.button_role = new Button( "Cambiar" );
		this.button_role.setVisible( false );
		this.button_role.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				if( txt_role.getText().matches("[01]")){
					SQLUser.setRole( Integer.parseInt( txt_role.getText() ), adminSent );
					adminSent.setRole( Integer.parseInt( txt_role.getText() ) );
					mainStage.setScene( new ProfileGrid( width, height, mainStage, translatel, adminSent ).getMainScene() );
				}
			}
		});

		if( translatel ){
			this.button_role.setText( "Change" );
			this.txt_role.setPromptText( "Modify role 0-1" );
		}
		else{
			this.txt_role.setPromptText( "Modifique el rol 0-1" );
		}

		this.secretUser = SQLUser.getActive();

		if( adminSent != null && adminSent.getRole() != 2){
			this.txt_oldPass.setDisable( true );
			if( this.secretUser.getRole() == 2 ){
				this.offset = 1;
				this.txt_role.setVisible( true );
				this.button_role.setVisible( true );
			}
		}else{
			this.txt_oldPass.setDisable( false );
		}
		this.button_changePassword = new Button( "Cambiar" );
		this.button_changePassword.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				if( txt_newPass.getText().matches(".{4,16}") )
					if( SQLUser.setPass( txt_oldPass.getText(), txt_newPass.getText() ) ){
						SQLUser.closeUsers();
						mainStage.setScene( new LoginGrid( width, height, mainStage, translatel ).getMainScene() );
					}else if( adminSent != null && adminSent.getRole() != 2){
						SQLUser.adminsetPass( txt_newPass.getText(), adminSent );
					}
			}
		});
		this.button_cancelPassword = new Button( "Cancelar" );
		this.button_cancelPassword.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				container_password.setVisible( false );
			}
		});

		this.button_search = new Button( "Busqueda" );
		this.button_search.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				mainStage.setScene( new SearchGrid( width, height, mainStage, translatel ).getMainScene() );
			}
		});

		if( adminSent != null )
			activeUser = adminSent;
		else
			activeUser = SQLUser.getActive();

		this.lbl_labeln = new Text( "Nombre:" );
		this.lbl_labeln.setId( "lbl_register" );
		super.setHalignment( this.lbl_labeln, HPos.RIGHT );
		this.lbl_labellastn = new Text( "Apellido:" );
		this.lbl_labellastn.setId( "lbl_register" );
		super.setHalignment( this.lbl_labellastn, HPos.RIGHT );
		this.lbl_labele = new Text( "Email:" );
		this.lbl_labele.setId( "lbl_register" );
		super.setHalignment( this.lbl_labele, HPos.RIGHT );
		this.lbl_labelp = new Text( "Rol:" );
		this.lbl_labelp.setId( "lbl_register" );
		super.setHalignment( this.lbl_labelp, HPos.RIGHT );

		this.lbl_name = new Text( activeUser.getName() );
		this.lbl_name.setId( "lbl_register" );
		super.setHalignment( this.lbl_name, HPos.RIGHT );
		this.lbl_lastname = new Text( activeUser.getLastname() );
		this.lbl_lastname.setId( "lbl_register" );
		super.setHalignment( this.lbl_lastname, HPos.RIGHT );
		this.lbl_email = new Text( activeUser.getEmail() );
		this.lbl_email.setId( "lbl_register" );
		super.setHalignment( this.lbl_email, HPos.RIGHT );
		this.setRolelbl( activeUser.getRole() );
		this.lbl_role.setId( "lbl_register" );
		super.setHalignment( this.lbl_role, HPos.RIGHT );
		
		this.txt_name = new TextField();
		this.txt_name.setVisible( false );
		this.txt_lastname = new TextField();
		this.txt_lastname.setVisible( false );
		this.txt_email = new TextField();
		this.txt_email.setVisible( false );

		String content_button = null;

		if( translatel )
			content_button = "Change";
		else
			content_button = "Cambiar";

		if( translatel )
			this.button_translate = new Button( "Español" );
		else
			this.button_translate = new Button( "English" );
		this.button_translate.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				if( translatel ){
					button_translate.setText( "English" );
					translatel = false;
				}else{
					button_translate.setText( "Español" );
					translatel = true;
				}
				translatel_profile( translatel );
			}
		});

		this.button_name = new Button( content_button );
		this.button_name.setMinWidth( 100 );
		this.button_name.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				txt_name.setVisible( true );
				txt_lastname.setVisible( false );
				txt_email.setVisible( false );
				button_lastname.setDisable( true );
				button_email.setDisable( true );
				if( translatel )
					button_name.setText( "Done" );
				else
					button_name.setText( "Hecho" );
				button_name.setOnAction( new EventHandler<ActionEvent>(){
					@Override
					public void handle( ActionEvent e ) {
						if(txt_name.getText().matches( "[A-Za-z]{2,15}" )){
							SQLUser.setName( txt_name.getText(), false );
							mainStage.setScene( new ProfileGrid( width, height, mainStage, translatel, activeUser ).getMainScene() );
						}else{
							if( translatel )
								txt_name.setText( "Not Valid" );
							else
								txt_name.setText( "No es valido" );
						}
						//here goes fucntion
					}
				});
			}
		});
		this.button_lastname = new Button( content_button );
		this.button_lastname.setMinWidth( 100 );
		this.button_lastname.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				txt_lastname.setVisible( true );
				txt_email.setVisible( false );
				txt_name.setVisible( false );
				button_name.setDisable( true );
				button_email.setDisable( true );
				if( translatel )
					button_lastname.setText( "Done" );
				else
					button_lastname.setText( "Hecho" );
				button_lastname.setOnAction( new EventHandler<ActionEvent>(){
					@Override
					public void handle( ActionEvent e ) {
						if(txt_lastname.getText().matches( "[A-Za-z]{2,15}" )){
							SQLUser.setName( txt_lastname.getText(), true );
							mainStage.setScene( new ProfileGrid( width, height, mainStage, translatel, activeUser ).getMainScene() );
						}else{
							if( translatel )
								txt_lastname.setText( "Not Valid" );
							else
								txt_lastname.setText( "No es valido" );
						}
					}
				});
			}
		});
		this.button_email = new Button( content_button );
		this.button_email.setMinWidth( 100 );
		this.button_email.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				txt_email.setVisible( true );
				txt_name.setVisible( false );
				txt_lastname.setVisible( false );
				button_name.setDisable( true );
				button_lastname.setDisable( true );
				if( translatel )
					button_email.setText( "Done" );
				else
					button_email.setText( "Hecho" );
				button_email.setOnAction( new EventHandler<ActionEvent>(){
					@Override
					public void handle( ActionEvent e ) {
						if(txt_email.getText().matches( "^[a-zA-Z0-9]+([ _-]|\\.)?[A-Za-z0-9]+@[a-z]+\\.[a-z\\.]+" )
							&& !SQLUser.searchUser( txt_email.getText() ) ){
							SQLUser.setEmail( txt_email.getText() );
							mainStage.setScene( new ProfileGrid( width, height, mainStage, translatel, activeUser ).getMainScene() );
						}else{
							if( translatel )
								txt_email.setText( "Not Valid" );
							else
								txt_email.setText( "No es valido" );
						}
						//here goes fucntion
					}
				});
			}
		});
		this.button_cancel = new Button( "Cancelar" );
		this.button_cancel.setMinWidth( 100 );
		this.button_cancel.setDisable( false );
		this.button_cancel.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				mainStage.setScene( new ProfileGrid( width, height, mainStage, translatel, activeUser ).getMainScene() );
			}
		});

		this.button_password = new Button( "Cambiar contraseña" );
		this.button_password.setMinWidth( 120 );
		this.button_password.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				container_password.setVisible( true );
			}
		});

		this.button_reserves = new Button( "Borrar reservas" );
		this.button_reserves.setMinWidth( 120 );
		this.button_reserves.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ){
				if( translatel )
					mainStage.setTitle( "Reservations" );
				else
					mainStage.setTitle( "Reservaciones" );
				mainStage.setScene( new DelGrid( width, height, mainStage, translatel, activeUser ).getMainScene() );
			}
		});

		this.button_change = new Button( "Cambiar datos" );
		this.button_change.setMinWidth( 120 );
		this.button_change.setOnAction( new EventHandler<ActionEvent>(){
			@Override
			public void handle( ActionEvent e ) {
				container_change.setVisible( true );
				button_change.setDisable( true );
			}
		});

		this.container.add( this.lbl_labeln, 0, 0 );
		this.container.add( this.lbl_labellastn, 0, 1 );
		this.container.add( this.lbl_labele, 0, 2 );
		this.container.add( this.lbl_labelp, 0, 3 );

		this.container.add( this.lbl_name, 1, 0);
		this.container.add( this.lbl_lastname, 1, 1);
		this.container.add( this.lbl_email, 1, 2);
		this.container.add( this.lbl_role, 1, 3);

		this.container.add( this.txt_name, 1, 0 );
		this.container.add( this.txt_lastname, 1, 1 );
		this.container.add( this.txt_email, 1, 2 );

		this.container.add( this.container_change, 2, 0, 1, 4 );

		this.container_button.add( this.button_reserves, 0, 0 );
		this.container_button.add( this.button_password, 1 , 0 );
		this.container_button.add( this.button_change, 2, 0 );

		this.container_change.add( this.button_name, 2, 0 );
		this.container_change.add( this.button_lastname, 2, 1 );
		this.container_change.add( this.button_email, 2, 2 );
		this.container_change.add( this.button_cancel, 2, 3 );

		this.container_password.add( this.txt_oldPass, 0, 0 );
		this.container_password.add( this.txt_newPass, 1, 0 );
		this.container_password.add( this.button_changePassword, 2, 0 );
		this.container_password.add( this.button_cancelPassword, 3, 0 );
		this.container_password.add( this.button_role, 2, 0+this.offset );
		this.container_password.add( this.txt_role, 1, 0+this.offset );

		this.container_phase1.add( this.container, 0, 1 );
		this.container_phase1.add( this.container_button, 0, 2 );
		this.container_phase1.add( this.container_password, 0, 3 );
		super.setMargin( this.container_phase1, new Insets( 180, 0, 0, 200 ) );
		super.add( this.container_phase1, 0, 0 );
		this.container_exit.add( this.button_translate, 1, 0 );
		this.container_exit.add( this.button_search, 0, 0 );
		super.add( this.container_exit, 0, 1 );
		super.setMargin( this.container_exit, new Insets( -550, 0, 500, 600 ) );

		translatel_profile( translatel );

		mainStage.setScene( this.mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}

	private void setRolelbl( int role ){
		switch( role ) {
			case 0:
				this.lbl_role = new Text( "Admin" );
				break;
			case 1:
				if( this.translatel )
					this.lbl_role = new Text( "Usuario" );
				else
					this.lbl_role = new Text( "User" );
				break;
			case 2:
				this.lbl_role = new Text( "Super admin" );
				break;

		}
	}

	private void translatel_profile( boolean translatel ) {
		if( translatel ){
			this.lbl_labeln.setText( "Name: " );
			this.lbl_labellastn.setText( "Lastname: " );
			this.lbl_labelp.setText( "Role: " );
			this.button_password.setText( "Change Pass" );
			this.button_reserves.setText( "Delete Reservation" );
			this.button_change.setText( "Change Data" );
			this.button_cancel.setText( "Cancel" );
			this.button_changePassword.setText( "Change" );
			this.button_cancelPassword.setText( "Cancel" );
			this.txt_oldPass.setPromptText( "Old pass" );
			this.txt_newPass.setPromptText( "New pass" );
			this.button_name.setText( "Change" );
			this.button_lastname.setText( "Change" );
			this.button_email.setText( "Change" );
			this.button_search.setText( "Search" );
		}else{
			this.lbl_labeln.setText( "Nombre: " );
			this.lbl_labellastn.setText( "Apellido: " );
			this.lbl_labelp.setText( "Rol: " );
			this.button_password.setText( "Cambiar contraseña" );
			this.button_reserves.setText( "Borrar reservas" );
			this.button_change.setText( "Cambiar datos" );
			this.button_cancel.setText( "Cancelar" );
			this.button_changePassword.setText( "Cambiar" );
			this.button_cancelPassword.setText( "Cancelar" );
			this.txt_oldPass.setPromptText( "Contraseña" );
			this.txt_newPass.setPromptText( "Nueva pass" );
			this.button_name.setText( "Cambiar" );
			this.button_lastname.setText( "Cambiar" );
			this.button_email.setText( "Cambiar" );
			this.button_search.setText( "Busqueda" );
		}
	}
}
