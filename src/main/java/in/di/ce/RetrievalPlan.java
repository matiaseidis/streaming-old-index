package in.di.ce;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

public class RetrievalPlan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter private final Video video;
	@Getter private final List<UserCacho> userCachos;
	
	public RetrievalPlan(Video video, List<UserCacho> userCachos) {
		super();
		this.video = video;
		this.userCachos = userCachos;
	}
	
	
	
	
}
