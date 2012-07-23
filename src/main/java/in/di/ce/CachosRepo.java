package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

@Deprecated
public class CachosRepo implements Serializable {
	
	@Getter private final ConcurrentMap<Video, ConcurrentHashMap<User, List<Cacho>>> cachosRepo = new ConcurrentHashMap<Video, ConcurrentHashMap<User,List<Cacho>>>();

	public boolean addCacho(Video video, User user, Cacho newCacho) {
		
		cachosRepo.putIfAbsent(video, new ConcurrentHashMap<User, List<Cacho>>());
		cachosRepo.get(video).putIfAbsent(user, new ArrayList<Cacho>());
		
		List<Cacho> userCachos = cachosRepo.get(video).get(user);
		
		
		List<Cacho> toAdd = new ArrayList<Cacho>();
		List<Cacho> toRemove = new ArrayList<Cacho>();
		boolean absent = true;
		
		for ( Cacho cacho : userCachos) {
			if (cacho.isChoterThan(newCacho)){
				toAdd.add(newCacho);
				absent = false;
				toRemove.add(cacho);
			}
		}
		
		if(absent){
			toAdd.add(newCacho);
		}

		userCachos.removeAll(toRemove);
		userCachos.addAll(toAdd);
		
		if (toAdd.isEmpty()){
			System.out.println("cacho "+newCacho+" not added for video " + video + " user: "+ user);
		} else {
			System.out.println("cacho "+newCacho+" added for video " + video + " user: "+ user);
		}
		
		return absent;
	}

	public boolean removeCacho(Video video, User user, Cacho c) {
		
		List<Cacho> userCachos = cachosRepo.get(video).get(user);
		
		Cacho cachoToRemove = null;
	
		for ( Cacho cacho : userCachos) {
			
			if(cacho.equals(c)){
				cachoToRemove = cacho;
				break;
			}
		}
		return userCachos.remove(cachoToRemove);
	}

	public UserCacho cachoStartingAt(Video video, long from) {

		UserCacho result = new UserCacho(null, null);
		
		for(Entry<User, List<Cacho>> entry : this.cachosRepo.get(video).entrySet()){
			for(Cacho cacho : entry.getValue()){
				if ( cacho.getFrom() == from && !cacho.isChoterThan(result.getCacho())) {
					User user = entry.getKey();
					result = new UserCacho(user, cacho);
				}
			}
		}
		return result;
	}
	
	

}
