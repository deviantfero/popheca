package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.RoomGrid;

import data.Reserve;

public class SQLRoom {

	public static ArrayList<RoomGrid> searchRoom( String hotelName, boolean translate, String state, Reserve message ) {
		ArrayList<RoomGrid> resultList = new ArrayList<RoomGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select R.codHabitacion, H.nomhotel, H.idHotel, R.maxPerson, R.prchabitacion, " + 
		"R.dethabitacion,R.estadoreserva, R.dethabitacioneng from hotel as H, habitacion as R, estadoreserva as " + 
		"S where H.idHotel=R.idHotel and R.estadoreserva = S.estadoreserva and H.nomhotel=?;";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setString( 1, hotelName );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/" + state.toLowerCase() 
												+ "/" + rs.getString( "nomhotel" ) 
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

}
