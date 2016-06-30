package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.HotelGrid;

import data.HotelChecker;
import data.Hotel;

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
				String path = new String( "/gui/styles/img/any/" +
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
			searchString = "select idhotel from hotel where lower(nomhotel)=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, hotel_name.toLowerCase() );
			ResultSet rs = search.executeQuery();
			rs.next();
			idhotel = rs.getInt( "idhotel" );
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
		return idhotel;
	}

	public static String searchHotelName( int idhotel ) {
		String searchString = null;
		PreparedStatement search = null;
		String namehotel = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "select nomhotel from hotel where idhotel=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, idhotel );
			ResultSet rs = search.executeQuery();
			rs.next();
			namehotel = rs.getString( "nomhotel" );
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
		return namehotel;
	}

	public static void deleteHotel( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from hotel where idhotel=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
	}

	public static void modifyHotel( Hotel new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "update hotel set nomhotel=?, direchotel=?, lvlhotel=?, idestado=?, descrhotel=?, descrhoteleng=? where idhotel=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomhotel() );
			search.setString( 2, new_values.getDirechotel() );
			search.setInt( 3, new_values.getLvlhotel() );
			search.setInt( 4, new_values.getIdestado() );
			search.setString( 5, new_values.getDescrhotel() );
			search.setString( 6, new_values.getDescrhoteleng() );
			search.setInt( 7, new_values.getIdhotel() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addHotel( Hotel new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "insert into hotel (nomhotel, direchotel, lvlhotel, idestado,"+
					" descrhotel, descrhoteleng) values ( ?, ?, ?, ?, ?, ? )";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomhotel() );
			search.setString( 2, new_values.getDirechotel() );
			search.setInt( 3, new_values.getLvlhotel() );
			search.setInt( 4, new_values.getIdestado() );
			search.setString( 5, new_values.getDescrhotel() );
			search.setString( 6, new_values.getDescrhoteleng() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
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

	public static ArrayList<Hotel> adminSearchHotel( String state, boolean translate, int opt ){
		ArrayList<Hotel> resultList = new ArrayList<Hotel>();
		PreparedStatement search = null;
		String searchString = null;
		int stateid = SQLInteractor.getStateID( state.toLowerCase() );
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			switch( opt ) {
				case 0:
					searchString = "select * from hotel where idestado=?";
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 1:
					searchString = "select * from hotel where lvlhotel=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 5;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 2:
					if( state.matches("[a-zA-Z0-9]+"))
						searchString = "select * from hotel where lower(nomhotel) like '%"+state.toLowerCase()+"%'";
					else
						searchString = "select * from hotel";
					search = c.prepareStatement( searchString );
					break;
				case 3:
					searchString = "select * from hotel where idhotel=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 1;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
			}
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				Hotel result = new Hotel();
				result.setIdestado( stateid );
				result.setNomhotel( rs.getString( "nomhotel" ) );
				result.setIdhotel( rs.getInt( "idhotel" ) );
				result.setDirechotel( rs.getString( "direchotel" ) );
				result.setLvlhotel( rs.getInt( "lvlhotel" ) );
				result.setDescrhoteleng( rs.getString( "descrhoteleng" ) );
				result.setDescrhotel( rs.getString( "descrhotel" ) );
				stateid = rs.getInt( "idestado" );
				result.setNomestado( SQLInteractor.getStateName( stateid ) );
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

}
