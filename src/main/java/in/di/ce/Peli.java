package in.di.ce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class Peli {
	
	private Map<String, Object> subs = new HashMap<String, Object>();

	public boolean addSub(String lang, Object sub){
		
		Assert.notNull(lang);
		Assert.notNull(sub);
		
		return this.getSubEn(lang) != null;
	}
	
	public Object getSubEn(String lang) {
		return subs.get(lang);
	}

}
