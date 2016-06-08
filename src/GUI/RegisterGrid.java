package GUI;

import java.io.File;

import GUI.proc.Loader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class RegisterGrid extends GridPane {
	private Scene mainScene;
	private GridPane form;

	private Button button_submit;
	private Button button_back;
	private Button button_translate;
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

	public RegisterGrid( int width, int height, Stage mainStage, boolean translate ) {
		super();
		this.form = new GridPane();
		this.mainScene = new Scene( this, width, height );
		//setting up gridpane
		super.setVgap( 10 );
		super.setHgap( 10 );
		this.form.setVgap( 10 );
		this.form.setHgap( 10 );
		Loader.loadCss( "register.css", this.mainScene );

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

		this.button_back = new Button( "⬅");
		this.button_back.setMinWidth( 120 );
		this.button_back.getStyleClass().add( "button_back" );
		this.button_back.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				mainStage.setTitle( "Login" );
				if( translate )
					mainStage.setScene( new LoginGrid( width, height, mainStage, true ).getMainScene() );
				else
					mainStage.setScene( new LoginGrid( width, height, mainStage, false ).getMainScene() );
			}
		});

		this.button_submit = new Button( "Confirmar" );
		this.button_submit.setMinWidth( 190 );
		this.button_submit.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "this things" );
				if( validate_register() ){
					System.out.println( "success" );
				}
			}
		});

		this.button_translate = new Button( "English" );
		this.button_translate.setMinWidth( 120 );
		this.button_translate.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				translate_register();
			}
		});

		if( translate )
			translate_register();

		this.form.add( this.button_translate, 0, 0 );
		this.form.add( this.lbl_name, 0, 1 );
		this.form.add( this.txt_name, 1, 1 );
		this.form.add( this.lbl_lastname, 0, 2 );
		this.form.add( this.txt_lastname, 1, 2 );
		this.form.add( this.lbl_email, 0, 3 );
		this.form.add( this.txt_email, 1, 3 );
		this.form.add( this.lbl_username, 0, 4 );
		this.form.add( this.txt_username, 1, 4 );
		this.form.add( this.lbl_newPass, 0, 5 );
		this.form.add( this.txt_newPass, 1, 5 );
		this.form.add( this.lbl_newPassConfirm, 0, 6 );
		this.form.add( this.txt_newPassConfirm, 1, 6 );
		this.form.add( this.button_back, 0, 7 );
		this.form.add( this.button_submit, 1, 7 );

		super.add( this.form, 0, 1, 2, 1 );
		super.setMargin( this.form, new Insets( 150, 600, 0, 210 ) );

		mainStage.setScene( mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}

	private void translate_register() {
		if( button_translate.getText().equals( "Español" ) ){
			this.lbl_username.setText( "Alias: " );
			this.lbl_name.setText( "Nombre: " );
			this.lbl_lastname.setText( "Apellido: " );
			this.lbl_newPass.setText( "Contraseña: " );
			this.lbl_newPassConfirm.setText( "Repetir contraseña: " );
			this.button_submit.setText( "Confirmar" );
			this.button_translate.setText( "English" );
		}else{
			this.lbl_username.setText( "Username: " );
			this.lbl_name.setText( "Name: " );
			this.lbl_lastname.setText( "Last name: " );
			this.lbl_newPass.setText( "Password: " );
			this.lbl_newPassConfirm.setText( "Confirm password: " );
			this.button_submit.setText( "Submit" );
			this.button_translate.setText( "Español" );
		}
	}

	private boolean validate_register() {
		if( this.txt_name.getText().matches( "[A-Za-z]{2,15}" ) 
			&& this.txt_lastname.getText().matches( "[A-Za-z]{2,15}" )
			&& this.txt_username.getText().matches( "^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$" ) 
			&& this.txt_email.getText().matches( "^[a-zA-Z0-9]+([ _-]|\\.)?[A-Za-z0-9]+@[a-z]+\\.[a-z\\.]+" ) 
			&& this.txt_newPass.getText().equals( this.txt_newPassConfirm.getText() )
			&& this.txt_newPass.getText().matches(".{4,16}"))
			return true;
		else
			return false;
	}
}
