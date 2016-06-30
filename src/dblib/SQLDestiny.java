package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.DestinyGrid;

import data.Register;
import data.Destiny;

public class SQLDestiny {
	public static ArrayList<DestinyGrid> searchDestiny( int stateId, boolean translate ) {
		ArrayList<DestinyGrid> resultList = new ArrayList<DestinyGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select * from entrada where idestado=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, stateId );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/destiny/" + rs.getString( "nombreentrada" ).toLowerCase() + ".jpg");

				DestinyGrid result = new DestinyGrid( translate, new Register() );

				result.setTxt_price( rs.getDouble( "precioentrada" ) );
				result.setIdplan( rs.getInt( "codentrada" ) );
				result.setTxt_plan( rs.getString( "nombreentrada" ) );
				if( translate )
					result.setTxt_descr( rs.getString( "tipoentradaeng" ) );
				else
					result.setTxt_descr( rs.getString( "tipoentrada" ) );
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
			System.err.println( "SQL::Some error ocurred while searching for entradas" );
			System.err.println( e.getMessage() );
			return null;
		}
		return resultList;
	}

	public static ArrayList<Destiny> adminSearchDestiny( String state, boolean translate, int opt ){
		ArrayList<Destiny> resultList = new ArrayList<Destiny>();
		PreparedStatement search = null;
		String searchString = null;
		int stateid = SQLInteractor.getStateID( state.toLowerCase() );
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			switch( opt ) {
				case 0:
					searchString = "select * from entrada where idestado=?";
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 1:
					searchString = "select * from entrada where precioentrada<=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 5;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
				case 2:
					if( state.matches("[a-zA-Z0-9]+"))
						searchString = "select * from entrada where lower(nombreentrada) like '%"+state.toLowerCase()+"%'";
					else
						searchString = "select * from entrada";
					search = c.prepareStatement( searchString );
					break;
				case 3:
					searchString = "select * from entrada where codentrada=?";
					try{
						stateid = Integer.parseInt( state );
					}catch( Exception e ){
						stateid = 1;
					}
					search = c.prepareStatement( searchString );
					search.setInt( 1, stateid );
					break;
			}
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				Destiny result = new Destiny();
				result.setIdestado( stateid );
				result.setNomentrada( rs.getString( "nombreentrada" ) );
				result.setCodentrada( rs.getInt( "codentrada" ) );
				result.setTipoentrada( rs.getString( "tipoentrada" ) );
				result.setTipoentradaeng( rs.getString( "tipoentradaeng" ) );
				result.setPrecioentrada( rs.getInt( "precioentrada" ) );
				stateid = rs.getInt( "idestado" );
				result.setNomestado( SQLInteractor.getStateName( stateid ) );
				resultList.add( result );
			}
			c.close();
		}catch( Exception e ) {
			System.err.println( "SQL::Some error ocurred while searching for entradas" );
			System.err.println( e.getMessage() );
			return null;
		}
		return resultList;
	}

	public static void modifyDestiny( Destiny new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "update entrada set nombreentrada=?, tipoentrada=?, tipoentradaeng=?, precioentrada=?, idestado=? where codentrada=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomentrada() );
			search.setString( 2, new_values.getTipoentrada() );
			search.setString( 3, new_values.getTipoentradaeng() );
			search.setDouble( 4, new_values.getPrecioentrada() );
			search.setInt( 5, new_values.getIdestado() );
			search.setInt( 6, new_values.getCodentrada() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void addDestiny( Destiny new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "insert into entrada (nombreentrada, tipoentrada, tipoentradaeng, idestado,"+
					" precioentrada) values ( ?, ?, ?, ?, ? )";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomentrada() );
			search.setString( 2, new_values.getTipoentrada() );
			search.setString( 3, new_values.getTipoentradaeng() );
			search.setInt( 4, new_values.getIdestado() );
			search.setDouble( 5, new_values.getPrecioentrada() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such entrada" );
			System.err.println( e.getMessage() );
		}
	}

	public static void deleteDestiny( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from entrada where codentrada=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such entrada" );
		}
	}

}
