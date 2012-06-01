package in.di.ce;

import lombok.Getter;
import lombok.Setter;

public class Peli {
	
	@Getter @Setter private String nombre; // nombre del archivo
	@Getter @Setter private long id; // md5 del file
	@Getter @Setter private long lenght; //en bytes
	

}
