package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenericWindow extends Stage {
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * now the window alias "stage" is managed from within the
	 * GenericWindow class, for ease of access to different kinds
	 * of events. And we are inheriting from the father class GridPane
	 * which is a layout from the scene package on javafx this lets
	 * us order our children widget to be ordered very much like a 
	 * table.
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	protected int width;
	protected int height;

	public GenericWindow( int width, int height ){
		super();
		super.setTitle( "Generic Window" );
		this.width = width;
		this.height = height;
		super.show();
	}

	public GenericWindow( String title, int width, int height ){
		super();
		super.setTitle( title );
		this.width = width;
		this.height = height;
		super.show();
	}

	public GenericWindow( String title, int width, int height, Scene content ){
		super();
		super.setTitle( title );
		this.width = width;
		this.height = height;
		super.setScene( content );
		super.show();
	}

	public GenericWindow(){
		super();
		super.setTitle( "Generic Window" );
		this.width = 400;
		this.height = 300;
		super.show();
	}

	public GenericWindow( String title ){
		super();
		super.setTitle( title );
		this.width = 400;
		this.height = 300;
		super.show();
	}

}
