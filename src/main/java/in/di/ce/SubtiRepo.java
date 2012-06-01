package in.di.ce;

import in.di.ce.error.SubtituloNoDisponibleException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class SubtiRepo {

	Map<String, PeliSubs> pelis = new HashMap<String, PeliSubs>();

	public Object getSubti(String peliHash, String lang) throws SubtituloNoDisponibleException{
		
		Assert.notNull(peliHash);
		Assert.notNull(lang);
		
		PeliSubs peli = pelis.get(peliHash);
		if(peli == null){
			throw new SubtituloNoDisponibleException();
		}
		return peli.getSubEn(lang);
	}
	
	
}
