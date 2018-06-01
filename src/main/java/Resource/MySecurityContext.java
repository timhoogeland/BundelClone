package Resource;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class MySecurityContext implements SecurityContext {
	private String name;
	private String role;
	private boolean isSecure;

	public MySecurityContext(String name, String role, boolean isSecure) {
		this.name = name;
		this.role = role;
		this.isSecure = isSecure;
	}
	
	@Override
	public String getAuthenticationScheme() { return "Bearer"; }

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() { return name; }
		};
	}

	@Override
	public boolean isSecure() { return isSecure; }

	@Override
	public boolean isUserInRole(String role) { return role.equals(this.role); }
}

