package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.ReservationGrid;

import data.User;

public class SQLReservation {
	public static ArrayList<ReservationGrid> searchReservation( boolean translate ) {
		ArrayList<ReservationGrid> resultList = new ArrayList<ReservationGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select R.codreserva, R.fechainicior, R.fechafinr, R.numadultos, R.numninnos,H.nomhotel, RR.codhabitacion, T.modelotransporte," 
			+ "T.codtransporte, E.nombreentrada from reserva as R, hotel as H, reservaxhabitacion as RR, transporte as T, entrada as E, "
			+ "reservaxentrada as ER where idusuario=? and R.idhotel=H.idhotel and RR.codreserva=R.codreserva and R.codtransporte="
			+ "T.codtransporte and R.codreserva=ER.codreserva and ER.codentrada=E.codentrada";
		User activeUser = SQLUser.getActive();
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, activeUser.getId() );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/any"
												+ "/" + rs.getString( "nomhotel" )+ "/3.jpg");

				ReservationGrid result = new ReservationGrid( translate);

				result.setTxt_dates( rs.getDate( "fechainicior" ) + "  -  " + rs.getDate( "fechafinr" ) );
				result.setTxt_hotel( rs.getString( "nomhotel" ));
				result.setCodreserva( rs.getInt( "codreserva" ) );
				result.setCodtransporte( rs.getInt( "codtransporte" ) );
				result.setCodhabitacion( rs.getInt( "codhabitacion" ) );
				if(translate)
					result.setTxt_descr( "Transport: " + rs.getString( "modelotransporte" ) + "\n" +
							"Destiny: " + rs.getString("nombreentrada") + "\n"+
							"Kids: " + rs.getInt( "numninnos" ) + " " + "Adults: " + rs.getInt( "numadultos" ));
				else
					result.setTxt_descr( "Transporte: " + rs.getString( "modelotransporte" ) + "\n" +
							"Destino: " + rs.getString("nombreentrada") + "\n"+
							"Niños: " + rs.getInt( "numninnos" ) + " " + "Adultos: " + rs.getInt( "numadultos" ));
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

	public static void deleteReservation( int codreserva, int codhabitacion, int codtransporte ) {
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setTrue = null;
			String updateString = "select deletereserve( ?, ?, ? )";
			setTrue = c.prepareStatement( updateString );
			setTrue.setInt( 1, codreserva );
			setTrue.setInt( 2, codhabitacion );
			setTrue.setInt( 3, codtransporte );
			setTrue.executeQuery();
			c.close();
			System.out.println( "SQL::Se borro reservacion" );
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
	}
}
