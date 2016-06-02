package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.geometry.Pos; //don't need it for now
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.*;
	
public class LoginWindow extends GenericWindow {
	private Button button_login;
	private Button button_register;
	private Text lbl_user;
	private Text lbl_password;
	private Text lbl_welcome;
	private TextField txt_user;
	private PasswordField txt_password;

	public LoginWindow(){
		super( "Login", 275, 200 );
		this.window.setTitle( "Login" );
		this.lbl_user = new Text( "Usuario: " );
		this.lbl_password = new Text( "Password: " );
		this.lbl_welcome = new Text();
		this.txt_user = new TextField();
		this.txt_password = new PasswordField();
		this.button_login = new Button();
		this.button_login.setText( "Login" );
		this.button_login.setMinWidth( 122 );
		this.button_login.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				//login button actions
				System.out.println( "Login?" );
				lbl_welcome.setText( "Hello" );
			}
		});
		this.button_register = new Button();
		this.button_register.setText( "Register" );
		this.button_register.setMinWidth( 122 );
		this.button_register.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				//register button actions
				System.out.println( "Register?" );
				lbl_welcome.setText( "Register!" );
				window.setHeight( 400 );
			}
		});
		super.add( this.lbl_welcome, 0, 0, 2, 1 );
		super.add( this.lbl_user, 0, 1 );
		super.add( this.lbl_password, 0, 2 );
		super.add( this.txt_user, 1, 1 );
		super.add( this.txt_password, 1, 2 );
		super.add( this.button_login, 0, 4 );
		super.add( this.button_register, 1, 4 );
	}

	public Button getButton_login() {
		return button_login;
	}

	public Button getButton_register() {
		return button_register;
	}
}
