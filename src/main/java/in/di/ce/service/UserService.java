package in.di.ce.service;

import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.AddUser;
import in.di.ce.service.rta.Respuesta;
import lombok.Getter;
import lombok.Setter;

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
@RequestMapping("user")
public class UserService {
	

		
		@Autowired @Setter @Getter private BaseModel baseModel;
		
			public Logger logger = Logger.getLogger(getClass());

			@RequestMapping(value="add/{nombre}/{ip}/{port}", method = RequestMethod.GET)
			public @ResponseBody Respuesta add(ModelAndView m, @PathVariable String nombre, 
					@PathVariable String ip, @PathVariable Integer port) throws Exception{
				
				Assert.notNull( nombre );
				Assert.notNull( ip );
				Assert.notNull( port );
				
				return new Respuesta(baseModel.getPrevayler().execute(new AddUser(nombre, ip, port)));
			}

}
