package in.di.ce;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.builder.ToStringBuilder;

public class User implements Serializable{

	@Getter final private String id;
	@Getter @Setter private String email;
	@Getter @Setter private String ip;
	@Getter @Setter private int port;
	
	public User(String id, String email, String ip, int port) {
		this.id = id;
		this.email = email;
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
