package data;

import java.time.LocalDate;

public class HotelChecker {
	private LocalDate in_date;
	private LocalDate out_date;
	private int id_hotel;

	public HotelChecker( LocalDate in_date, LocalDate out_date, int id ) {
		this.in_date = in_date;
		this.out_date = out_date;
		this.id_hotel = id;
	}

	public HotelChecker(){}

	public LocalDate getIn_date() {
		return in_date;
	}

	public void setIn_date(LocalDate in_date) {
		this.in_date = in_date;
	}

	public LocalDate getOut_date() {
		return out_date;
	}

	public void setOut_date(LocalDate out_date) {
		this.out_date = out_date;
	}

	public int getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}
}
