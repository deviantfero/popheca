package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.FoodGrid;

import data.Register;
import data.Food;

public class SQLFood {

	public static ArrayList<FoodGrid> searchFood( int hotelId, boolean translate ) {
		ArrayList<FoodGrid> resultList = new ArrayList<FoodGrid>();
		PreparedStatement search = null;
		String searchString = null;
		searchString = "select * from plancomida where idhotel=?";
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			search = c.prepareStatement( searchString );
			search.setInt( 1, hotelId );
			ResultSet rs = search.executeQuery();
			while( rs.next() ) {
				String image_path = new String( "/gui/styles/img/food"
												+ "/" + rs.getString( "nomplan" ).toLowerCase() + ".jpg");

				FoodGrid result = new FoodGrid( translate, new Register() );

				result.setTxt_price( rs.getDouble( "precioplan" ) );
				result.setIdplan( rs.getInt( "codplan" ) );
				result.setTxt_plan( rs.getString( "nomplan" ) );
				if( translate )
					result.setTxt_descr( rs.getString( "descrplaneng" ) );
				else
					result.setTxt_descr( rs.getString( "descrplan" ) );
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

	public static ArrayList<Food> adminSearchFood( String state, int opt ){
		ArrayList<Food> resultList = new ArrayList<Food>();
		PreparedStatement search = null;
		String searchString = null;
		String basicTable = "select P.codplan, P.nomplan, P.descrplan, P.descrplaneng, P.idhotel, P.precioplan, H.nomhotel from plancomida as P, hotel as H where P.idhotel=H.idhotel ";
		int stateid = SQLInteractor.getStateID( state.toLowerCase() );
		double price = 0;
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
					searchString = basicTable +"and precioplan<=?";
					try{
						price = Double.parseDouble( state );
					}catch( Exception e ){
						stateid = 4;
					}
					search = c.prepareStatement( searchString );
					search.setDouble( 1, price );
					break;
				case 2:
					if( state.matches("[a-zA-Z0-9]+"))
						searchString = basicTable +"and lower(nomplan) like '%"+state.toLowerCase()+"%'";
					else
						searchString = basicTable;
					search = c.prepareStatement( searchString );
					break;
				case 3:
					searchString = basicTable + "and codplan=?";
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
				Food result = new Food();
				result.setNomplan( rs.getString( "nomplan" ) );
				result.setIdhotel( rs.getInt( "idhotel" ) );
				result.setDescrplan( rs.getString( "descrplan" ) );
				result.setDescrplaneng( rs.getString( "descrplaneng" ) );
				result.setPrecioplan( rs.getDouble( "precioplan" ) );
				result.setCodplan( rs.getInt( "codplan" ) );
				result.setNomhotel( rs.getString( "nomhotel" ) );
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

	public static void addFood( Food new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "insert into plancomida (nomplan, descrplan, descrplaneng, precioplan,"+
					" idhotel ) values ( ?, ?, ?, ?, ? )";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomplan() );
			search.setString( 2, new_values.getDescrplan() );
			search.setString( 3, new_values.getDescrplaneng() );
			search.setDouble( 4, new_values.getPrecioplan() );
			search.setInt( 5, new_values.getIdhotel() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void modifyFood( Food new_values ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "update plancomida set nomplan=?, descrplan=?, descrplaneng=?, precioplan=?, idhotel=? where codplan=?";
			search = c.prepareStatement( searchString );
			search.setString( 1, new_values.getNomplan() );
			search.setString( 2, new_values.getDescrplan() );
			search.setString( 3, new_values.getDescrplaneng() );
			search.setDouble( 4, new_values.getPrecioplan() );
			search.setInt( 5, new_values.getIdhotel() );
			search.setInt( 6, new_values.getCodplan() );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
			System.err.println( e.getMessage() );
		}
	}

	public static void deleteFood( int id ) {
		String searchString = null;
		PreparedStatement search = null;
		try{
			Connection c = SQLInteractor.connect();
			c.setAutoCommit( false );
			searchString = "delete from plancomida where codplan=?";
			search = c.prepareStatement( searchString );
			search.setInt( 1, id );
			search.executeUpdate();
			c.commit();
		}catch( Exception e ) {
			System.err.println( "SQL::Could not find such hotel" );
		}
	}

}
