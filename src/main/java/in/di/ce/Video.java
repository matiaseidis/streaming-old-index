package in.di.ce;

import lombok.Getter;
import lombok.Setter;

public class Video {
	
	@Setter @Getter private String fileName; // original
	@Setter @Getter private String id; //md5
	@Setter @Getter private long lenght; //bytes
	
}
