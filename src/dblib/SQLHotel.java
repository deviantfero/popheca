package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.HotelGrid;

import data.HotelChecker;

public class SQLHotel {

	public static ArrayList<HotelGrid> searchHotel( String state, boolean translate ){
		ArrayList<HotelGrid> resultList = new ArrayList<HotelGrid>();
		PreparedStatement search = null;
		String searchString = null;
		if( state.equals( "Any" ) )
			searchString = "select * from hotel";
		else
			searchString = "select * from estado E, hotel H where lower(E.nomestado)=? and E.idestado = H.idestado";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			if( !state.equals( "Any" ) )
				search.setString( 1, state.toLowerCase() );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				HotelGrid result = new HotelGrid( translate );
				String path = new String( "/gui/styles/img/" +
							state.toLowerCase() + "/" +
							rs.getString( "nomhotel" ) + "/0.jpg" );
				String pathr = new String( "/gui/styles/img/" + rs.getInt( "lvlhotel" ) + ".png");
				result.setTxt_state( state );
				result.setTxt_hname( rs.getString( "nomhotel" ) );
				result.setHotel_id( rs.getInt( "idhotel" ) );
				result.setTxt_direction( rs.getString( "direchotel" ) );
				if( translate )
					result.setTxt_descr( rs.getString( "descrhoteleng" ) );
				else
					result.setTxt_descr( rs.getString( "descrhotel" ) );

				try{
					result.setImageHotel( path.replaceAll( " ", "\\ " ) );
				}catch( Exception e ){
					System.err.println( "FX::Could not load image in: " + path );
					result.setImageHotel( "/gui/styles/img/backback.jpg" );
				}
				try{
					result.setImageRating( pathr );
				}catch( Exception e ){
					System.err.println( "FX::Could not load image in: " + pathr );
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

	public static int searchHotelId( String hotel_name ) {
		String searchString = null;
		PreparedStatement search = null;
		int idhotel = 0;

		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "select id from hotel where nomhotel=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, hotel_name );
			ResultSet rs = search.executeQuery();
			rs.next();
			idhotel = rs.getInt( "idhotel" );
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
		return idhotel;
	}

	public static ArrayList<HotelChecker> getUserStays( int idusuario ) {
		PreparedStatement search = null;
		String searchString = null;
		ArrayList<HotelChecker> userStays = new ArrayList<HotelChecker>();
		searchString = "select r.idhotel, R.fechainicior, R.fechafinr from reservaxhabitacion as X, " +
		"reserva as R where X.codreserva=R.codreserva and R.idusuario=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, idusuario );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				HotelChecker temp_checker = new HotelChecker();
				temp_checker.setIn_date( rs.getDate( "fechainicior" ).toLocalDate() );
				temp_checker.setOut_date( rs.getDate( "fechafinr" ).toLocalDate() );
				temp_checker.setId_hotel( rs.getInt( "idhotel" ) );
				userStays.add( temp_checker );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for state's ID" );
			System.err.println( e.getMessage() );
		}
		return userStays;
	}

}
