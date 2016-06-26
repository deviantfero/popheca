package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.FoodGrid;

import data.Reserve;
import data.Register;

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
}
