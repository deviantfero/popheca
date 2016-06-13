package dblib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
