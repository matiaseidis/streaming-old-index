package in.di.ce;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class SubsRepo {
	
	/*
	 * [peliId:[idioma:subtitulo]]
	 */
	private final ConcurrentMap<String, ConcurrentHashMap<String, String>> repo = new ConcurrentHashMap<String, ConcurrentHashMap<String,String>>(); 

	public boolean add(String peli, String lang, String sub){
		repo.putIfAbsent(peli, new ConcurrentHashMap<String, String>());
		return repo.get(peli).putIfAbsent(lang, sub) == null;
	}
	
	public String get(String peli, String lang){
		ConcurrentMap<String, String> langs = repo.get(peli);
		return langs == null ? StringUtils.EMPTY : langs.get(lang);
	}
	
	public boolean remove(String peli, String lang){
		ConcurrentMap<String, String> langs = repo.get(peli);
		
		boolean result = false;
		
		if ( langs != null ) {
			result = langs.remove(lang) != null;
		}

		if ( MapUtils.isEmpty(langs) ) {
			repo.remove(peli);
		}
				
		return result;
	}
	
}
