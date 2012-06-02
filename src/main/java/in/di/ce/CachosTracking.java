package in.di.ce;

import java.util.HashMap;
import java.util.Map;

public class CachosTracking {
	
	private final Map<String, VideoCachos> videoCachosRepo = new HashMap<String, VideoCachos>();
	
	public boolean addCacho(String usuario, String peli, Long from, Long lenght) {
		
		if ( !videoCachosRepo.containsKey( peli )) { 
			videoCachosRepo.put(peli, new VideoCachos());
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
}
