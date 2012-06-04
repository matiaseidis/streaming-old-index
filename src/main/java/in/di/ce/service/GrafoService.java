package in.di.ce.service;

import in.di.ce.UserCachos;
import in.di.ce.error.VideoNoExisteException;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.service.rta.Respuesta;

import java.util.List;

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

@Controller
@RequestMapping("grafo")
public class GrafoService {

	
	@Autowired @Setter @Getter private BaseModel baseModel;
		
			public Logger logger = Logger.getLogger(getClass());

			@RequestMapping(value="{peli}", method = RequestMethod.GET)
			public @ResponseBody Respuesta<List<UserCachos>> get(@PathVariable String peli) throws VideoNoExisteException{
				
				Assert.notNull( peli );

				return new Respuesta(baseModel.getModel().grafo( peli ));
			}
			
}
