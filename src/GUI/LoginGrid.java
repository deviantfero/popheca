package GUI;

import java.io.File;
import java.io.IOException;

import GUI.proc.Loader;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class LoginGrid extends GridPane {
	protected Scene mainScene;
	private Stage mainStage;

	private Button button_login;
	private Button button_register;
	private Text lbl_user;
	private Text lbl_password;
	private Button button_translate;
	private TextField txt_user;
	private PasswordField txt_password;

	public LoginGrid( int width, int height, Stage mainStage ) {
		super();
		this.mainScene = new Scene( this, width, height );
		super.setVgap( 10 );
		super.setHgap( 10 );
		super.setPadding( new Insets( 150, 150, 150, 150 ) );
		Loader.loadCss( "login.css", this.mainScene );
		this.mainStage = mainStage;
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
			}
		});

		this.button_login.setMinWidth( 120 );
		this.button_login.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
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
				mainStage.setTitle( "Register" );
				mainStage.setScene( new RegisterGrid( width, height, mainStage ).getMainScene() );
			}
		});
		super.add( this.button_translate, 0, 0 );
		super.add( this.lbl_user, 0, 2 );
		super.add( this.lbl_password, 0, 3 );
		super.add( this.txt_user, 1, 2 );
		super.add( this.txt_password, 1, 3 );
		super.add( this.button_login, 0, 4 );
		super.add( this.button_register, 1, 4 );
		this.mainStage.setScene( this.mainScene );
		this.mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}
}
