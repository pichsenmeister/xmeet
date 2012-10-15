package webapp.client.presenter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
	
	 public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			
		    byte byteData[] = md.digest();
		
		    StringBuffer hash = new StringBuffer();
		 	for (int i=0;i<byteData.length;i++) {
		 		String hex=Integer.toHexString(0xff & byteData[i]);
			     	if(hex.length()==1) hash.append('0');
			     	hash.append(hex);
		 	}

		 	return hash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
