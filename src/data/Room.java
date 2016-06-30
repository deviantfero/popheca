package data;
import java.lang.Math;

public class Room {

	private int codhabitacion;
	private int maxperson;
	private double prchabitacion;
	private String dethabitacion;
	private String dethabitacioneng;
	private String nomhotel;
	private int estadoreserva;
	private int idhotel = 0;

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
	 * @return the maxperson
	 */
	public int getMaxperson() {
		return maxperson;
	}

	/**
	 * @param maxperson the maxperson to set
	 */
	public void setMaxperson(int maxperson) {
		this.maxperson = maxperson;
	}

	/**
	 * @return the prchabitacion
	 */
	public double getPrchabitacion() {
		return Math.round(prchabitacion * 100.0)/100.0;
	}

	/**
	 * @param prchabitacion the prchabitacion to set
	 */
	public void setPrchabitacion(double prchabitacion) {
		this.prchabitacion = prchabitacion;
	}

	/**
	 * @return the nomhotel
	 */
	public String getNomhotel() {
		return nomhotel;
	}

	/**
	 * @param nomhotel the nomhotel to set
	 */
	public void setNomhotel(String nomhotel) {
		this.nomhotel = nomhotel;
	}

	/**
	 * @return the dethabitacion
	 */
	public String getDethabitacion() {
		return dethabitacion;
	}

	/**
	 * @param dethabitacion the dethabitacion to set
	 */
	public void setDethabitacion(String dethabitacion) {
		this.dethabitacion = dethabitacion;
	}

	/**
	 * @return the dethabitacioneng
	 */
	public String getDethabitacioneng() {
		return dethabitacioneng;
	}

	/**
	 * @param dethabitacioneng the dethabitacioneng to set
	 */
	public void setDethabitacioneng(String dethabitacioneng) {
		this.dethabitacioneng = dethabitacioneng;
	}

	/**
	 * @return the estadoreserva
	 */
	public int getEstadoreserva() {
		return estadoreserva;
	}

	/**
	 * @param estadoreserva the estadoreserva to set
	 */
	public void setEstadoreserva(int estadoreserva) {
		this.estadoreserva = estadoreserva;
	}

	/**
	 * @return the idhotel
	 */
	public int getIdhotel() {
		return idhotel;
	}

	/**
	 * @param idhotel the idhotel to set
	 */
	public void setIdhotel(int idhotel) {
		this.idhotel = idhotel;
	}

}
