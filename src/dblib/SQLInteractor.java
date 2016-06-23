package dblib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.HotelGrid;
import gui.RoomGrid;

import data.Reserve;
import data.User;

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

	public static boolean searchUser( String email, String password ) {
		password = MD5.encrypt( password );
		PreparedStatement search = null;
		String remail = null;
		String searchString = "SELECT * FROM usuario where emailusuario" +
		"= ? and passUsuario = ?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			//this prevents the auto submit of some queries
			search = c.prepareStatement( searchString );
			search.setString( 1, email );
			search.setString( 2, password );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				remail = rs.getString( "emailusuario" );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::There was some error" );
		}
		if( remail != null ){
			System.out.println( "SQL::User found" );
			return true;
		}else{
			System.err.println( "SQL::User not found" );
			return false;
		}
	}

	public static boolean searchUser( String email ) {
		PreparedStatement search = null;
		String remail = null;
		String searchString = "SELECT * FROM usuario where emailusuario" +
		"= ?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			//this prevents the auto submit of some queries
			search = c.prepareStatement( searchString );
			search.setString( 1, email );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				remail = rs.getString( "emailusuario" );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::There was some error" );
		}
		if( remail != null ){
			System.out.println( "SQL::User found" );
			return true;
		}else{
			System.err.println( "SQL::User not found" );
			return false;
		}
	}

	public static void setActive( String email, String password ) {
		try{
			password = MD5.encrypt( password );
			Connection c = SQLInteractor.connect();
			PreparedStatement setTrue = null;
			String updateString = "UPDATE usuario SET cnxusuario=true WHERE emailusuario=? and passUsuario=?";
			setTrue = c.prepareStatement( updateString );
			setTrue.setString( 1, email );
			setTrue.setString( 2, password );
			setTrue.executeUpdate();
			c.close();
			System.out.println( "SQL::Login Exitoso, usuario activo" );
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
	}
	
	public static void closeUsers() {
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setTrue = null;
			String updateString = "UPDATE usuario SET cnxusuario=false";
			setTrue = c.prepareStatement( updateString );
			setTrue.executeUpdate();
			c.close();
			System.out.println( "SQL::All users offline" );
		}catch( Exception e ) {
			System.err.println( "SQL::Error updating state" );
		}
	}

	public static void registerUser( User newUser ) {
		try{
			Connection c = SQLInteractor.connect();	
			PreparedStatement registerNew = null;
			String registerString = "SELECT register( ?, ?, ?, ? )";
			registerNew = c.prepareStatement( registerString );
			registerNew.setString( 1, newUser.getName() );
			registerNew.setString( 2, newUser.getLastname() );
			registerNew.setString( 3, newUser.getEmail() );
			registerNew.setString( 4, MD5.encrypt(newUser.getPassword()) );
			registerNew.executeQuery();
			c.close();
			System.out.println( "SQL::Registered new user" );
		}catch( Exception e ) {
			System.out.println( e.getMessage() );
			System.err.println( "SQL::Failed to register new user" );
		}
	}

	public static User getActive() {
		PreparedStatement search = null;
		User activeUser = null;
		String searchString = "SELECT * FROM usuario where cnxusuario" +
		"= ?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			//this prevents the auto submit of some queries
			search = c.prepareStatement( searchString );
			search.setBoolean( 1, true );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				activeUser = new User();
				activeUser.setName( rs.getString( "nomusuario" ) );
				activeUser.setLastname( rs.getString( "apeusuario" ) );
				activeUser.setEmail( rs.getString( "emailusuario" ) );
				activeUser.setRole( rs.getInt( "rol" ) );
				activeUser.setId( rs.getInt( "idusuario" ) );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::There was some error" );
			return null;
		}
		if( activeUser != null ){
			System.out.println( "SQL::User found" );
			return activeUser;
		}else{
			System.err.println( "SQL::User not found" );
			return null;
		}
	}

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

	public static ArrayList<RoomGrid> searchRoom( String hotelName, boolean translate, String state, Reserve message ) {
		ArrayList<RoomGrid> resultList = new ArrayList<RoomGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select R.codHabitacion, H.nomhotel, H.idHotel, R.maxPerson, R.prchabitacion, " + 
		"R.dethabitacion,R.estadoreserva from hotel as H, habitacion as R, estadoreserva as " + 
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
				result.setTxt_state( rs.getInt( "estadoreserva" ) );
				result.setTxt_price( rs.getDouble( "prchabitacion" ) );
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

	public static int getStateID( String statename ) {
		PreparedStatement search = null;
		String searchString = null;
		int stateid = 0;
		searchString = "select idestado from estado where nomestado=?"; 
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setString( 1, statename );
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
			search.setInt( 5, message.getAdults() + message.getKids() + message.getIdState() + message.getIdHotel() );
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
	}
}
