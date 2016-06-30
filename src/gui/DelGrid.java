package gui;

import gui.proc.Loader;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.util.ArrayList;

import dblib.*;
import data.User;

public class DelGrid extends GridPane {
	private Scene mainScene;

	private ScrollPane resultscontainer;

	private GridPane results;
	private GridPane center;
	private ArrayList<ReservationGrid> resultGrids;
	private Hyperlink back;

	private User activeUser;

	public DelGrid( int width, int height, Stage mainStage, boolean translate, User adminSent ) {
		super();
		this.mainScene = new Scene( this, width, height );
		Loader.loadCss( "search.css", this.mainScene );

		this.resultscontainer = new ScrollPane();
		this.resultscontainer.getStyleClass().add( "myscrollpane" );
		this.resultscontainer.setContent( this.results );
		this.resultscontainer.setHbarPolicy( ScrollBarPolicy.NEVER );
		this.resultscontainer.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		this.resultscontainer.setMinHeight( 300 );
		
		this.back = new Hyperlink();
		if( translate )
			this.back.setText( "Back" );
		else
			this.back.setText( "Atras" );

		if( adminSent != null )
			activeUser = adminSent;
		else
			activeUser = SQLUser.getActive();
		this.back.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				mainStage.setTitle( activeUser.getName() + " " + activeUser.getLastname() );
				mainStage.setScene( new ProfileGrid( width, height, mainStage, translate, activeUser ).getMainScene() );
			}
		});

		this.center = new GridPane();

		this.results = new GridPane();
		this.resultGrids = SQLReservation.searchReservation( translate, adminSent );
		super.setMargin( this.back, new Insets( 130, 0, 0, 410 ) );
		this.back.setTextAlignment( TextAlignment.CENTER );
		if( this.resultGrids.size() < 1 ){
			if( translate )
				this.back.setText( "No reservations, go back" );
			else
				this.back.setText( "No hay reservaciones, atras" );
			this.resultscontainer.setVisible( false );
			super.setMargin( this.back, new Insets( 130, 0, 0, 320 ) );
		}
		int i = 0;
		for( ReservationGrid result : this.resultGrids ) {
			this.results.add( result, 0, i );
			result.getButton_reserve().setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle( ActionEvent e ) {
					SQLReservation.deleteReservation( result.getCodreserva(), result.getCodhabitacion(),
							result.getCodtransporte() );
					result.getButton_reserve().setText( "Deleted" );
					result.getButton_reserve().setDisable( true );
				}
			});
			i++;
		}

		this.resultscontainer.setContent( this.results );

		this.center.add( this.back, 0, 0 );
		this.center.add( this.resultscontainer, 0, 1 );
		super.setMargin( this.resultscontainer, new Insets( 10, 20, 100, 100 ) );
		super.add( this.center, 0, 0 );
	}

	/**
	 * @return the mainScene
	 */
	public Scene getMainScene() {
		return mainScene;
	}
}
