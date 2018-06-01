package DAOS;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import Objects.Login;
import Resource.AuthenticationResource;

public class LoginDAO extends baseDAO {
	public Map<String, String> login(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Map<String, String> userdata = new HashMap<String, String>();;
		String database_userid = null;
		String database_password = null;
		String database_salt = null;
		String database_status = null;
		String database_usertype = null;

		AuthenticationResource AR = new AuthenticationResource();
		String query = "select userid, password, salt, status, usertype from public.user where username = ?";
		
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				database_userid = Integer.toString(rs.getInt("userid"));
				database_password = rs.getString("password");
				database_salt = rs.getString("salt");
				database_status = rs.getString("status");
				database_usertype = rs.getString("usertype");
			}
			
			pstmt.getConnection().close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if (database_password != null && database_salt != null && database_userid != null) {
			int iterations = 985;
			char[] chars = password.toCharArray();
			byte[] salt = hexStringToByteArray(database_salt);
			
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        byte[] hash = skf.generateSecret(spec).getEncoded();
	        String client_password = toHex(hash);
	        
	        Response test = AR.authenticateUser(username, database_password);
	        Object t = test.getEntity();
	        String testString = t.toString();


	        if(client_password.equals(database_password)) {
	        	userdata.put("userid", database_userid);
	        	userdata.put("session", testString);
	        	userdata.put("usertype", database_usertype);
	        	userdata.put("status", database_status);
	        }
		}

		return userdata;
	}
	
	private static String toHex(byte[] bytes) {
		  String hex = DatatypeConverter.printHexBinary(bytes);
		  return new String(hex);
	}
	
	private static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}