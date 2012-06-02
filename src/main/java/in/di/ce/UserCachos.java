package in.di.ce;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class UserCachos {
	
	@Getter private final String userId;
	@Getter private final List<Cacho> cachos = new ArrayList<Cacho>();
	
	public UserCachos(String usuario) {
		this.userId = usuario;
	}
	
	public boolean addCacho(Cacho cacho){
		return cachos.add ( cacho );
	}
	
	public boolean removeCacho(Cacho cacho){
		return cachos.remove ( cacho );
	}
	
}
