package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDate;

import data.Reserve;
import data.Register;
import gui.TransportGrid;

public class SQLTransport {

	public static ArrayList<TransportGrid> searchTransport( int hotelId, boolean translate, Reserve message ) {
		ArrayList<TransportGrid> resultList = new ArrayList<TransportGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select * from transporte where idhotel=? order by estadoreserva";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, hotelId );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/transport"
												+ "/" + rs.getString( "modelotransporte" ).toLowerCase() + ".png");

				TransportGrid result = new TransportGrid( translate, new Register(), message );

				result.setTxt_price( rs.getDouble( "prectransporte" ) );
				result.setIdtransport( rs.getInt( "codtransporte" ) );
				result.setTxt_passengers( rs.getString( "modelotransporte" ) );
				result.setTxt_state( rs.getInt( "estadoreserva" ) );
				if( translate )
					result.setTxt_model( "Passengers: " + rs.getInt( "numpasajeros" ) );
				else
					result.setTxt_model( "Pasajeros: " + rs.getInt( "numpasajeros" ) );
				try{
					result.setImageRoom( image_path.replaceAll( " ", "\\ " ) );
				}catch( Exception e ){
					System.err.println( "FX::Could not load image in: " + image_path );
					result.setImageRoom( "/gui/styles/img/backback.jpg" );
				}
				resultList.add( result );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for hotels" );
			System.err.println( e.getMessage() );
			return null;
		}
		return resultList;
	}

	public static ArrayList<LocalDate> getInDates( int codtransporte ) {
		PreparedStatement search = null;
		String searchString = null;
		ArrayList<LocalDate> in_dates = new ArrayList<LocalDate>();
		searchString = "select * from reserva where codtransporte=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, codtransporte );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				in_dates.add( rs.getDate( "fechainicior" ).toLocalDate() );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for transport in dates" );
			System.err.println( e.getMessage() );
		}
		return in_dates;
	}

	public static ArrayList<LocalDate> getOutDates( int codtransporte ) {
		PreparedStatement search = null;
		String searchString = null;
		ArrayList<LocalDate> out_dates = new ArrayList<LocalDate>();
		searchString = "select * from reserva where codtransporte=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, codtransporte );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				out_dates.add( rs.getDate( "fechafinr" ).toLocalDate() );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for transport out dates" );
			System.err.println( e.getMessage() );
		}
		return out_dates;
	}
}
