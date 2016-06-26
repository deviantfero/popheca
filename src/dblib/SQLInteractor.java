package dblib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

import data.Reserve;
import data.Register;

public class SQLInteractor {

	public static Connection connect() {
		Connection c = null;
		try {
			Class.forName( "org.postgresql.Driver" );
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/nopheca","postgres","postgres"
			);
		}catch( Exception e ) {
			e.printStackTrace();
			System.err.println( "SQL::Failed to connect" );
		}
		if( c != null )
			System.out.println( "SQL::Connection Successful" );
		return c;
	}

	public static int getStateID( String statename ) {
		PreparedStatement search = null;
		String searchString = null;
		int stateid = 0;
		searchString = "select idestado from estado where lower(nomestado)=?"; 
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setString( 1, statename.toLowerCase() );
			ResultSet rs = search.executeQuery();
			rs.next();
			stateid = rs.getInt( "idestado" );
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for state's ID" );
			System.err.println( e.getMessage() );
		}
		return stateid;
	}

	public static String getStateName( int stateid ) {
		PreparedStatement search = null;
		String searchString = null;
		String name = null;
		searchString = "select nomestado from estado where idestado=?"; 
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, stateid );
			ResultSet rs = search.executeQuery();
			rs.next();
			name = rs.getString( "nomestado" );
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for state's ID" );
			System.err.println( e.getMessage() );
		}
		return name;
	}

	public static int getLastReserveID() {
		PreparedStatement search = null;
		String searchString = null;
		int reservaid = 0;
		searchString = "select codreserva from reserva order by codreserva"; 
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			ResultSet rs = search.executeQuery();
			while( rs.next() )
				reservaid = rs.getInt( "codreserva" );
			System.out.println( "ID::" + reservaid );
			if( reservaid == 0 )
				reservaid++;
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for reserve's ID" );
			System.err.println( e.getMessage() );
		}
		return reservaid;
	}

	public static void makeReservation( Reserve message ) {
		PreparedStatement search = null;
		String searchString = null;
		searchString = "SELECT makereserve( ?, ?, ?, ?, ?, ?, ?, ? )"; 
		try{
			Connection c = SQLInteractor.connect();
			search = c.prepareStatement( searchString );
			search.setDate(1, java.sql.Date.valueOf(message.getIn_date()) );
			search.setDate(2, java.sql.Date.valueOf(message.getOut_date()) );
			search.setInt( 3, message.getAdults() );
			search.setInt( 4, message.getKids() );
			search.setInt( 5, message.getAdults() + message.getKids() + message.getIdState() + message.getIdHotel() + SQLInteractor.getLastReserveID() );
			search.setInt( 6, message.getActiveUser().getId() );
			search.setInt( 7, message.getIdState() );
			search.setInt( 8, message.getIdHotel() );
			search.executeQuery();
			System.out.println( "SUCCESS" );
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while inserting1" );
			System.err.println( e.getMessage() );
		}
		try{
			Connection c = SQLInteractor.connect();
			String newString = "insert into reservaxhabitacion (codreserva, codhabitacion, canttipo) values ( ?, ?, ? )";
			search = c.prepareStatement( newString );
			search.setInt( 1, SQLInteractor.getLastReserveID());
			search.setInt( 2, message.getIdRoom() );
			search.setInt( 3, 1 );
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while inserting2" );
			System.err.println( e.getMessage() );
		}
		try{
			Connection c = SQLInteractor.connect();
			String newString = "update habitacion set estadoreserva=1 where codhabitacion=?";
			search = c.prepareStatement( newString );
			search.setInt( 1, message.getIdRoom());
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while updating reserve 3" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addFoodReservation( Register food_register ) {
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			String newString = "insert into reservaxplan (codreserva, codplan, cantidad) values ( ?, ?, ? )";
			search = c.prepareStatement( newString );
			search.setInt( 1, SQLInteractor.getLastReserveID());
			search.setInt( 2, food_register.getItemid() );
			search.setInt( 3, food_register.getKids() + food_register.getAdults() );
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while inserting2" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addDestinyReservation( Register register_destiny ) {
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			String newString = "insert into reservaxentrada (codreserva, codentrada, cantidad, fecha ) values ( ?, ?, ?, ? )";
			search = c.prepareStatement( newString );
			search.setInt( 1, SQLInteractor.getLastReserveID());
			search.setInt( 2, register_destiny.getItemid() );
			search.setInt( 3, register_destiny.getKids() + register_destiny.getAdults() );
			search.setDate( 4, Date.valueOf(register_destiny.getIn_date() ));
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while inserting2" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addTransportReservation( Register transport_register ) {
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			String newString = "update reserva set codtransporte=? where codreserva=?";
			search = c.prepareStatement( newString );
			search.setInt( 1, transport_register.getItemid() );
			search.setInt( 2, SQLInteractor.getLastReserveID());
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while inserting2" );
			System.err.println( e.getMessage() );
		}
		try{
			Connection c = SQLInteractor.connect();
			String newString = "update transporte set estadoreserva=1 where codtransporte=?";
			search = c.prepareStatement( newString );
			search.setInt( 1, transport_register.getItemid() );
			search.executeUpdate();
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::some error ocurred while updating reserve 3" );
			System.err.println( e.getMessage() );
		}
	}

	public static ArrayList<LocalDate> getInDates( int codhabitacion ) {
		PreparedStatement search = null;
		String searchString = null;
		ArrayList<LocalDate> in_dates = new ArrayList<LocalDate>();
		searchString = "select X.codhabitacion, X.codreserva, R.fechainicior, " +
		"R.fechafinr from reservaxhabitacion as X, reserva as R " +
		"where X.codreserva=R.codreserva and X.codhabitacion=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, codhabitacion );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				in_dates.add( rs.getDate( "fechainicior" ).toLocalDate() );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for state's ID" );
			System.err.println( e.getMessage() );
		}
		return in_dates;
	}

	public static ArrayList<LocalDate> getOutDates( int codhabitacion ) {
		PreparedStatement search = null;
		String searchString = null;
		ArrayList<LocalDate> out_dates = new ArrayList<LocalDate>();
		searchString = "select X.codhabitacion, X.codreserva, R.fechainicior, " +
		"R.fechafinr from reservaxhabitacion as X, reserva as R " +
		"where X.codreserva=R.codreserva and X.codhabitacion=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, codhabitacion );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				out_dates.add( rs.getDate( "fechafinr" ).toLocalDate() );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for state's ID" );
			System.err.println( e.getMessage() );
		}
		return out_dates;
	}
}
