package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.RoomGrid;

import data.Reserve;
import data.Room;

public class SQLRoom {

	public static ArrayList<RoomGrid> searchRoom( String hotelName, boolean translate, String state, Reserve message, int amount ) {
		ArrayList<RoomGrid> resultList = new ArrayList<RoomGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select R.codHabitacion, H.nomhotel, H.idHotel, R.maxPerson, R.prchabitacion, " + 
		"R.dethabitacion,R.estadoreserva, R.dethabitacioneng from hotel as H, habitacion as R, estadoreserva as " + 
		"S where H.idHotel=R.idHotel and R.estadoreserva = S.estadoreserva and H.nomhotel=? and R.maxperson <=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setString( 1, hotelName );
			search.setInt( 2, amount );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/any/" + rs.getString( "nomhotel" ) 
												+ "/" + rs.getInt( "maxperson" ) + ".jpg");

				RoomGrid result = new RoomGrid( translate, message );

				result.setCodroom( rs.getInt( "codhabitacion" ) );
				result.setCodhotel( rs.getInt( "idHotel" ) );
				result.setTxt_capacity( rs.getInt( "maxperson" ) );
				result.setTxt_price( rs.getDouble( "prchabitacion" ) );

				if( translate )
					result.setTxt_descr( rs.getString( "dethabitacioneng" ) );
				else
					result.setTxt_descr( rs.getString( "dethabitacion" ) );

				result.setTxt_state( rs.getInt( "estadoreserva" ) );

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

	public static void updateRoomState( int codhabitacion, int state ){
		PreparedStatement update = null;
		try{
			Connection c = SQLInteractor.connect();
			String newString = "update habitacion set estadoreserva=? where codhabitacion=?";
			update = c.prepareStatement( newString );
			update.setInt( 1, state ); 
			update.setInt( 2, codhabitacion ); 
			update.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while updating reserve 3" );
			System.err.println( e.getMessage() );
		}
		
	}

	public static String getRoomCapacity( int roomId, boolean translate ) {
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select maxperson from habitacion where codhabitacion=?"; 
		String returnString = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, roomId );
			ResultSet rs = search.executeQuery();
			rs.next();
			switch (rs.getInt( "maxperson" )){
				case 1:
					if( translate )
						returnString = "Single";
					else
						returnString = "Sencilla";
					break;
				case 2:
					if( translate )
						returnString = "Double";
					else
						returnString = "Doble";
					break;
				case 3:
					returnString = "Familiar";
					break;
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for room capacity" );
			System.err.println( e.getMessage() );
		}
		return returnString;
	}

	public static double getRoomPrice( int roomId ) {
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select prchabitacion from habitacion where codhabitacion=?"; 
		double price = 0;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, roomId );
			ResultSet rs = search.executeQuery();
			rs.next();
			price = rs.getDouble( "prchabitacion" );
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for room price" );
			System.err.println( e.getMessage() );
		}
		return price;
	}

	public static String getRoomDesc( int roomId, boolean translate ) {
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select dethabitacion, dethabitacioneng from habitacion where codhabitacion=?"; 
		String desc = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, roomId );
			ResultSet rs = search.executeQuery();
			rs.next();
			if( translate )
				desc = rs.getString( "dethabitacioneng" );
			else
				desc = rs.getString( "dethabitacion" );
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for room description" );
			System.err.println( e.getMessage() );
		}
		return desc;
	}

	public static ArrayList<Room> adminSearchRoom( String habitacion, boolean translate, int opt ){
		ArrayList<Room> resultList = new ArrayList<Room>();
		PreparedStatement search = null;
		String searchString = null;
		String basicTable = "select R.codhabitacion, R.prchabitacion, R.maxperson, "+
			"R.dethabitacion, R.dethabitacioneng, R.idhotel, H.nomhotel from habitacion as R, hotel as H" +
			" where R.idhotel = H.idhotel and ";
		int habitacionid = SQLInteractor.getStateID( habitacion.toLowerCase() );
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			switch( opt ) {
				case 0:
					searchString = basicTable + "prchabitacion <= ?";
					search = c.prepareStatement( searchString );
					search.setFloat( 1, Float.parseFloat( habitacion ));
					break;
				case 2:
					searchString = basicTable + "maxperson <= ?";
					try{
						habitacionid = Integer.parseInt( habitacion );
					}catch( Exception e ){
						habitacionid = 3;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, habitacionid );
					break;
				case 1:
					if( habitacion.matches("[a-zA-Z0-9]+"))
						searchString = basicTable + "lower(nomhotel) like '%"+habitacion.toLowerCase()+"%'";
					else
						searchString = basicTable;
					search = c.prepareStatement( searchString );
					break;
				case 3:
					searchString = basicTable + "codhabitacion=?";
					try{
						habitacionid = Integer.parseInt( habitacion );
					}catch( Exception e ){
						habitacionid = 1;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, habitacionid );
					break;
			}
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				Room result = new Room();
				result.setCodhabitacion( rs.getInt( "codhabitacion" ) );
				result.setMaxperson( rs.getInt( "maxperson" ) );
				result.setPrchabitacion( rs.getDouble( "prchabitacion" ) );
				result.setDethabitacioneng( rs.getString( "dethabitacioneng" ));
				result.setDethabitacion( rs.getString( "dethabitacion" ));
				result.setNomhotel( rs.getString( "nomhotel" ));
				result.setIdhotel( rs.getInt( "idhotel" ) );
	
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

	public static void addRoom( Room new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "insert into habitacion (maxperson, prchabitacion, dethabitacion, dethabitacioneng,"+
					" idhotel, estadoreserva) values ( ?, ?, ?, ?, ?, 0)";
			search = c.prepareStatement( searchString );
			search.setInt( 1, new_values.getMaxperson() );
			search.setDouble( 2, new_values.getPrchabitacion() );
			search.setString( 3, new_values.getDethabitacion() );
			search.setString( 4, new_values.getDethabitacioneng() );
			search.setInt( 5, new_values.getIdhotel() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void modifyRoom( Room new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "update habitacion set maxperson=?, prchabitacion=?, dethabitacion=?, dethabitacioneng=?, idhotel=? where codhabitacion=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, new_values.getMaxperson() );
			search.setDouble( 2, new_values.getPrchabitacion() );
			search.setString( 3, new_values.getDethabitacion() );
			search.setString( 4, new_values.getDethabitacioneng() );
			search.setInt( 5, new_values.getIdhotel() );
			search.setInt( 6, new_values.getCodhabitacion() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void deleteRoom( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from habitacion where codhabitacion=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
	}

}
