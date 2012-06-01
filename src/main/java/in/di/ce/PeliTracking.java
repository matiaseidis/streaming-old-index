package in.di.ce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

public class PeliTracking {

	private Map<String, List<UserCachos>> cachosPorPeli = new HashMap();
	
	public Object grafoFor(String peliId){
		
		Assert.assertNotNull(peliId);
		
		List<UserCachos> cachos = cachosPorPeli.get(peliId);
		
		/*
		 * aca
		 * estrategia para recorrer el grafo de usuarios buscando los cachos
		 * o reventar
		 */
		return null;
				
	}
	
	
	
}
