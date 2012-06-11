package in.di.ce.service;

import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.AddCacho;
import in.di.ce.prevalence.transaction.RemoveCacho;
import in.di.ce.service.rta.Respuesta;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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

		@RequestMapping(value="add/{usuario}/{ip}/{port}/{peli}/{fileName}/{from}/{lenght}", method = RequestMethod.GET)
		public @ResponseBody Respuesta add(@PathVariable String usuario, @PathVariable String ip, @PathVariable int port, 
				@PathVariable String peli, @PathVariable String fileName, @PathVariable Long from, @PathVariable Long lenght) throws Exception{
			
			Assert.state( StringUtils.isNotEmpty(usuario ));
			Assert.state( StringUtils.isNotEmpty(ip ));
			Assert.state(port != 0);
			Assert.state( StringUtils.isNotEmpty(fileName ));
			Assert.state( StringUtils.isNotEmpty(peli ));
			Assert.state( lenght != 0);
			
			return new Respuesta(baseModel.getPrevayler().execute(new AddCacho(usuario, ip, port, peli, fileName,  from, lenght)));
		}
		
		@RequestMapping(value="remove/{usuario}/{peli}/{from}/{lenght}", method = RequestMethod.GET)
		public @ResponseBody Respuesta remove(ModelAndView m, @PathVariable String usuario, 
				@PathVariable String peli, @PathVariable Long from, @PathVariable Long lenght) throws Exception{
			
			Assert.notNull( usuario );
			Assert.notNull( peli );
			Assert.notNull( from );
			Assert.notNull( lenght );
			
			return new Respuesta(baseModel.getPrevayler().execute(new RemoveCacho(usuario, peli, from, lenght)));
		}
		
		@RequestMapping(value="list", method = RequestMethod.GET)
		public @ResponseBody Respuesta list() throws Exception{
			return new Respuesta(baseModel.getModel().getCachosRepo());
		}
		
		
}
