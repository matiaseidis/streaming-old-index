package in.di.ce;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

public class UserChunks implements Serializable{
	
	@Getter private final String userId;
	@Getter private final List<Integer> chunks;

	public UserChunks(String userId, List<Integer> chunks) {
		super();
		this.userId = userId;
		this.chunks = chunks;
	}

	@Override
	public String toString() {
		return "UserChunks [userId=" + userId + ", chunks=" + chunks + "]";
	}
	
	
	

}
