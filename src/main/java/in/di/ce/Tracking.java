package in.di.ce;

import in.di.ce.error.VideoNoExisteException;
import in.di.ce.service.VideoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import lombok.Getter;

public class Tracking implements Serializable {
	
	@Getter final private UserRepo usersRepo = new UserRepo();
	@Getter private final SubsRepo subsRepo = new SubsRepo();
	@Getter private final VideosRepo videosRepo = new VideosRepo();
	@Getter private final CachosRepo cachosRepo = new CachosRepo();
	
	
	
//	private final ConcurrentHashMap<String, Video> videosRepo = new ConcurrentHashMap<String, Video>();
	
//	private final ConcurrentHashMap<String, List<Long>> usersVideosRepo = new ConcurrentHashMap<String, List<Long>>();
	
//	private final Map<String, VideoCachos> videoCachosRepo = new HashMap<String, VideoCachos>();
	

	public User loadUserByUserId(String userId) {
		return usersRepo.getById(userId);
	}
	
	public boolean addCacho(String userId, String ip, int port, String peli, String fileName, Long from, Long lenght) throws VideoNoExisteException {
		
		Video video = videosRepo.getById(peli);
		
		User user = usersRepo.getById(userId);
		Cacho cacho = new Cacho(from, lenght);

		if( video == null){
			video  = new Video(peli, fileName, lenght);
			videosRepo.addVideo(video);
		}
		if (user == null) {
			user = new User(userId, ip, port);
			usersRepo.addUser(user);
		}
		
		return cachosRepo.addCacho(video, user, cacho);
	}
	

	public boolean removeCacho(String userId, String peli, Long from, Long lenght) {
		
		Video video = videosRepo.getById(peli);
		User user = usersRepo.getById(userId);
		Cacho cacho = new Cacho(from, lenght);
		
		if(video == null || user == null){
			System.out.println("user or video not in repositories");
			return false;
		}
		
		return cachosRepo.removeCacho(video, user, cacho);
	}
	
	public String fileNameForId(String id) throws VideoNoExisteException {
		Video video = videosRepo.getById( id );
		if( video == null ){
			throw new VideoNoExisteException();
		}
		return video.getFileName();
	}

	public List<Video> listVideos() {
		return videosRepo.listVideos();
	}

	public List<UserCacho> grafo(String peli) {
		
		Video video = videosRepo.getById(peli);
		
		List<UserCacho> result = new ArrayList<UserCacho>();
		long currentByte = 1;
		boolean done = false;
		UserCacho thisCacho = null;

		while ( !done ){

			thisCacho = cachosRepo.cachoStartingAt(video, currentByte);
			
			if(thisCacho == null || thisCacho.getCacho() == null){

				done = true;
			
			} else {
			
				currentByte = thisCacho.getCacho().lastByte() + 1;
				result.add(thisCacho);
			}
		}
		return result;
	}
	
}
