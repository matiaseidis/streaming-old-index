package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.service.PlanService;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.prevayler.TransactionWithQuery;

public class RegisterChunks implements TransactionWithQuery {

	private static final Log log = LogFactory.getLog(RegisterChunks.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String videoId; 
	private final String userId;
	private final List<Integer> chunkOrdinals;
	
	public RegisterChunks(String videoId, String userId,
			List<Integer> chunkOrdinals) {
		super();
		this.videoId = videoId;
		this.userId = userId;
		this.chunkOrdinals = chunkOrdinals;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		log.info("about to register chunks for user: " + userId+" for video "+ videoId+" - "+chunkOrdinals);
		Tracking tracking = (Tracking) prevalentSystem;
		return tracking.registerChunks(videoId, userId, chunkOrdinals);
	}

}
