package GUI;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GenericWindow extends GridPane {
	protected Stage window;
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * now the window alias "stage" is managed from within the
	 * GenericWindow class, for ease of access to different kinds
	 * of events. And we are inheriting from the father class GridPane
	 * which is a layout from the scene package on javafx this lets
	 * us order our children widget to be ordered very much like a 
	 * table.
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	protected Scene wscene;
	protected int width;
	protected int height;

	public GenericWindow( int width, int height ){
		super();
		this.window = new Stage();
		this.window.setTitle( "Generic Window" );
		super.setVgap(10);
		super.setHgap(10);
		super.setPadding( new Insets( 15, 15, 15, 15 ) );
		this.width = width;
		this.height = height;
		this.wscene = new Scene( this, this.width, this.height );
		this.window.setScene( this.wscene );
		this.window.show();
	}

	public GenericWindow( String title, int width, int height ){
		super();
		this.window = new Stage();
		this.window.setTitle( title );
		super.setVgap(10);
		super.setHgap(10);
		super.setPadding( new Insets( 15, 15, 15, 15 ) );
		this.width = width;
		this.height = height;
		this.wscene = new Scene( this, this.width, this.height );
		this.window.setScene( this.wscene );
		this.window.show();
	}

	public GenericWindow(){
		super();
		this.window = new Stage();
		this.window.setTitle( "Generic Window" );
		super.setVgap(10);
		super.setHgap(10);
		super.setPadding( new Insets( 15, 15, 15, 15 ) );
		this.width = 400;
		this.height = 300;
		this.wscene = new Scene( this, this.width, this.height );
		this.window.setScene( this.wscene );
		this.window.show();
	}

	public GenericWindow( String title ){
		super();
		this.window = new Stage();
		this.window.setTitle( title );
		super.setVgap(10);
		super.setHgap(10);
		super.setPadding( new Insets( 15, 15, 15, 15 ) );
		this.width = 400;
		this.height = 300;
		this.wscene = new Scene( this, this.width, this.height );
		this.window.setScene( this.wscene );
		this.window.show();
	}

	public Scene getWScene() {
		return wscene;
	}
}
