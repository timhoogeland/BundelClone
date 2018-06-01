package Resource;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAOS.UserDAO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class AuthenticationResource {
	final static public Key key = MacProvider.generateKey();
	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(String user,
									String password) {
		try {
			// Authenticate the user against the database
			UserDAO dao = new UserDAO();
			String role = dao.findRoleForNameAndPassword(user, password);
			
			if (role == null) { throw new IllegalArgumentException("Geen gebruiker gevonden met user/wachtwoord combinatie!"); }
			
			
			// Issue a token for the user
			Calendar expiration = Calendar.getInstance();
			expiration.add(Calendar.MINUTE, 30);
			
			String token = Jwts.builder()
			.setSubject(user)
			.claim("role", role)
			.setExpiration(expiration.getTime())
			.signWith(SignatureAlgorithm.HS512, key)
			.compact();
			
			System.out.println(token);
//			Response.ok(token).build();
			// Return the token on the response
			return Response.ok(token).build();
		} catch (JwtException | IllegalArgumentException e) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}
	}
}
