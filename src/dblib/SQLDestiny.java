package dblib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import gui.DestinyGrid;

import data.Register;

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
				String image_path = new String( "/gui/styles/img/" + SQLInteractor.getStateName( stateId ).toLowerCase() 
												+ "/" + rs.getString( "nombreentrada" ).toLowerCase() + ".jpg");

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
			System.err.println( "SQL::Some error ocurred while searching for hotels" );
			System.err.println( e.getMessage() );
			return null;
		}
		return resultList;
	}
}
