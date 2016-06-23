package gui;

import data.Reserve;

import dblib.SQLInteractor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.scene.text.*;

public class RoomGrid extends GridPane {
	private int capacity;
	private int state;
	private int codroom;
	private int codhotel;
	private boolean translate;

	private Text txt_capacity;
	private Text txt_state;
	private Text txt_price;
	private ImageView view_room;
	private Image img_room;
	private Button button_reserve;
	private GridPane container;

	public RoomGrid( boolean translate, Reserve message ) {
		super();
		this.container = new GridPane();
		this.translate = translate;
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setVgap( 10 );
		this.container.setId( "hotel_result" );
		this.txt_capacity = new Text();
		this.txt_capacity.minWidth( 200 );
		this.txt_capacity.maxWidth( 200 );
		this.txt_state = new Text();
		this.txt_price = new Text();
		this.view_room = new ImageView();
		if( translate )
			this.button_reserve = new Button( "Reserve" );
		else
			this.button_reserve = new Button( "Reservar" );
		this.button_reserve.setMinWidth( 90 );
		this.button_reserve.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent e ) {
				message.setIdHotel( codhotel );
				message.setIdRoom( codroom );
				System.out.println( "Reserve" + codhotel );
				SQLInteractor.makeReservation( message );
			}
		});

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_capacity, 0, 0 );
		super.setValignment( this.txt_capacity, VPos.TOP );
		this.container.add( this.view_room, 1, 0, 1, 2 );
		this.container.add( this.button_reserve, 1, 2 );
		this.container.add( this.txt_price, 0, 3 );
		this.container.add( this.txt_state, 1, 3 );
		super.setHalignment( this.txt_state, HPos.CENTER );
	}

	public void setTxt_capacity( int capacity ) {
		this.capacity = capacity;
		switch( this.capacity ) {
			case 1:
				if( translate )
					this.txt_capacity.setText( "Single" ); 
				else
					this.txt_capacity.setText( "Sencilla" );
				break;
			case 2:
				if( translate )
					this.txt_capacity.setText( "Double" ); 
				else
					this.txt_capacity.setText( "Doble" );
				break;
			case 3:
				if( translate )
					this.txt_capacity.setText( "Familiar" ); 
				else
					this.txt_capacity.setText( "Familiar" );
				break;
		}
	}

	public void setTxt_state( int state ) {
		this.state = state;
		switch( this.state ) {
			case 0:
				if( translate )
					this.txt_state.setText( "Free" ); 
				else
					this.txt_state.setText( "Libre" );
				break;
			case 1:
				if( translate )
					this.txt_state.setText( "Reserved" ); 
				else
					this.txt_state.setText( "Reservado" );
				this.txt_state.setId( "room_states" );
				break;
			case 2:
				if( translate )
					this.txt_state.setText( "Occupied" ); 
				else
					this.txt_state.setText( "Ocupado" );
				this.txt_state.setId( "room_states" );
				break;
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCodroom() {
		return codroom;
	}

	public void setCodroom(int codroom) {
		this.codroom = codroom;
	}

	public int getCodhotel() {
		return codhotel;
	}

	public void setCodhotel(int codhotel) {
		this.codhotel = codhotel;
	}

	public String getTxt_capacity() {
		return txt_capacity.getText();
	}

	public String getTxt_state() {
		return txt_state.getText();
	}

	public void setTxt_price( double price ) {
		this.txt_price.setText( "$" + price );
	}

	public Button getButton_select() {
		return button_reserve;
	}

	public void setImageRoom( String path ) {
		this.img_room = new Image( path, 90, 150, true, false );
		this.view_room.setImage( this.img_room );
	}
	
}