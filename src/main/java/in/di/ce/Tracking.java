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
	private final ConcurrentMap<String, Map<String, List<Integer>>> usersLocalRepoTracking = new ConcurrentHashMap<String, Map<String,List<Integer>>>();
	
	public boolean registerVideo(Video video){
		return videos.putIfAbsent(video.getId(), video) == null;
	}
	
	public boolean registerChunks(String videoId, String userId, List<Integer> chunks){
		
		if(usersLocalRepoTracking.get(videoId) == null){
			throw new IllegalStateException("Can't register chunks for video: "+videoId+". there is no such video in database.");
		}
		
		if (usersLocalRepoTracking.get(videoId).get(userId) == null){
			usersLocalRepoTracking.get(videoId).put(userId, new ArrayList<Integer>());
			log.info("adding chunk list for video "+ videoId + " for user " + userId);
		}
		
		return usersLocalRepoTracking.get(videoId).get(userId).addAll(chunks);
	}
	
	public boolean unregisterChunks(String videoId, String userId, List<Integer> chunks){
		
		if (!usersLocalRepoTracking.containsKey(videoId) || !usersLocalRepoTracking.get(videoId).containsKey(userId)){ 
			return false;
		}
		return usersLocalRepoTracking.get(videoId).get(userId).removeAll(chunks);
	}
	
	public List<Integer>getChunksFrom(String videoId, String userId){
		
		if (!usersLocalRepoTracking.containsKey(videoId) || !usersLocalRepoTracking.get(videoId).containsKey(userId)){ 
			return null;
		}
		return usersLocalRepoTracking.get(videoId).get(userId);
	}

	public boolean videoExist(String videoId) {
		return videos.containsKey(videoId);
	}

	public Video getVideo(String videoId) {
		return videos.get(videoId);
	}

	public List<UserChunks> grafo(String videoId, String userId) {
		
		Map<String, List<Integer>> usersWithChunks = usersLocalRepoTracking.get(videoId);
		
		if(MapUtils.isEmpty(usersWithChunks)){
			log.info("Unable to ellaborate retrieving plan for video "+videoId+" for user "+userId);
			return null;
		}
		
		UserChunks thisUserChunks = new UserChunks(userId, new ArrayList<Integer>());
		
		/*
		 * userId:[chunkOrdinals]
		 */
		Map<String, List<Integer>> chunks = new HashMap<String, List<Integer>>();
		Map<String, UserChunks> userChunks = new HashMap<String, UserChunks>();
		
		if(usersWithChunks.containsKey(userId) && CollectionUtils.isNotEmpty(usersWithChunks.get(userId))){
			/*
			 * el usuario tiene chunks de este video
			 */
			thisUserChunks.getChunks().addAll(usersWithChunks.get(userId));
			userChunks.put(userId, thisUserChunks);
		}
		
		for(Map.Entry<String, List<Integer>> entry : usersWithChunks.entrySet()){
			if(CollectionUtils.isNotEmpty(entry.getValue()) && !entry.getKey().equals(userId)){
				for(Integer chunkOrdinal : entry.getValue()){
					if(!thisUserChunks.getChunks().contains(chunkOrdinal)){
						
//						TODO el usuario con el conjunto de chunks consecutivos mas corto desde el ordinal actual
						// necesito recorrer de otra manera la lista de chunks (por orden natural de chunk ordinal)
//						String poorUserId = getUserWithShortSegmentFrom(ordinalActual, usersWithChunks);
						
						
						if(chunks.get(entry.getKey()) == null){
							chunks.put(entry.getKey(), new ArrayList<Integer>());
						}
						chunks.get(entry.getKey()).add(chunkOrdinal);
					}
				}
			}
		}
		
		for(Map.Entry<String, List<Integer>> entry : chunks.entrySet()){
			userChunks.put(entry.getKey(), new UserChunks(entry.getKey(), entry.getValue()));
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
