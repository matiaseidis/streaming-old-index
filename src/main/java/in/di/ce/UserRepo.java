package in.di.ce;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserRepo {
	
	private final ConcurrentMap<String, User> users = new ConcurrentHashMap<String, User>();
	
	public boolean addUser(User user){
		return users.putIfAbsent(user.getId(), user) == null;
	}

	public User getById(String userId) {
		return users.get(userId);
	}
	
}
