package in.di.ce.service;

import in.di.ce.CachosTracking;
import in.di.ce.VideoCachos;
import junit.framework.Assert;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("cacho")
public class InfoCachoService {

	
	@Autowired @Setter private CachosTracking cachosTracking;
	
		public Logger logger = Logger.getLogger(getClass());

		@RequestMapping(value="add/{usuario}/{peli}/{from}/{lenght}", method = RequestMethod.GET)
		public ModelAndView add(ModelAndView m, @PathVariable String usuario, 
				@PathVariable String peli, @PathVariable Long from, @PathVariable Long lenght){
			
			Assert.assertNotNull( usuario );
			Assert.assertNotNull( peli );
			Assert.assertNotNull( from );
			Assert.assertNotNull( lenght );
			
			cachosTracking.addCacho(usuario, peli, from, lenght);
			
			return m;
		}
		
		@RequestMapping(value="remove/{usuario}/{peli}/{from}/{lenght}", method = RequestMethod.GET)
		public ModelAndView remove(ModelAndView m, @PathVariable String usuario, 
				@PathVariable String peli, @PathVariable Long from, @PathVariable Long lenght){
			
			Assert.assertNotNull( usuario );
			Assert.assertNotNull( peli );
			Assert.assertNotNull( from );
			Assert.assertNotNull( lenght );
			
			cachosTracking.removeCacho(usuario, peli, from, lenght);
			
			return m;
		}
		
		@RequestMapping(value="list/{peli}", method = RequestMethod.GET)
		public @ResponseBody VideoCachos listForVideo(ModelAndView m, @PathVariable String peli){
			
			Assert.assertNotNull( peli );

			return cachosTracking.listForVideo( peli );
		}
		
		
}
