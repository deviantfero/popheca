package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.RoomGrid;

import data.Reserve;

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

}
