package in.di.ce;

import in.di.ce.error.VideoNoExisteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

public class Tracking {
	
	@Getter final private UserRepo usersRepo = new UserRepo();
	@Getter private final SubsRepo subsRepo = new SubsRepo();
	
	private final ConcurrentHashMap<String, Video> videosRepo = new ConcurrentHashMap<String, Video>();
	
	private final Map<String, VideoCachos> videoCachosRepo = new HashMap<String, VideoCachos>();
	

	public User loadUserByUserId(String userId) {
		return usersRepo.getById(userId);
	}
	
	public boolean addCacho(String usuario, String peli, Long from, Long lenght) throws VideoNoExisteException {
		
		if ( !videosRepo.containsKey( peli )) { 
			throw new VideoNoExisteException();
		}
		if ( !videoCachosRepo.containsKey( peli )) {
			Video video = videosRepo.get(peli);
			VideoCachos videoCachos = new VideoCachos(video);
			videoCachosRepo.put(peli, videoCachos);
		}
		
		VideoCachos videoCachos = videoCachosRepo.get(peli);
		
		Cacho newCacho = new Cacho(from, lenght);
		
		return videoCachos.addCacho( usuario, newCacho );
	}
	

	public boolean removeCacho(String usuario, String peli, Long from, Long lenght) {
		
		VideoCachos videoCachos = videoCachosRepo.get(peli);
		
		if(videoCachos == null) {
			return false;
		}
		
		UserCachos userCachos = videoCachos.getCachosDe ( usuario );

		if( userCachos == null ) {
			return false;
		}
		
		Cacho toRemove =  null; 
		
		for(Cacho c: userCachos.getCachos()){
			if ( c.getFrom() == from && c.getLenght() == lenght){
				toRemove = c;
			}
		}
		
		if( toRemove == null){
			return false;
		}
		
		boolean removed = userCachos.removeCacho( toRemove );
		
		if(userCachos.getCachos().isEmpty()){
			videoCachos.removeUser( usuario );
		}
		
		return removed;
	}

	public VideoCachos listForVideo(String peli) {
		return videoCachosRepo.get(peli);
	}


	public String fileNameForId(String id) throws VideoNoExisteException {
		Video video = videosRepo.get( id );
		if( video == null ){
			throw new VideoNoExisteException();
		}
		return video.getFileName();
	}


	public boolean addVideo(String id, String fileName, Long size) {
		return videosRepo.putIfAbsent(id, new Video(id, fileName, size)) == null;
	}


	public boolean removeVideo(String id) {
		return videosRepo.remove(id) != null;
	}


	public List<Video> listVideos() {
		return new ArrayList<Video>(videosRepo.values());
	}


	public List<UserCacho> grafo(String peli) {
		
		List<UserCacho> result = new ArrayList<UserCacho>();
		long currentByte = 1;
		boolean done = false;
		UserCacho thisCacho = null;

		while ( !done ){

			thisCacho = cachoStartingAt(peli, currentByte);
			
			if(thisCacho == null || thisCacho.getCacho() == null){

				done = true;
			
			} else {
			
				currentByte = thisCacho.getCacho().lastByte() + 1;
				result.add(thisCacho);
			}
			
		}
		
		return result;
	}

	
	private UserCacho cachoStartingAt(String peli, long from) {
		
		UserCacho result = new UserCacho(null, null);
		
		VideoCachos vc = videoCachosRepo.get(peli);
		
		for ( UserCachos uc : vc.getCachos() ) {
			for ( Cacho c : uc.getCachos() ) {
				if ( c.getFrom() == from && !c.isChoterThan(result.getCacho())) {
					result = new UserCacho(getUsersRepo().getById(uc.getUserId()), c);
				}
			}
		}
		return result;
	}
}
