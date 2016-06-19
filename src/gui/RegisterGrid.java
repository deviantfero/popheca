package gui;

import dblib.SQLInteractor;
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

public class RegisterGrid extends GridPane {
	private Scene mainScene;
	private GridPane form;
	private boolean translate;

	private Button button_submit;
	private Button button_back;
	private Text lbl_name;
	private Text lbl_lastname;
	private Text lbl_email;
	private Text lbl_newPass;
	private Text lbl_newPassConfirm;
	private Text lbl_error;
	private TextField txt_name;
	private TextField txt_lastname;
	private TextField txt_email;
	private PasswordField txt_newPass; 
	private PasswordField txt_newPassConfirm; 

	public RegisterGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.translate = translate;
		this.form = new GridPane();
		this.mainScene = new Scene( this, width, height );
		//setting up gridpane
		super.setVgap( 10 );
		super.setHgap( 10 );
		this.form.setVgap( 10 );
		this.form.setHgap( 10 );
		this.form.getStyleClass().add( "register_form" );
		Loader.loadCss( "register.css", this.mainScene );

		this.txt_name = new TextField();
		this.txt_lastname = new TextField();
		this.txt_email = new TextField();
		this.txt_newPass = new PasswordField();
		this.txt_newPassConfirm = new PasswordField();
		this.lbl_name = new Text( "Nombre: " );
		this.lbl_lastname = new Text( "Apellido: " );
		this.lbl_email = new Text( "Email: " );
		this.lbl_newPass = new Text( "Contraseña: " );
		this.lbl_newPassConfirm = new Text( "Repetir contraseña: " );
		this.lbl_error = new Text( "" );
		this.lbl_error.setTextAlignment( TextAlignment.CENTER );
		this.lbl_error.getStyleClass().add( "lbl_error" );

		this.button_back = new Button( "⬅");
		this.button_back.setMinWidth( 120 );
		this.button_back.getStyleClass().add( "button_back" );
		this.button_back.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				mainStage.setTitle( "Login" );
					mainStage.setScene( new LoginGrid( width, height, mainStage, translate ).getMainScene() );
			}
		});

		this.button_submit = new Button( "Confirmar" );
		this.button_submit.setMinWidth( 190 );
		this.button_submit.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "wrong format.." );
				if( validate_register() ){
					User newUser = new User( txt_name.getText(), txt_lastname.getText(), 
							txt_email.getText(), txt_newPass.getText() );
					SQLInteractor.registerUser( newUser );
					newUser = new User();
				}
			}
		});

		if( translate )
			translate_register();

		this.form.add( this.lbl_name, 0, 1 );
		this.form.add( this.txt_name, 1, 1 );
		this.form.add( this.lbl_lastname, 0, 2 );
		this.form.add( this.txt_lastname, 1, 2 );
		this.form.add( this.lbl_email, 0, 3 );
		this.form.add( this.txt_email, 1, 3 );
		this.form.add( this.lbl_newPass, 0, 4 );
		this.form.add( this.txt_newPass, 1, 4 );
		this.form.add( this.lbl_newPassConfirm, 0, 5 );
		this.form.add( this.txt_newPassConfirm, 1, 5 );
		this.form.add( this.button_back, 0, 6 );
		this.form.add( this.button_submit, 1, 6 );

		super.add( this.lbl_error, 0, 0, 2, 1 );
		super.add( this.form, 0, 1, 2, 1 );
		super.setMargin( this.lbl_error, new Insets( 100, 0, 0, 5 ) );
		super.setMargin( this.form, new Insets( 45, 500, 0, 250 ) );
		super.setHalignment( this.lbl_error, HPos.CENTER );

		mainStage.setScene( mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_register() {
		if( !this.translate ){
			this.lbl_name.setText( "Nombre: " );
			this.lbl_lastname.setText( "Apellido: " );
			this.lbl_newPass.setText( "Contraseña: " );
			this.lbl_newPassConfirm.setText( "Repetir contraseña: " );
			this.button_submit.setText( "Confirmar" );
		}else{
			this.lbl_name.setText( "Name: " );
			this.lbl_lastname.setText( "Last name: " );
			this.lbl_newPass.setText( "Password: " );
			this.lbl_newPassConfirm.setText( "Confirm password: " );
			this.button_submit.setText( "Submit" );
		}
	}

	private boolean validate_register() {
		if( this.txt_name.getText().matches( "[A-Za-z]{2,15}" ) 
			&& this.txt_lastname.getText().matches( "[A-Za-z]{2,15}" )
			&& this.txt_email.getText().matches( "^[a-zA-Z0-9]+([ _-]|\\.)?[A-Za-z0-9]+@[a-z]+\\.[a-z\\.]+" ) 
			&& this.txt_newPass.getText().equals( this.txt_newPassConfirm.getText() )
			&& this.txt_newPass.getText().matches(".{4,16}")){
			if( SQLInteractor.searchUser(txt_email.getText()) ){
				System.out.println( "SQL::Email already used" );
				if( this.translate ){
					lbl_error.setText( "Email already used" );
				}
				else
					lbl_error.setText( "Email ya utilizado" );
				return false;
			}else{
				if( this.translate ) 
					this.lbl_error.setText( "Registered user" );
				else
					this.lbl_error.setText( "Usuario Registrado" );
				return true;
			}
		}else if( !this.txt_newPass.getText().equals( this.txt_newPassConfirm) ){
			if( this.translate ){
				lbl_error.setText( "Passwords do not match" );
			}else{
				lbl_error.setText( "Contraseñas no son iguales" );
			}
			txt_newPass.clear();
			txt_newPassConfirm.clear();
			return false;
		}else{
			if( this.translate )
				this.lbl_error.setText( "Format error" );
			else
				this.lbl_error.setText( "Error de formato" );
			return false;
		}
	}
}
