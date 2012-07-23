package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.Video;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.prevayler.TransactionWithQuery;

public class RegisterVideo implements TransactionWithQuery {
	
	public static final Log log = LogFactory.getLog(RegisterVideo.class);

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
		if(tracking.getVideo(videoId) != null) {
			log.warn("El video que se intenta registrar <id: "+videoId+"><fileName: "+fileName+"> ya existe en el indice");
			return false;
		}
		
		Video video = new Video(videoId, fileName, lenght, chunks, userId);
		boolean ok = tracking.registerVideo(video, userId, chunks);
		if(ok) {
			log.info("Se registro el video <id: "+videoId+"><fileName: "+fileName+"> en el indice");
		} else {
			log.error("No se pudo registrar el video <id: "+videoId+"><fileName: "+fileName+"> en el indice");
			
		}
		return ok ? video : null;
	}


}
