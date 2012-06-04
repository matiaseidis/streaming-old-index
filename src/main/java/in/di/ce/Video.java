package in.di.ce;

import java.io.Serializable;

import lombok.Getter;

public class Video implements Serializable{
	
	@Getter private final String fileName; // original
	@Getter private final String id; //md5
	@Getter private final long lenght; //bytes
	
	public Video(String id, String fileName, Long lenght) {
		this.fileName = fileName;
		this.id = id;
		this.lenght = lenght;
	}
		
}
