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
	private Stage mainStage;

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

	public RegisterGrid( int width, int height, Stage mainStage ) {
		super();
		this.mainScene = new Scene( this, width, height );
		//setting up gridpane
		super.setVgap( 10 );
		super.setHgap( 10 );
		super.setPadding( new Insets( 150, 150, 150, 150 ) );
		Loader.loadCss( "register.css", this.mainScene );

		this.mainStage = mainStage;
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
				mainStage.setScene( new LoginGrid( width, height, mainStage ).getMainScene() );
			}
		});

		this.button_submit = new Button( "Confirmar" );
		this.button_submit.setMinWidth( 120 );
		this.button_submit.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				System.out.println( "this things" );
			}
		});

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

		mainStage.setScene( mainScene );
		mainStage.show();
	}

	public Scene getMainScene() {
		return mainScene;
	}
}
