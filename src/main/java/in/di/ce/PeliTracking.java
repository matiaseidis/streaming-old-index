package in.di.ce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

public class PeliTracking {

	private Map<String, List<CachosDelUser>> cachosPorPeli = new HashMap();
	
	public List<CachosDelUser> grafoFor(String peliId){
		
		Assert.assertNotNull(peliId);
		
		List<CachosDelUser> cachos = cachosPorPeli.get(peliId);

		long byteActual = 0;
		
		List<CachosDelUser> result = new ArrayList<CachosDelUser>();
		
		
		/*
		 * revisar esto
		 */
		for(CachosDelUser userCachos: cachos){
			for(Cacho cacho : userCachos.getCachos()){
				if ( cacho.getFrom() == byteActual ){
					result.add(new CachosDelUser(userCachos.getUser(), cacho));
					byteActual = cacho.getFrom() + cacho.getLenght() + 1;
				}
			}
	 	}
		
		/*
		 * aca
		 * estrategia para recorrer el grafo de usuarios buscando los cachos
		 * o reventar
		 */
		return result;
				
	}
	
	
	
}
