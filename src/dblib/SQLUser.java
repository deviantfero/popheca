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

public class SQLUser {

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
}
