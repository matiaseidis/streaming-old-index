package in.di.ce;

import in.di.ce.error.SubtituloNoDisponibleException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class SubtiRepo {

	Map<String, Peli> pelis = new HashMap<String, Peli>();

	public Object getSubti(String peliHash, String lang) throws SubtituloNoDisponibleException{
		
		Assert.notNull(peliHash);
		Assert.notNull(lang);
		
		Peli peli = pelis.get(peliHash);
		if(peli == null){
			throw new SubtituloNoDisponibleException();
		}
		return peli.getSubEn(lang);
	}
	
}
