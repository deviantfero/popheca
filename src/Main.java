import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main( String[] args ) {
		launch(args);
		System.out.println( "I Am done!" );
	}

	@Override
	public void start( Stage primaryStage ) {
		primaryStage.setTitle( "Animu simulator v1.0" );
		Button btn = new Button();
		btn.setText( "Click me senpai-chan~" );

		StackPane layout = new StackPane();
		layout.getChildren().add(btn);

		Scene main_scene = new Scene( layout, 300, 250 );
		primaryStage.setScene( main_scene );
		primaryStage.show();
		
	}
}
