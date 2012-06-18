package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.Video;

import java.util.Date;
import java.util.List;

import org.prevayler.TransactionWithQuery;

public class RegisterVideo implements TransactionWithQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String videoId;
	private final String fileName;
	private final long lenght;
	private final List<String> chunks;
	private final String userId;

	public RegisterVideo(String videoId, String fileName, long lenght,
			List<String> chunks, String userId) {
		super();
		this.videoId = videoId;
		this.fileName = fileName;
		this.lenght = lenght;
		this.chunks = chunks;
		this.userId = userId;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
		
		Tracking tracking = (Tracking) prevalentSystem;
		
		/*TODO validar si ya existe*/
		
		Video video = new Video(videoId, fileName, lenght, chunks, userId);
		return tracking.registerVideo(video);
	}


}
