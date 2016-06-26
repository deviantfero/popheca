package data;

import java.time.LocalDate;

public class Register {
	private LocalDate in_date;
	private LocalDate out_date;

	private String description;
	private String item;

	private int adults;
	private int kids;
	private int itemid;
	private double price;

	/**
	 * @return the in_date
	 */
	public LocalDate getIn_date() {
		return in_date;
	}

	/**
	 * @param in_date the in_date to set
	 */
	public void setIn_date(LocalDate in_date) {
		this.in_date = in_date;
	}

	/**
	 * @return the out_date
	 */
	public LocalDate getOut_date() {
		return out_date;
	}

	/**
	 * @param out_date the out_date to set
	 */
	public void setOut_date(LocalDate out_date) {
		this.out_date = out_date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the adults
	 */
	public int getAdults() {
		return adults;
	}

	/**
	 * @param adults the adults to set
	 */
	public void setAdults(int adults) {
		this.adults = adults;
	}

	/**
	 * @return the kids
	 */
	public int getKids() {
		return kids;
	}

	/**
	 * @param kids the kids to set
	 */
	public void setKids(int kids) {
		this.kids = kids;
	}

	/**
	 * @return the itemid
	 */
	public int getItemid() {
		return itemid;
	}

	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

}
