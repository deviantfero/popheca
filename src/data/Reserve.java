package data;

import java.time.LocalDate;

import dblib.SQLInteractor;
import javafx.scene.control.DatePicker;

public class Reserve {
	private DatePicker in_date;
	private DatePicker out_date;
	private int adults;
	private int kids;
	private int idState;
	private int idHotel;
	private int idRoom;
	private User activeUser;

	public Reserve( DatePicker in_date, DatePicker out_date ) {
		this.in_date = in_date;
		this.out_date = out_date;
		this.activeUser = SQLInteractor.getActive();
	}

	public LocalDate getIn_date() {
		return in_date.getValue();
	}
	public void setIn_date(DatePicker in_date) {
		this.in_date = in_date;
	}

	public LocalDate getOut_date() {
		return out_date.getValue();
	}

	public void setOut_date(DatePicker out_date) {
		this.out_date = out_date;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public int getIdState() {
		return idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}
}
