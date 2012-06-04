package in.di.ce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class UserCachos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter private final String userId;
	@Getter private final List<Cacho> cachos = new ArrayList<Cacho>();
	
	public UserCachos(String usuario) {
		this.userId = usuario;
	}
	
	public boolean addCacho(Cacho newCacho){
		
		List<Cacho> toAdd = new ArrayList<Cacho>();
		List<Cacho> toRemove = new ArrayList<Cacho>();
		boolean absent = true;
		
		for ( Cacho cacho : cachos) {
			if (cacho.isChoterThan(newCacho)){
				toAdd.add(newCacho);
				absent = false;
				toRemove.add(cacho);
			}
		}
		
		if(absent){
			toAdd.add(newCacho);
		}

		cachos.removeAll(toRemove);
		cachos.addAll(toAdd);
		
		return !toAdd.isEmpty();
	}
	
	public boolean removeCacho(Cacho cacho){
		return cachos.remove ( cacho );
	}
	
}
