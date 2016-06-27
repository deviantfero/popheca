package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import dblib.SQLReservation;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.text.*;

public class ReservationGrid extends GridPane {

	private boolean translate;
	private int codtransporte;
	private int codhabitacion;
	private int codreserva;

	private Text txt_hotel;
	private Text txt_descr;
	private Text txt_dates;
	private ImageView view_hotel;
	private Image img_hotel;
	private Button button_reserve;
	private GridPane container;

	public ReservationGrid( boolean translate ) {
		super();
		this.container = new GridPane();
		this.translate = translate;
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setVgap( 10 );
		this.container.setId( "hotel_result" );
		this.txt_dates = new Text();
		this.txt_dates.minWidth( 200 );
		this.txt_dates.maxWidth( 200 );
		this.txt_hotel = new Text();
		this.txt_descr = new Text();
		this.txt_descr.setWrappingWidth( 290 );
		this.txt_descr.setId( "hotel_txt" );
		this.view_hotel = new ImageView();
		if( this.translate )
			this.button_reserve = new Button( "Delete" );
		else
			this.button_reserve = new Button( "Borrar" );
		this.button_reserve.setMinWidth( 90 );

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_dates, 0, 0 );
		super.setValignment( this.txt_dates, VPos.TOP );
		this.container.add( this.txt_descr, 0, 1, 1, 2 );
		super.setValignment( this.txt_descr, VPos.TOP );
		this.container.add( this.view_hotel, 1, 0, 1, 2 );
		this.container.add( this.button_reserve, 1, 2 );
		this.container.add( this.txt_hotel, 0, 2 );
	}

	/**
	 * @return the codtransporte
	 */
	public int getCodtransporte() {
		return codtransporte;
	}

	/**
	 * @param codtransporte the codtransporte to set
	 */
	public void setCodtransporte(int codtransporte) {
		this.codtransporte = codtransporte;
	}

	/**
	 * @return the codhabitacion
	 */
	public int getCodhabitacion() {
		return codhabitacion;
	}

	/**
	 * @param codhabitacion the codhabitacion to set
	 */
	public void setCodhabitacion(int codhabitacion) {
		this.codhabitacion = codhabitacion;
	}

	/**
	 * @return the codreserva
	 */
	public int getCodreserva() {
		return codreserva;
	}

	/**
	 * @param codreserva the codreserva to set
	 */
	public void setCodreserva(int codreserva) {
		this.codreserva = codreserva;
	}

	public Text getTxt_hotel() {
		return txt_hotel;
	}

	public void setTxt_hotel( String hotel ) {
		this.txt_hotel.setText( hotel );
	}

	public Text getTxt_descr() {
		return txt_descr;
	}

	public void setTxt_descr(String txt_descr) {
		this.txt_descr.setText( txt_descr );
	}

	public Text getTxt_dates() {
		return txt_dates;
	}

	public void setTxt_dates( String txt_dates) {
		this.txt_dates.setText( txt_dates );
	}

	public Button getButton_reserve() {
		return button_reserve;
	}

	public void setImageRoom( String path ) {
		this.img_hotel = new Image( path, 90, 150, true, false );
		this.view_hotel.setImage( this.img_hotel );
	}
}
