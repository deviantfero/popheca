package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDate;

import data.Reserve;
import data.Register;
import gui.TransportGrid;
import data.Transport;

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

	public static ArrayList<Transport> adminSearchTransport( String state, int opt ){
		ArrayList<Transport> resultList = new ArrayList<Transport>();
		PreparedStatement search = null;
		String searchString = null;
		String basicTable = "select T.codtransporte, T.modelotransporte, T.prectransporte, H.nomhotel, H.idhotel, T.numpasajeros from transporte as T, hotel as H where H.idhotel=T.idhotel ";
		int stateid = SQLInteractor.getStateID( state.toLowerCase() );
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			switch( opt ) {
				case 0:
					if( state.matches("[a-zA-Z0-9]+"))
						searchString = basicTable + "and lower(nomhotel) like '%"+state.toLowerCase()+"%'";
					else
						searchString = basicTable;
					search = c.prepareStatement( searchString );
					break;
				case 1:
					searchString = basicTable +"and numpasajeros=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 4;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 2:
					if( state.matches("[a-zA-Z0-9]+"))
						searchString = basicTable +"and lower(modelotransporte) like '%"+state.toLowerCase()+"%'";
					else
						searchString = basicTable;
					search = c.prepareStatement( searchString );
					break;
				case 3:
					searchString = basicTable + "and codtransporte=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 0;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 4:
					searchString = basicTable + "and prectransporte<=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 0;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
			}
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				Transport result = new Transport();
				result.setNomhotel( rs.getString( "nomhotel" ) );
				result.setIdhotel( rs.getInt( "idhotel" ) );
				result.setModelotransporte( rs.getString( "modelotransporte" ) );
				result.setNumpasajeros( rs.getInt( "numpasajeros" ) );
				result.setPrectransporte( rs.getDouble( "prectransporte" ) );
				result.setCodtransporte( rs.getInt( "codtransporte" ) );
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

	public static void modifyTransport( Transport new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "update transporte set modelotransporte=?, prectransporte=?, numpasajeros=?, idhotel=? where codtransporte=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getModelotransporte() );
			search.setDouble( 2, new_values.getPrectransporte() );
			search.setInt( 3, new_values.getNumpasajeros() );
			search.setInt( 4, new_values.getIdhotel() );
			search.setInt( 5, new_values.getCodtransporte() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addTransport( Transport new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "insert into transporte (modelotransporte, prectransporte, numpasajeros, idhotel,"+
					"estadoreserva) values ( ?, ?, ?, ?, 0 )";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getModelotransporte() );
			search.setDouble( 2, new_values.getPrectransporte() );
			search.setInt( 3, new_values.getNumpasajeros() );
			search.setInt( 4, new_values.getIdhotel() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void deleteTransport( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from transporte where codtransporte=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such transport" );
		}
	}

}
