package adv.security.dto;

import java.util.ArrayList;
import java.util.List;

import adv.model.enums.RolNombre;

public class JwtDto {
	
	 private String token;
	 private int username;
	 private List<RolNombre> roles = new ArrayList<>();

	    public JwtDto() {
	    }

	    public JwtDto(String token) {
			super();
			this.token = token;
		}

		public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

		public int getUsername() {
			return username;
		}

		public void setUsername(int username) {
			this.username = username;
		}

		public List<RolNombre> getRoles() {
			return roles;
		}

		public void setRoles(List<RolNombre> roles) {
			this.roles = roles;
		} 
		
	    
	    

}
