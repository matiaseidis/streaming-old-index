package in.di.ce;

import java.io.Serializable;

import lombok.Getter;

public class UserCacho implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Getter private final User user;
	@Getter private final Cacho cacho;
	
	public UserCacho(User user, Cacho c){
		this.user = user;
		this.cacho = c;
	}

}
