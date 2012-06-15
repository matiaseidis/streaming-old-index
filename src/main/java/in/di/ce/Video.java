package in.di.ce;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Video implements Serializable{
	
	@Getter private final String id; 
	@Getter private final String fileName; 
	@Getter private final long lenght; 
	@Getter private final List<String> chunks;
	@Getter private final String addedBy;
	
	public Video(String id, String fileName, long lenght, List<String> chunks, String userId) {
		this.fileName = fileName;
		this.id = id;
		this.lenght = lenght;
		this.chunks = chunks;
		this.addedBy = userId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
		
}
