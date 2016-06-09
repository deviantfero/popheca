package encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private static MessageDigest stomach;

	static {
		try{
			stomach = MessageDigest.getInstance( "MD5" );
		}catch( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		}
	}

	public static String encrypt( String str ) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException( "String cannot be empty!" );
		}

		stomach.update(str.getBytes());
		byte[] hash = stomach.digest();
		StringBuffer hexString = new StringBuffer();
		for( int i = 0; i < hash.length; i++ ) {
			if((0xff & hash[i]) < 0x10 ) 
				hexString.append( "0" + Integer.toHexString((0xFF & hash[i])) );
			else
				hexString.append( Integer.toHexString(0xFF & hash[i]) );
		}
		return hexString.toString();
	}

}
