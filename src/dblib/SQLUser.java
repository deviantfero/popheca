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

	public static void setName( String name, boolean lastname ) {
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setName = null;
			User activeUser = SQLUser.getActive();
			String updateString = null;
			if( lastname )
				updateString = "UPDATE usuario SET apeusuario=? WHERE cnxusuario=true and idusuario=?";
			else
				updateString = "UPDATE usuario SET nomusuario=? WHERE cnxusuario=true and idusuario=?";
			setName = c.prepareStatement( updateString );
			setName.setString( 1, name );
			setName.setInt( 2, activeUser.getId() );
			setName.executeUpdate();
			c.close();
			if( lastname )
				System.out.println( "SQL::Apellido cambiado con exito" );
			else
				System.out.println( "SQL::Nombre cambiado con exito" );
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
	}

	public static void setEmail( String email ) {
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setEmail = null;
			User activeUser = SQLUser.getActive();
			String updateString = "UPDATE usuario SET emailusuario=? WHERE cnxusuario=true and idusuario=?";
			setEmail = c.prepareStatement( updateString );
			setEmail.setString( 1, email );
			setEmail.setInt( 2, activeUser.getId() );
			setEmail.executeUpdate();
			c.close();
			System.out.println( "SQL::Email cambiado con exito" );
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
	}

	public static boolean setPass( String oldPass, String newPass ) {
		boolean returnval = false;
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setPass = null;
			User activeUser = SQLUser.getActive();
			String updateString = "UPDATE usuario SET passusuario=? WHERE passusuario=? and idusuario=?";
			setPass = c.prepareStatement( updateString );
			setPass.setString( 1, MD5.encrypt(newPass) );
			setPass.setString( 2, MD5.encrypt(oldPass) );
			setPass.setInt( 3, activeUser.getId() );
			if( SQLUser.searchUser(activeUser.getEmail(), oldPass ) ){
				setPass.executeUpdate();
				System.out.println( "SQL::Pass cambiado con exito" );
				returnval = true;
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
		return returnval;
	}

	public static void setRole( int new_role, User adminSent ) {
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setPass = null;
			String updateString = "UPDATE usuario SET rol=? WHERE idusuario=?";
			setPass = c.prepareStatement( updateString );
			setPass.setInt( 1, new_role );
			setPass.setInt( 2, adminSent.getId() );
			setPass.executeUpdate();
			System.out.println( "SQL::Rol cambiado con exito" );
			c.close();
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
	}
	
	public static boolean adminsetPass( String newPass, User adminSent ) {
		boolean returnval = false;
		try{
			Connection c = SQLInteractor.connect();
			PreparedStatement setPass = null;
			User activeUser = adminSent;
			String updateString = "UPDATE usuario SET passusuario=? WHERE idusuario=?";
			setPass = c.prepareStatement( updateString );
			setPass.setString( 1, MD5.encrypt(newPass) );
			setPass.setInt( 2, activeUser.getId() );
			setPass.executeUpdate();
			System.out.println( "SQL::Pass cambiado con exito" );
			returnval = true;
			c.close();
		}catch( Exception e ) {
			System.err.println( e.getMessage() );
			System.err.println( "SQL::Error updating state" );
		}
		return returnval;
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

	public static void deleteUser( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from usuario where idusuario=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such user" );
		}
	}

	public static ArrayList<User> adminSearchUser( String statements, boolean translate, int opt ){
		ArrayList<User> resultList = new ArrayList<User>();
		PreparedStatement search = null;
		String searchString = null;
		int num = 0;
		if( statements.matches("[0-9]*" ))
			num = Integer.parseInt( statements );
		try{
			Connection c = SQLInteractor.connect();
			switch( opt ) {
				case 0:
					if( statements.matches("[a-zA-Z0-9]+"))
						searchString = "select * from usuario where lower(nomusuario) like '%"+statements.toLowerCase()+"%'";
					else
						searchString = "select * usuario hotel";
					search = c.prepareStatement( searchString );
					break;
				case 1:
					searchString = "select * from usuario where idusuario=?";
					search = c.prepareStatement( searchString );
					search.setInt( 1, num );
					break;
				case 2:
					if( statements.matches("[a-zA-Z0-9]+"))
						searchString = "select * from usuario where lower(apeusuario) like '%"+statements.toLowerCase()+"%'";
					else
						searchString = "select * from usuario";
					search = c.prepareStatement( searchString );
					break;
				case 3:
					if( statements.matches("[a-zA-Z0-9]+"))
						searchString = "select * from usuario where lower(emailusuario) like '%"+statements.toLowerCase()+"%'";
					else
						searchString = "select * from usuario";
					search = c.prepareStatement( searchString );
					break;
				case 4:
					searchString = "select * from usuario where rol=?";
					search = c.prepareStatement( searchString );
					search.setInt( 1, num );
					break;
			}
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				User result = new User();
				result.setId( rs.getInt( "idusuario" ) );
				result.setName( rs.getString( "nomusuario" ) );
				result.setLastname( rs.getString( "apeusuario" ) );
				result.setRole( rs.getInt( "rol" ) );
				result.setEmail( rs.getString( "emailusuario" ) );
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
