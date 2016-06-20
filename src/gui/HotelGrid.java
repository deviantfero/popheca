package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.*;

public class HotelGrid extends GridPane {
	private Text txt_hname;
	private Text txt_state;
	private Text txt_descr;
	private Text txt_engdescr;
	private Image rating_img;
	private ImageView view_hotel;
	private Image img_hotel;
	private ImageView view_rating;
	private Button button_select;
	private GridPane container;

	public HotelGrid( boolean translate ) {
		super();
		this.container = new GridPane();
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setId( "hotel_result" );
		this.txt_hname = new Text();
		this.txt_hname.minWidth( 200 );
		this.txt_hname.maxWidth( 200 );
		this.txt_state = new Text();
		this.txt_descr = new Text();
		this.txt_engdescr = new Text();
		this.view_hotel = new ImageView();
		this.view_rating = new ImageView();
		if( translate )
			this.button_select = new Button( "Select" );
		else
			this.button_select = new Button( "Seleccionar" );
		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_hname, 0, 0 );
		this.container.add( this.view_rating, 0, 1 );
		this.container.add( this.view_hotel, 1, 0 );
		this.container.add( this.button_select, 1, 0 );
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

	public void setTxt_engdescr(String txt_engdescr) {
		this.txt_engdescr.setText( txt_engdescr );
	}

}
