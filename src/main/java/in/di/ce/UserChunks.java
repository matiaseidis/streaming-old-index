package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class UserChunks implements Serializable{
	
	public UserChunks(String userId) {
		super();
		this.userId = userId;
		this.chunks = new ArrayList<Integer>();
	}

	@Getter private final String userId;
	@Getter private final List<Integer> chunks;

	public UserChunks(String userId, List<Integer> chunks) {
		super();
		this.userId = userId;
		this.chunks = chunks;
	}
	
	public boolean addChunk(int chunk){
		return this.getChunks().add(chunk);
	}

	@Override
	public String toString() {
		return "UserChunks [userId=" + userId + ", chunks=" + chunks + "]";
	}
	
	
	

}
