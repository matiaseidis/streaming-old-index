package in.di.ce.service;

import in.di.ce.VideoCachos;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.AddCacho;
import in.di.ce.prevalence.transaction.RemoveCacho;
import in.di.ce.service.rta.Respuesta;
import junit.framework.Assert;
import lombok.Getter;
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
public class CachoService {

	
	@Autowired @Setter @Getter private BaseModel baseModel;
	
		public Logger logger = Logger.getLogger(getClass());

		@RequestMapping(value="add/{usuario}/{peli}/{from}/{lenght}", method = RequestMethod.GET)
		public @ResponseBody Respuesta add(ModelAndView m, @PathVariable String usuario, 
				@PathVariable String peli, @PathVariable Long from, @PathVariable Long lenght) throws Exception{
			
			Assert.assertNotNull( usuario );
			Assert.assertNotNull( peli );
			Assert.assertNotNull( from );
			Assert.assertNotNull( lenght );
			
			return new Respuesta(baseModel.getPrevayler().execute(new AddCacho(usuario, peli, from, lenght)));
		}
		
		@RequestMapping(value="remove/{usuario}/{peli}/{from}/{lenght}", method = RequestMethod.GET)
		public @ResponseBody Respuesta remove(ModelAndView m, @PathVariable String usuario, 
				@PathVariable String peli, @PathVariable Long from, @PathVariable Long lenght) throws Exception{
			
			Assert.assertNotNull( usuario );
			Assert.assertNotNull( peli );
			Assert.assertNotNull( from );
			Assert.assertNotNull( lenght );
			
			return new Respuesta(baseModel.getPrevayler().execute(new RemoveCacho(usuario, peli, from, lenght)));
		}
		
		@RequestMapping(value="list/{peli}", method = RequestMethod.GET)
		public @ResponseBody Respuesta listForVideo(ModelAndView m, @PathVariable String peli){
			
			Assert.assertNotNull( peli );
			
			VideoCachos vc = baseModel.getModel().listForVideo( peli );

			return new Respuesta<VideoCachos>(vc);
		}
}
