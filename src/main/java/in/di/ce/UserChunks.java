package in.di.ce;

import java.util.List;

import lombok.Getter;

public class UserChunks {
	
	@Getter private final String userId;
	@Getter private final List<Integer> chunks;

	public UserChunks(String userId, List<Integer> chunks) {
		super();
		this.userId = userId;
		this.chunks = chunks;
	}
	

}
