package in.di.ce.service;

import in.di.ce.Tracking;

import java.util.Date;
import java.util.List;

import org.prevayler.TransactionWithQuery;

public class UnregisterChunks implements TransactionWithQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String videoId; 
	private final String userId;
	private final List<Integer> chunkOrdinals;
	
	public UnregisterChunks(String videoId, String userId,
			List<Integer> chunkOrdinals) {
		super();
		this.videoId = videoId;
		this.userId = userId;
		this.chunkOrdinals = chunkOrdinals;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking tracking = (Tracking) prevalentSystem;
		return tracking.unregisterChunks(videoId, userId, chunkOrdinals);
	}


}
