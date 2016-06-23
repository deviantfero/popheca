package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.text.*;

public class HotelGrid extends GridPane {
	private Text txt_hname;
	private Text txt_state;
	private Text txt_descr;
	private Text txt_direction;
	private ImageView view_hotel;
	private Image img_hotel;
	private Image img_rating;
	private ImageView view_rating;
	private Button button_select;
	private GridPane container;

	public HotelGrid( boolean translate ) {
		super();
		this.container = new GridPane();
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setVgap( 10 );
		this.container.setId( "hotel_result" );
		this.txt_hname = new Text();
		this.txt_hname.minWidth( 200 );
		this.txt_hname.maxWidth( 200 );
		this.txt_direction = new Text();
		this.txt_direction.setId( "hotel_txt" );
		this.txt_direction.minWidth( 200 );
		this.txt_direction.maxWidth( 200 );
		this.txt_state = new Text();
		this.txt_descr = new Text();
		this.txt_descr.setWrappingWidth( 290 );
		this.txt_descr.setId( "hotel_txt" );
		this.view_hotel = new ImageView();
		this.view_rating = new ImageView();
		if( translate )
			this.button_select = new Button( "Select" );
		else
			this.button_select = new Button( "Seleccionar" );
		this.button_select.setMinWidth( 90 );

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_hname, 0, 0 );
		super.setValignment( this.txt_hname, VPos.TOP );
		this.container.add( this.txt_descr, 0, 1, 1, 2 );
		super.setValignment( this.txt_descr, VPos.TOP );
		this.container.add( this.txt_direction, 0, 3 );
		this.container.add( this.view_hotel, 1, 0, 1, 2 );
		this.container.add( this.button_select, 1, 2 );
		this.container.add( this.view_rating, 1, 3 );
	}

	public void setTxt_hname(String txt_hname) {
		this.txt_hname.setText( txt_hname );
	}

	public void setTxt_state(String txt_state) {
		this.txt_state.setText( txt_state );
	}

	public void setTxt_descr(String txt_descr) {
		this.txt_descr.setText( txt_descr );
	}

	public String getTxt_hname() {
		return txt_hname.getText();
	}

	public String getTxt_state() {
		return txt_state.getText();
	}

	public String getTxt_descr() {
		return txt_descr.getText();
	}

	public Button getButton_select() {
		return button_select;
	}

	public void setTxt_direction(String txt_direction) {
		this.txt_direction.setText( txt_direction );
	}

	public void setImageHotel( String path ) {
		this.img_hotel = new Image( path, 90, 150, true, false );
		this.view_hotel.setImage( this.img_hotel );
	}
	
	public void setImageRating( String path ) {
		this.img_rating = new Image( path, 90, 50, true, false );
		this.view_rating.setImage( this.img_rating );
	}
}
