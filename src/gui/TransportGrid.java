package gui;

import data.Register;
import data.Reserve;
import dblib.*;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.text.*;
import java.time.LocalDate;

import java.util.ArrayList;


public class TransportGrid extends GridPane {
	private boolean translate;
	private Register transport_register;
	private int idtransport;
	private int codhotel;
	private int state;
	private LocalDate rin_date;
	private LocalDate rout_date;

	private Text txt_price;
	private Text txt_model;
	private Text txt_passengers;
	private Text txt_state;
	private ImageView view_transport;
	private Image img_transport;
	private Button button_reserve;
	private GridPane container;

	public TransportGrid( boolean translate, Register transport_register, Reserve message ) {
		super();
		this.container = new GridPane();
		this.transport_register = transport_register;
		this.translate = translate;
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setVgap( 10 );
		this.container.setId( "hotel_result" );
		
		this.rin_date = message.getIn_date();
		this.rout_date = message.getOut_date();

		this.txt_passengers = new Text();
		this.txt_passengers.minWidth( 200 );
		this.txt_passengers.maxWidth( 200 );
		this.txt_price = new Text();
		this.txt_model = new Text();
		this.txt_model.setWrappingWidth( 290 );
		this.txt_model.setId( "hotel_txt" );
		this.txt_state = new Text();
		this.view_transport = new ImageView();
		if( this.translate )
			this.button_reserve = new Button( "Reserve" );
		else
			this.button_reserve = new Button( "Reservar" );
		this.button_reserve.setMinWidth( 90 );

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_passengers, 0, 0 );
		super.setValignment( this.txt_passengers, VPos.TOP );
		this.container.add( this.txt_model, 0, 1, 1, 2 );
		super.setValignment( this.txt_model, VPos.TOP );
		this.container.add( this.view_transport, 1, 0, 1, 2 );
		this.container.add( this.button_reserve, 1, 2 );
		this.container.add( this.txt_price, 0, 2 );
	}

	/**
	 * @return the transport_register
	 */
	public Register getTransport_register() {
		return transport_register;
	}

	public void setTransport_register(Register transport_register) {
		this.transport_register = transport_register;
	}

	/**
	 * @return the idtransport
	 */
	public int getIdtransport() {
		return idtransport;
	}

	/**
	 * @param idtransport the idtransport to set
	 */
	public void setIdtransport(int idtransport) {
		this.transport_register.setItemid(idtransport);
		this.idtransport = idtransport;
	}

	/**
	 * @return the codhotel
	 */
	public int getCodhotel() {
		return codhotel;
	}

	/**
	 * @param codhotel the codhotel to set
	 */
	public void setCodhotel(int codhotel) {
		this.codhotel = codhotel;
	}

	public Text getTxt_price() {
		return txt_price;
	}

	public void setTxt_price( double price ) {
		this.transport_register.setPrice( price );
		this.txt_price.setText( "$" + price);
	}

	public Text getTxt_model() {
		return txt_model;
	}

	public void setTxt_model(String txt_model) {
		this.transport_register.setDescription( txt_model );
		this.txt_model.setText( txt_model );
	}

	public Text getTxt_passengers() {
		return txt_passengers;
	}

	public void setTxt_passengers( String txt_passengers) {
		this.transport_register.setItem( txt_passengers );
		this.txt_passengers.setText( txt_passengers );
	}

	public Button getButton_reserve() {
		return button_reserve;
	}

	public void setImageRoom( String path ) {
		this.img_transport = new Image( path, 90, 150, true, false );
		this.view_transport.setImage( this.img_transport );
	}

	public void setTxt_state( int state ) {
		int index = 0;
		this.state = state;
		ArrayList<LocalDate> in_dates = new ArrayList<LocalDate>();
		ArrayList<LocalDate> out_dates = new ArrayList<LocalDate>();

		if( this.state == 1 ){
			in_dates = SQLTransport.getInDates( this.idtransport );
			out_dates = SQLTransport.getOutDates( this.idtransport );
			if( in_dates.size() == 0 ){
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
					this.txt_model.setText( "Occupied until: " + out_dates.get(index) );
				}
				else{
					this.txt_state.setText( "Ocupado" );
					this.txt_model.setText( "Ocupado hasta: " + out_dates.get(index) );
				}
				this.txt_state.setId( "room_states" );
				this.setVisible( false );
				this.button_reserve.setDisable( true );
				break;
		}
	}
}
