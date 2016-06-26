package gui;

import data.Register;


import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.text.*;

public class FoodGrid extends GridPane {

	private boolean translate;
	private Register food_register;
	private int idplan;
	private int codhotel;

	private Text txt_price;
	private Text txt_descr;
	private Text txt_plan;
	private ImageView view_transport;
	private Image img_transport;
	private Button button_reserve;
	private GridPane container;

	public FoodGrid( boolean translate, Register food_register ) {
		super();
		this.container = new GridPane();
		this.food_register = food_register;
		this.translate = translate;
		ColumnConstraints column = new ColumnConstraints( 350 );
		this.container.getColumnConstraints().add( column );
		this.container.setHgap( 20 );
		this.container.setVgap( 10 );
		this.container.setId( "hotel_result" );
		this.txt_plan = new Text();
		this.txt_plan.minWidth( 200 );
		this.txt_plan.maxWidth( 200 );
		this.txt_price = new Text();
		this.txt_descr = new Text();
		this.txt_descr.setWrappingWidth( 290 );
		this.txt_descr.setId( "hotel_txt" );
		this.view_transport = new ImageView();
		if( this.translate )
			this.button_reserve = new Button( "Reserve" );
		else
			this.button_reserve = new Button( "Reservar" );
		this.button_reserve.setMinWidth( 90 );

		super.add( this.container, 0, 0 );
		super.setMargin( this.container, new Insets( 10, 100, 10, 80 ) );
		this.container.add( this.txt_plan, 0, 0 );
		super.setValignment( this.txt_plan, VPos.TOP );
		this.container.add( this.txt_descr, 0, 1, 1, 2 );
		super.setValignment( this.txt_descr, VPos.TOP );
		this.container.add( this.view_transport, 1, 0, 1, 2 );
		this.container.add( this.button_reserve, 1, 2 );
		this.container.add( this.txt_price, 0, 2 );
	}

	/**
	 * @return the food_register
	 */
	public Register getFood_register() {
		return food_register;
	}

	public void setFood_register(Register food_register) {
		this.food_register = food_register;
	}

	/**
	 * @return the idplan
	 */
	public int getIdplan() {
		return idplan;
	}

	/**
	 * @param idplan the idplan to set
	 */
	public void setIdplan(int idplan) {
		this.food_register.setItemid(idplan);
		this.idplan = idplan;
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
		this.food_register.setPrice( price );
		this.txt_price.setText( "$" + price);
	}

	public Text getTxt_descr() {
		return txt_descr;
	}

	public void setTxt_descr(String txt_descr) {
		this.food_register.setDescription( txt_descr );
		this.txt_descr.setText( txt_descr );
	}

	public Text getTxt_plan() {
		return txt_plan;
	}

	public void setTxt_plan( String txt_plan) {
		this.food_register.setItem( txt_plan );
		this.txt_plan.setText( txt_plan );
	}

	public Button getButton_reserve() {
		return button_reserve;
	}

	public void setImageRoom( String path ) {
		this.img_transport = new Image( path, 90, 150, true, false );
		this.view_transport.setImage( this.img_transport );
	}
}
