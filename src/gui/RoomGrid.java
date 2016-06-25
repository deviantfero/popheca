package gui;

import data.Reserve;

import dblib.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Button;
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
	private LocalDate rin_date;
	private LocalDate rout_date;

	private Text txt_capacity;
	private Text txt_state;
	private Text txt_price;
	private Text txt_descr;
	private ImageView view_room;
	private Image img_room;
	private Button button_reserve;
	private GridPane container;

	public RoomGrid( boolean translate, Reserve message ) {
		super();
		this.container = new GridPane();
		this.translate = translate;
		this.rin_date = message.getIn_date();
		this.rout_date = message.getOut_date();
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
		this.txt_descr = new Text();
		this.txt_descr.setWrappingWidth( 290 );
		this.txt_descr.setId( "hotel_txt" );
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
				//SQLInteractor.makeReservation( message );
			}
		});

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_capacity, 0, 0 );
		super.setValignment( this.txt_capacity, VPos.TOP );
		this.container.add( this.txt_descr, 0, 1, 1, 2 );
		super.setValignment( this.txt_descr, VPos.TOP );
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
		int index = 0;
		this.state = state;
		ArrayList<LocalDate> in_dates = new ArrayList<LocalDate>();
		ArrayList<LocalDate> out_dates = new ArrayList<LocalDate>();

		if( this.state == 1 ){
			in_dates = SQLInteractor.getInDates( this.codroom );
			out_dates = SQLInteractor.getOutDates( this.codroom );
			if( in_dates.size() == 0 ){
				SQLRoom.updateRoomState( this.codroom, 0 );
				this.state = 0;
			}
			for( int i = 0; i < in_dates.size(); i++ ) {
				if( this.rin_date.compareTo( in_dates.get(i) ) >= 0 
					&& this.rin_date.compareTo( out_dates.get(i)) <= 0 ){
					this.state = 2;
					index = i;
					break;
				}
				else if( this.rout_date.compareTo( in_dates.get(i) ) >= 0
						 && this.rout_date.compareTo( out_dates.get(i)) <= 0 ){
					this.state = 2;
					index = i;
					break;
				}
			}

		}

		switch( this.state ) {
			default:
				if( translate )
					this.txt_state.setText( "Free" ); 
				else
					this.txt_state.setText( "Libre" );
				break;
			case 2:
				if( translate ){
					this.txt_state.setText( "Occupied" ); 
					this.txt_descr.setText( "Occupied until: " + out_dates.get(index) );
				}
				else{
					this.txt_state.setText( "Ocupado" );
					this.txt_descr.setText( "Ocupado hasta: " + out_dates.get(index) );
				}
				this.txt_state.setId( "room_states" );
				this.button_reserve.setDisable( true );
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

	public void setTxt_descr(String txt_descr) {
		this.txt_descr.setText( txt_descr );
	}

	public String getTxt_descr() {
		return txt_descr.getText();
	}
	
}
