package GUI;

import java.io.File;
import java.io.IOException;

import encrypt.*;
import GUI.proc.Loader;
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
	private Text lbl_user;
	private Text lbl_password;
	private Text lbl_error;
	private Button button_translate;
	private TextField txt_user;
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
		this.lbl_user = new Text( "Usuario: " );
		this.lbl_password = new Text( "Contraseña: " );
		this.lbl_error = new Text( "" );
		this.lbl_error.setTextAlignment( TextAlignment.CENTER );
		this.lbl_error.getStyleClass().add( "lbl_error" );
		this.txt_user = new TextField();
		this.txt_user.setMaxWidth( 120 );
		this.txt_password = new PasswordField();
		this.txt_password.setMaxWidth( 120 );
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
		this.button_login.setMinWidth( 120 );
		this.button_login.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "Login..." );
				if( validate_login() ){
					System.out.println( "Success" );
				}else{
					System.out.println( "Invalid data" );
					if( button_translate.getText().equals("Español") )
						lbl_error.setText( "Invalid user or password" );
					else
						lbl_error.setText( "Usuario o contraseña invalidos" );
					txt_user.clear();
					txt_password.clear();
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
		this.form.add( this.lbl_user, 0, 2 );
		this.form.add( this.lbl_password, 0, 3 );
		this.form.add( this.txt_user, 1, 2 );
		this.form.add( this.txt_password, 1, 3 );
		this.form.add( this.button_login, 0, 4 );
		this.form.add( this.button_register, 1, 4 );
		super.add( this.lbl_error, 0, 0, 2, 1 );
		//this Centers the error
		super.setHalignment( this.lbl_error, HPos.CENTER );
		super.setMargin( this.lbl_error, new Insets( 100, 10, 0, 0 ) );
		super.add( this.form, 0, 1, 2, 1 );
		super.setMargin( this.form, new Insets( 60, 600, 0, 210 ));
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
			this.lbl_user.setText( "Usuario: " );
			this.lbl_password.setText( "Contraseña: " );
			this.button_register.setText( "Registarse" );
			this.button_translate.setText( "English" );
		}else{
			this.lbl_user.setText( "User: " );
			this.lbl_password.setText( "Password: " );
			this.button_register.setText( "Register" );
			this.button_translate.setText( "Español" );
		}
	}

	private boolean validate_login() {
		if( this.txt_user.getText().matches( "^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$" ) 
				&& this.txt_password.getText().matches(".{4,16}")){
			this.lbl_error.setText( MD5.encrypt(txt_password.getText()) );
			return true;
		}else
			return false;
	}

}
