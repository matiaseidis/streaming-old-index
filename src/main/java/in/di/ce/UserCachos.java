package in.di.ce;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class UserCachos {
	
	@Getter final private User user;
	@Getter final private List<Cacho> cachos = new ArrayList();

	public UserCachos(User user, Cacho cacho) {
		this.user = user;
		this.getCachos().add(cacho);
	}
	
}
