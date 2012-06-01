package in.di.ce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

public class PeliTracking {

	private Map<String, List<UserCachos>> cachosPorPeli = new HashMap();
	
	public List<UserCachos> grafoFor(String peliId){
		
		Assert.assertNotNull(peliId);
		
		List<UserCachos> cachos = cachosPorPeli.get(peliId);

		long byteActual = 0;
		
		List<UserCachos> result = new ArrayList<UserCachos>();
		
		
		/*
		 * revisar esto
		 */
		for(UserCachos userCachos: cachos){
			for(Cacho cacho : userCachos.getCachos()){
				if ( cacho.getFrom() == byteActual ){
					result.add(new UserCachos(userCachos.getUser(), cacho));
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
