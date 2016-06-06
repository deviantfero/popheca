package GUI;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.*;
	
public class LoginWindow extends GenericWindow {
	private Text lbl_error;

	private Button button_login;
	private Button button_register;
	private Text lbl_user;
	private Text lbl_password;
	private Button button_translate;
	private TextField txt_user;
	private PasswordField txt_password;
	//register window widgets
	private Button button_submit;
	private Button button_back;
	private Text lbl_name;
	private Text lbl_lastname;
	private Text lbl_email;
	private Text lbl_username;
	private Text lbl_newPass;
	private Text lbl_newPassConfirm;
	private TextField txt_name;
	private TextField txt_lastname;
	private TextField txt_email;
	private TextField txt_username;
	private PasswordField txt_newPass; 
	private PasswordField txt_newPassConfirm; 

	public LoginWindow(){
		super( "Login", 290, 330 );
		File f = new File("bin/GUI/styles/login.css");
		if( f.exists())
			this.wscene.getStylesheets().add( "file://" + f.getAbsolutePath() );
		else
			this.wscene.getStylesheets().add("/GUI/styles/login.css");
		this.window.setResizable( false );
		this.initRegister();
		//this creates all register items and sets them as
		//not visible
		this.lbl_error = new Text( "" );
		this.lbl_user = new Text( "Usuario: " );
		this.lbl_password = new Text( "Contraseña: " );
		this.txt_user = new TextField();
		this.txt_password = new PasswordField();
		this.button_login = new Button( "Login" );
		this.button_translate = new Button( "English" );
		this.button_translate.setMinWidth( 120 );
		this.button_translate.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				translate( button_translate.getText() );
			}
		});

		this.button_login.setMinWidth( 120 );
		this.button_login.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				//login button actions
				System.out.println( "Login..." );
				txt_user.clear();
				txt_password.clear();
			}
		});
		this.button_register = new Button( "Registarse" );
		this.button_register.setMinWidth( 120 );
		this.button_register.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				//register button actions
				System.out.println( "Register..." );
				showRegister();
				hideLogin();
			}
		});

		super.add( this.lbl_error, 1, 0 );
		super.add( this.button_translate, 0, 0 );
		super.add( this.lbl_user, 0, 2 );
		super.add( this.lbl_password, 0, 3 );
		super.add( this.txt_user, 1, 2 );
		super.add( this.txt_password, 1, 3 );
		super.add( this.button_login, 0, 4 );
		super.add( this.button_register, 1, 4 );
		//register things
		super.add( this.lbl_name, 0, 1 );
		super.add( this.txt_name, 1, 1 );
		super.add( this.lbl_lastname, 0, 2 );
		super.add( this.txt_lastname, 1, 2 );
		super.add( this.lbl_email, 0, 3 );
		super.add( this.txt_email, 1, 3 );
		super.add( this.lbl_username, 0, 4 );
		super.add( this.txt_username, 1, 4 );
		super.add( this.lbl_newPass, 0, 5 );
		super.add( this.txt_newPass, 1, 5 );
		super.add( this.lbl_newPassConfirm, 0, 6 );
		super.add( this.txt_newPassConfirm, 1, 6 );
		super.add( this.button_back, 0, 7 );
		super.add( this.button_submit, 1, 7 );
	}

	private void initRegister(){
		this.txt_name = new TextField();
		this.txt_lastname = new TextField();
		this.txt_email = new TextField();
		this.txt_username = new TextField();
		this.txt_newPass = new PasswordField();
		this.txt_newPassConfirm = new PasswordField();
		this.lbl_name = new Text( "Nombre: " );
		this.lbl_lastname = new Text( "Apellido: " );
		this.lbl_email = new Text( "Email: " );
		this.lbl_username = new Text( "Alias: " );
		this.lbl_newPass = new Text( "Contraseña: " );
		this.lbl_newPassConfirm = new Text( "Repetir contraseña: " );

		this.button_back = new Button( "Atrás" );
		this.button_back.setMinWidth( 120 );
		this.button_back.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				hideRegister();
				showLogin();
			}
		});

		this.button_submit = new Button( "Confirmar" );
		this.button_submit.setMinWidth( 120 );
		this.button_submit.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Submitting..." );
				try{
					validateRegister();
					if( button_translate.getText().equals("Español") )
						lbl_error.setText( "Submitting..." );
					else
						lbl_error.setText( "Registrando..." );
				}catch( IOException error ){
					if( button_translate.getText().equals("Español") )
						lbl_error.setText( "Retry..." );
					else
						lbl_error.setText( "Reintentar..." );
				}
			}
		});
		
		this.hideRegister();
	}

	private void showRegister() {
		this.txt_name.setVisible( true );
		this.txt_lastname.setVisible( true );
		this.txt_email.setVisible( true );
		this.txt_username.setVisible( true );
		this.txt_newPass.setVisible( true );
		this.txt_newPassConfirm.setVisible( true );
		this.lbl_name.setVisible( true );
		this.lbl_lastname.setVisible( true );
		this.lbl_email.setVisible( true );
		this.lbl_username.setVisible( true );
		this.lbl_newPass.setVisible( true );
		this.lbl_newPassConfirm.setVisible( true );
		this.button_back.setVisible( true );
		this.button_submit.setVisible( true );
	}

	private void hideRegister() {
		this.txt_name.setVisible( false );
		this.txt_lastname.setVisible( false );
		this.txt_email.setVisible( false );
		this.txt_username.setVisible( false );
		this.txt_newPass.setVisible( false );
		this.txt_newPassConfirm.setVisible( false );
		this.lbl_name.setVisible( false );
		this.lbl_lastname.setVisible( false );
		this.lbl_email.setVisible( false );
		this.lbl_username.setVisible( false );
		this.lbl_newPass.setVisible( false );
		this.lbl_newPassConfirm.setVisible( false );
		this.button_back.setVisible( false );
		this.button_submit.setVisible( false );
	}

	private void showLogin() {
		lbl_password.setVisible( true );
		lbl_user.setVisible( true );

		txt_user.setVisible( true );
		txt_password.setVisible( true );

		button_login.setVisible( true );
		button_register.setVisible( true );
	}

	private void hideLogin() {
		lbl_password.setVisible( false );
		lbl_user.setVisible( false );

		txt_user.setVisible( false );
		txt_password.setVisible( false );

		button_login.setVisible( false );
		button_register.setVisible( false );
	}

	private void translate( String language ) {
		if( language.equals("Español") ){
			this.lbl_user.setText( "Usuario: " );
			this.lbl_password.setText( "Contraseña: " );
			this.lbl_username.setText( "Alias: " );
			this.lbl_name.setText( "Nombre: " );
			this.lbl_lastname.setText( "Apellido: " );
			this.lbl_newPass.setText( "Contraseña: " );
			this.lbl_newPassConfirm.setText( "Repetir contraseña: " );
			this.button_register.setText( "Registarse" );
			this.button_submit.setText( "Confirmar" );
			this.button_back.setText( "Atrás" );
			this.button_translate.setText( "English" );
		}else{
			this.lbl_user.setText( "User: " );
			this.lbl_username.setText( "Username: " );
			this.lbl_password.setText( "Password: " );
			this.lbl_name.setText( "Name: " );
			this.lbl_lastname.setText( "Last name: " );
			this.lbl_newPass.setText( "Password: " );
			this.lbl_newPassConfirm.setText( "Confirm password: " );
			this.button_register.setText( "Register" );
			this.button_submit.setText( "Submit" );
			this.button_back.setText( "Back" );
			this.button_translate.setText( "Español" );
		}
	}

	private void validateRegister() throws IOException {
		if( !this.txt_newPass.getText().equals( this.txt_newPassConfirm.getText() ) ){
			throw new IOException();
		}else if( !this.txt_username.getText().matches( "[A-Za-z0-9]{5,15}" ) ) {
			throw new IOException();
		}else if( !this.txt_newPass.getText().matches( "[A-Za-z0-9]{5,15}" ) ){
			throw new IOException();
		}
	}
}
