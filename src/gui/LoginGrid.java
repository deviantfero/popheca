package gui;

import java.io.File;
import java.io.IOException;

import dblib.*;
import gui.proc.Loader;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class LoginGrid extends GridPane {
	protected Scene mainScene;
	private GridPane form;

	private Button button_login;
	private Button button_register;
	private Text lbl_email;
	private Text lbl_password;
	private Text lbl_error;
	private Button button_translate;
	private TextField txt_email;
	private PasswordField txt_password;

	public LoginGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.form = new GridPane();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "login.css", this.mainScene );
		this.form.getStyleClass().add( "grid" );
		super.setVgap( 10 );
		super.setHgap( 10 );
		this.form.setVgap( 10 );
		this.form.setHgap( 10 );
		this.lbl_email = new Text( "Email: " );
		this.lbl_password = new Text( "Contraseña: " );
		this.lbl_error = new Text( "" );
		this.lbl_error.setTextAlignment( TextAlignment.CENTER );
		this.lbl_error.getStyleClass().add( "lbl_error" );
		this.txt_email = new TextField();
		this.txt_email.setMaxWidth( 150 );
		this.txt_password = new PasswordField();
		this.txt_password.setMaxWidth( 150 );
		this.button_translate = new Button( "English" );
		this.button_translate.setMinWidth( 120 );
		this.button_translate.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				translate_login();
			}
		});

		this.button_login = new Button( "Login" );
		this.button_login.getStyleClass().add( "button_login" );
		this.button_login.setMinWidth( 150 );
		this.button_login.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Login..." );
				if( validate_login() ){
					boolean exists = SQLInteractor.searchUser( txt_email.getText(), txt_password.getText() );
					if( exists ){
						lbl_error.setText( "Usuario encontrado, login correcto" );
						SQLInteractor.setActive( txt_email.getText(), txt_password.getText() );
						if( button_translate.getText().equals( "Español" ) ) {
							mainStage.setTitle( "Search" );
							mainStage.setScene( new SearchGrid( width, height, mainStage, true ).getMainScene() );
						}else{
							mainStage.setTitle( "Busqueda" );
							mainStage.setScene( new SearchGrid( width, height, mainStage, false ).getMainScene() );
						}
						//abrir ventana nueva aqui
					}
				}else{
					System.out.println( "Invalid data" );
					if( button_translate.getText().equals("Español") )
						lbl_error.setText( "Invalid user or password" );
					else
						lbl_error.setText( "Usuario o contraseña invalidos" );
				}
			}
		});
		this.button_register = new Button( "Registarse" );
		this.button_register.setMinWidth( 120 );
		this.button_register.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				//register button actions
				System.out.println( "Register..." );
				if( button_translate.getText().equals( "Español" ) ) {
					mainStage.setTitle( "Register" );
					mainStage.setScene( new RegisterGrid( width, height, mainStage, true ).getMainScene() );
				}else{
					mainStage.setTitle( "Registrar" );
					mainStage.setScene( new RegisterGrid( width, height, mainStage, false ).getMainScene() );
				}
			}
		});

		if( translate )
			translate_login();

		this.form.add( this.button_translate, 0, 0 );
		this.form.add( this.lbl_email, 0, 2 );
		this.form.add( this.lbl_password, 0, 3 );
		this.form.add( this.txt_email, 1, 2 );
		this.form.add( this.txt_password, 1, 3 );
		this.form.add( this.button_register, 0, 4 );
		this.form.add( this.button_login, 1, 4 );
		super.add( this.lbl_error, 0, 0, 2, 1 );
		//this Centers the error
		super.setHalignment( this.lbl_error, HPos.CENTER );
		super.setMargin( this.lbl_error, new Insets( 100, 0, 0, 0 ) );
		super.add( this.form, 0, 1, 2, 1 );
		//top, right, bottom, left
		super.setMargin( this.form, new Insets( 60, 230, 0, 200 ));
		super.getColumnConstraints().add( new ColumnConstraints(350) );
		super.getColumnConstraints().add( new ColumnConstraints(350) );
		mainStage.setScene( this.mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}
	
	private void translate_login() {
		if( button_translate.getText().equals( "Español" ) ){
			this.lbl_password.setText( "Contraseña: " );
			this.button_register.setText( "Registarse" );
			this.button_translate.setText( "English" );
		}else{
			this.lbl_password.setText( "Password: " );
			this.button_register.setText( "Register" );
			this.button_translate.setText( "Español" );
		}
	}

	private boolean validate_login() {
		if( this.txt_email.getText().matches( "^[a-zA-Z0-9]+([ _-]|\\.)?[A-Za-z0-9]+@[a-z]+\\.[a-z\\.]+" ) 
				&& this.txt_password.getText().matches(".{4,100}")){
			this.lbl_error.setText( MD5.encrypt(txt_password.getText()) );
			return true;
		}else
			return false;
	}

}
