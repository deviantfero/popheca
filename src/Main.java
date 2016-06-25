import javafx.application.Application;
import javafx.stage.Stage;
import gui.*;
import dblib.*;

/* **************************************************
 * when creating a window we need to override
 * the start method after executing launch(args)
 * in the main method of the main class every window
 * needs some things, a Stage object, which is like
 * the master container for everything in a window then
 * a layout, be it a grid or a flow layout which goes
 * inside of each one of the different classes we'll be
 * creating to simulate different kinds of windows, and
 * contains the widgets or elements in which we interact
 * with the software, in the GenericWindow case, that
 * layout is by default a grid, that layout is asigned 
 * to a "scene" wich is the window itself, which contains 
 * a width heigth and title
 * *****************************************************/

public class Main extends Application {
	public static void main( String[] args ) {
		launch(args);
	}

	@Override
	public void start( Stage loginStage ) {
		GenericWindow main_window = new GenericWindow( "Login", 850, 600 );
		main_window.setResizable( false );
		LoginGrid login = new LoginGrid( 850, 600, main_window, false );
		//BlankGrid blank = new BlankGrid( 850, 600, main_window, false );
	}

	@Override
	public void stop() {
		SQLUser.closeUsers();
		System.out.println( "Exit succesfully" );
	}
}
