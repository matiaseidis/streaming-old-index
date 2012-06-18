package in.di.ce.service;

import in.di.ce.Tracking;
import in.di.ce.Video;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.RegisterChunks;
import in.di.ce.prevalence.transaction.RegisterVideo;
import in.di.ce.prevalence.transaction.UnregisterChunks;
import in.di.ce.service.rta.Respuesta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/video")
public class VideoService {
	
	private static final Log log = LogFactory.getLog(VideoService.class); 
	
	@Autowired @Setter @Getter private Tracking tracking;
	@Autowired @Setter @Getter private BaseModel baseModel;
	
	@RequestMapping(value="check/{videoId}", method = RequestMethod.GET)
	public @ResponseBody Respuesta<Video> checkVideo(@PathVariable String videoId){
		Video video = null;
		if(tracking.videoExist(videoId)){
			video = tracking.getVideo(videoId);
		}
		return new Respuesta(video);
	}	
	
	@RequestMapping(value="register/{videoId}/{fileName}/{lenght}/{userId}", method = RequestMethod.POST)
	public @ResponseBody Respuesta<Boolean> registerVideo(@PathVariable String videoId, @PathVariable String fileName, @PathVariable Long lenght, @PathVariable String userId, @RequestParam(value="chunks", required=true) String chunks){
		if(tracking.videoExist(videoId)){
			log.info("Unable to add video ["+videoId+"]in videos central repository. Video already exists");
			throw new IllegalArgumentException("video id: " + videoId+" is already registered in central repository");
		}
		
		
		boolean registered = false;
		try {
			registered = (Boolean)this.getBaseModel().getPrevayler().execute(new RegisterVideo(videoId, fileName, lenght, this.chunkIds(chunks), userId));
		} catch (Exception e) {
			log.error("unable to register video "+ videoId, e);
		}
		
		return new Respuesta(registered);
	}
	
	/*
	 * chunk map format expected: 
	 * ordinal-chunkId|ordinal-chunkId
	 */
	@RequestMapping(value="registerChunks/{fileName}/{userId}/{chunks}", method = RequestMethod.GET)
	public Respuesta<Boolean> registerChunks(@PathVariable String fileName, @PathVariable String userId, @PathVariable String chunks){
		
		if(StringUtils.isEmpty(fileName)){
			throw new IllegalArgumentException("File name can not be null nor empty");
		}
		String videoId = tracking.getVideoIdFromFilename(fileName);
				
		List<Integer> chunkOrdinals = chunkOrdinalsForExistentVideo(videoId, chunks);
		log.info("registering chunks "+chunkOrdinals+"for video " +  videoId + " by user " + userId);

		boolean registered = false;
		try {
			registered = (Boolean)this.getBaseModel().getPrevayler().execute(new RegisterChunks(videoId, userId, chunkOrdinals));
		} catch (Exception e) {
			log.error("unable to register chunks "+ userId +" - "+videoId+" - "+chunks, e);
		}
		
		return new Respuesta(registered);
	}
	/*
	 * chunk map format expected: 
	 * ordinal-chunkId|ordinal-chunkId
	 */
	@RequestMapping(value="unregisterChunks/{fileName}/{userId}/{from}/{lenght}", method = RequestMethod.GET)
	public Respuesta<Boolean> unregisterChunks(@PathVariable String fileName, @PathVariable String userId, @PathVariable String chunks){
		
		if(StringUtils.isEmpty(fileName)){
			throw new IllegalArgumentException("File name can not be null nor empty");
		}
		String videoId = tracking.getVideoIdFromFilename(fileName);
		List<Integer> chunkOrdinals = chunkOrdinalsForExistentVideo(videoId, chunks);
		log.info("unregistering chunks "+chunkOrdinals+"for video " +  videoId + " by user " + userId);
		boolean registered = false;
		try {
			registered = (Boolean)this.getBaseModel().getPrevayler().execute(new UnregisterChunks(videoId, userId, chunkOrdinals));
		} catch (Exception e) {
			log.error("unable to unregister chunks "+ userId +" - "+videoId+" - "+chunks, e);
		}
		
		return new Respuesta(registered);
	}
	
	
	@RequestMapping(value="getChunks/{videoId}/{userId}", method = RequestMethod.GET)
	public Respuesta<List<Integer>> getChunksFrom(@PathVariable String videoId, @PathVariable String userId){
		if(!tracking.videoExist(videoId)){
			throw new IllegalArgumentException("video id: " + videoId+" does not exist");
		}
		log.info("about to retrieve chunks from video: " +  videoId + " for user " + userId);
		return new Respuesta(tracking.getChunksFrom(videoId, userId)); 
	}

//	private List<Integer> chunkOrdinalsForNewVideo(String videoId, String chunks) {
//		if(!tracking.videoExist(videoId)){
//			throw new IllegalArgumentException("video id: " + videoId+" does not exist");
//		}
//		Video video = tracking.getVideo(videoId);
//		List<Integer> result = new ArrayList<Integer>();
//		String[] chunksSplitted = chunks.split(",");
//		if(chunksSplitted.length == 0){
//			throw new IllegalArgumentException("no valid chunks passed for video: " + videoId);
//		}
//		
//		for(int i = 0; i<chunksSplitted.length; i++){
//			video.getChunks().add(i,chunksSplitted[i]);
//		}
//		
//		return result;
//	}
	
	private List<Integer> chunkOrdinalsForExistentVideo(String videoId, String chunks) {
	if(!tracking.videoExist(videoId)){
		throw new IllegalArgumentException("video id: " + videoId+" does not exist");
	}
	Video video = tracking.getVideo(videoId);
	List<Integer> result = new ArrayList<Integer>();
	
	for(String chunk : chunks.split("\\&")){
		String[] splittedChunk = chunk.split("!");
		int chunkOrdinal = Integer.parseInt(splittedChunk[0]);
		String chunkId = splittedChunk[1];
		
		if(video.getChunks().get(chunkOrdinal) != null && video.getChunks().get(chunkOrdinal).equals(chunkId)){
			result.add(chunkOrdinal);
		}
	}
	if(CollectionUtils.isEmpty(result)){
		throw new IllegalArgumentException("no valid chunks passed for video: " + videoId);
	}
	return result;
}

	
	private List<String> chunkIds(String chunks) {
		List<String> result = Arrays.asList(chunks.split("!"));
		return result;
	}

}
