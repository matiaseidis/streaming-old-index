package in.di.ce;

import java.util.Map;

public class VideoCachos {
	
	private Map<String, UserCachos> userCachos;
	
	boolean userHasCachos(String user){
		return userCachos.get(user) != null;
	}

	public boolean addCacho(String usuario, Cacho newCacho) {
		
		if( userCachos.get(usuario) == null) {
			userCachos.put(usuario, new UserCachos( usuario ));
		}
		
		return userCachos.get(usuario).addCacho( newCacho );
	}
	
	public UserCachos getCachosDe ( String user ){
		return userCachos.get( user );
	}

	public void removeUser(String usuario) {
		
	}
	
}
