package in.di.ce;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class CachosDelUser {
	
	@Getter final private User user;
	@Getter final private List<Cacho> cachos = new ArrayList();

	public CachosDelUser(User user, Cacho cacho) {
		this.user = user;
		this.getCachos().add(cacho);
	}
	
	public CachosDelUser(User user, List<Cacho> cachos) {
		this.user = user;
		this.getCachos().addAll(cachos);
	}
	
}
