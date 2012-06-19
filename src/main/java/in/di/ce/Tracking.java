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

	private static final int MAX_FIRST_CACHO_SIZE = 32;
	private static final int MAX_CACHO_SIZE = 64; 

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
		//		Map<String, List<Integer>> chunks = new HashMap<String, List<Integer>>();
		Map<String, UserChunks> userChunks = new HashMap<String, UserChunks>();

		if(usersWithChunks.containsKey(userId) && CollectionUtils.isNotEmpty(usersWithChunks.get(userId))){
			/*
			 * el usuario tiene chunks de este video
			 */
			thisUserChunks.getChunks().addAll(usersWithChunks.get(userId));
			userChunks.put(userId, thisUserChunks);
		}

		Video video = this.getVideo(videoId);

		List<UserChunks> userChunkList = new ArrayList<UserChunks>();

		for(int i = 0; i<video.getChunks().size(); i++) {
			if(thisUserChunks.getChunks().contains(i)) {
				UserChunks uc = segmentFrom(i, thisUserChunks);
				userChunkList.add(uc);
				i+=uc.getChunks().size();
			} else {
				UserChunks poorUserChunks = getShorterUserChunksFrom(i, usersWithChunks);
				userChunkList.add(poorUserChunks);
				i+=poorUserChunks.getChunks().size();
			}
		}

		int total = 0;
		for(UserChunks uc : userChunkList){
			total+=uc.getChunks().size();
		}
		if(total != video.getChunks().size()) {
			log.error("Unable to ellaborate retrieving plan for video "+videoId+" for user "+userId +" - not enough sources available");
			throw new IllegalStateException("Unable to ellaborate retrieving plan for video "+videoId+" for user "+userId+" - not enough sources available");
		}
		return userChunkList;

	}
	private UserChunks getShorterUserChunksFrom(int i, Map<String, List<Integer>> usersWithChunks) {

		int max = i == 0 ? MAX_FIRST_CACHO_SIZE : MAX_CACHO_SIZE;

		//[userId: to]
		Map<String, Integer> temp = new HashMap<String, Integer>();
		int minor = -1;
		for(Map.Entry<String, List<Integer>> entry: usersWithChunks.entrySet()) {
			if(entry.getValue().contains(i)){
				int current = i++;
				while(true) {
					if(!entry.getValue().contains(current) || current == max) {
						break;
					}
					current++;
				}
				temp.put(entry.getKey(), current);
				if(minor == -1) {
					minor = current;
				} 
				if(minor > current) {
					minor = current;
				} 
			}
		}

		for(Map.Entry<String, Integer> entry : temp.entrySet()){
			if(entry.getValue() == minor){
				UserChunks uc = new UserChunks(entry.getKey(), new ArrayList<Integer>());
				for(int ordinal = 0; ordinal < minor; ordinal++) {
					uc.getChunks().add(ordinal);
				}
				return uc;
			}
		}
		return null;
	}

	private UserChunks segmentFrom(int from, UserChunks thisUserChunks) {
		UserChunks result = new UserChunks(thisUserChunks.getUserId(), new ArrayList());
		result.getChunks().add(from);
		int current = from++;
		while(true) {
			if(!thisUserChunks.getChunks().contains(current)) {
				break;
			}
			result.getChunks().add(current);
		}
		return result;
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
