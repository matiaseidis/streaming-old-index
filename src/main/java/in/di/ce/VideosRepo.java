package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class VideosRepo implements Serializable{
	
	private final ConcurrentMap<String, Video> videos = new ConcurrentHashMap<String, Video>();

	public Video getById(String peli) {
		return videos.get(peli);
	}

	public boolean addVideo(Video video) {
		return videos.putIfAbsent(video.getId(), video) == null;
	}

	public List<Video> listVideos() {
		return new ArrayList<Video>(videos.values());
	}
	
	

}
