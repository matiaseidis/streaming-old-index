package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Tracking implements Serializable {
	
	private static final Log log = LogFactory.getLog(Tracking.class); 

	/*
	 * videos repository
	 */
	private final ConcurrentMap<String, Video> videos = new ConcurrentHashMap<String, Video>();
	
	/*
	 * chunks repository
	 * [videoId:[userId:[chunks]]]
	 */
	private final ConcurrentMap<String, Map<String, List<Integer>>> tracking = new ConcurrentHashMap<String, Map<String,List<Integer>>>();
	
	public boolean registerVideo(Video video){
		return videos.putIfAbsent(video.getId(), video) == null;
	}
	
	public boolean registerChunks(String videoId, String userId, List<Integer> chunks){
		
		if(tracking.get(videoId) == null){
			throw new IllegalStateException("Can't register chunks for video: "+videoId+". there is no such video in database.");
		}
		
		if (tracking.get(videoId).get(userId) == null){
			tracking.get(videoId).put(userId, new ArrayList<Integer>());
			log.info("adding chunk list for video "+ videoId + " for user " + userId);
		}
		
		return tracking.get(videoId).get(userId).addAll(chunks);
	}
	
	public boolean unregisterChunks(String videoId, String userId, List<Integer> chunks){
		
		if (!tracking.containsKey(videoId) || !tracking.get(videoId).containsKey(userId)){ 
			return false;
		}
		return tracking.get(videoId).get(userId).removeAll(chunks);
	}
	
	public List<Integer>getChunksFrom(String videoId, String userId){
		
		if (!tracking.containsKey(videoId) || !tracking.get(videoId).containsKey(userId)){ 
			return null;
		}
		return tracking.get(videoId).get(userId);
	}

	public boolean videoExist(String videoId) {
		return videos.containsKey(videoId);
	}

	public Video getVideo(String videoId) {
		return videos.get(videoId);
	}

	public List<UserChunks> grafo(String videoId) {
		
		Map<String, List<Integer>> usersWithChunks = tracking.get(videoId);
		
		List<UserChunks> result = new ArrayList<UserChunks>();
		if(MapUtils.isEmpty(usersWithChunks)){
			return null;
		}
		
		Map<Integer, String> chunksForUser = new HashMap<Integer, String>();
		for(Map.Entry<String, List<Integer>> entry : usersWithChunks.entrySet()){
			if(CollectionUtils.isNotEmpty(entry.getValue())){
				for(Integer i : entry.getValue()){
					if(chunksForUser.get(i) == null){
						chunksForUser.put(i, entry.getKey());
					}
				}
			}
		}
		Map<String, UserChunks> userChunks = new HashMap<String, UserChunks>();
		for(Map.Entry<Integer, String> entry : chunksForUser.entrySet()){
			if(userChunks.get(entry.getValue())==null){
				userChunks.put(entry.getValue(), new UserChunks(entry.getValue(), new ArrayList<Integer>()));
			}
			userChunks.get(entry.getValue()).getChunks().add(entry.getKey());
		}
		return new ArrayList<UserChunks>(userChunks.values());
	}

	public String getVideoIdFromFilename(String fileName) {
		
		for(Map.Entry<String, Video> entry : videos.entrySet()){
			if(entry.getValue().getFileName().equals(fileName)){
				return entry.getKey();
			}
		}
		return null;
	}
	
	
}
