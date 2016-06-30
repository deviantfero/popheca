package data;

import java.lang.Math;

public class Transport {
	private String modelotransporte;
	private int numpasajeros;
	private int idhotel;
	private int codtransporte;
	private double prectransporte;
	private String nomhotel;

	/**
	 * @return the modelotransporte
	 */
	public String getModelotransporte() {
		return modelotransporte;
	}

	/**
	 * @param modelotransporte the modelotransporte to set
	 */
	public void setModelotransporte(String modelotransporte) {
		this.modelotransporte = modelotransporte;
	}

	/**
	 * @return the numpasajeros
	 */
	public int getNumpasajeros() {
		return numpasajeros;
	}

	/**
	 * @param numpasajeros the numpasajeros to set
	 */
	public void setNumpasajeros(int numpasajeros) {
		this.numpasajeros = numpasajeros;
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
	 * @return the prectransporte
	 */
	public double getPrectransporte() {
		return Math.round( prectransporte * 100.0 ) / 100.0;
	}

	/**
	 * @param prectransporte the prectransporte to set
	 */
	public void setPrectransporte(double prectransporte) {
		this.prectransporte = prectransporte;
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
}
