package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

public class VideoCachos implements Serializable{
	
	/**
	 * 
	 */
	@Getter private final Video video;
	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<String, UserCachos> userCachosMap = new ConcurrentHashMap<String, UserCachos>();
	
	public VideoCachos(Video video){
		this.video = video;
	}
	
	boolean userHasCachos(String user){
		return userCachosMap.get(user) != null;
	}

	public boolean addCacho(String usuario, Cacho newCacho) {
		
		userCachosMap.putIfAbsent(usuario, new UserCachos( usuario ));

		UserCachos userCachos = userCachosMap.get( usuario );

		return userCachos.addCacho( newCacho );
	}
	
	public UserCachos getCachosDe ( String user ){
		return userCachosMap.get( user );
	}

	public void removeUser(String usuario) {
		
	}
	
	public List<UserCachos> getCachos(){
		return new ArrayList(userCachosMap.values());
	}
	
}
