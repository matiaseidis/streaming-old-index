package in.di.ce.service;

import in.di.ce.User;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.RegisterUser;
import in.di.ce.service.rta.Ok;
import in.di.ce.service.rta.Respuesta;
import in.di.ce.service.rta.TodoMal;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(value="add/{nombre}/{email}/{ip}/{port}", method = RequestMethod.POST)
	public @ResponseBody Respuesta add(ModelAndView m, @PathVariable String nombre, @PathVariable String email, 
			@PathVariable String ip, @PathVariable Integer port) throws Exception{

		logger.info("registrando usuario "+nombre);
		
		Respuesta respuesta = null;

		if(valid(nombre, email, ip, port)){
			User result = (User)baseModel.getPrevayler().execute(new RegisterUser(nombre, email, ip, port));
			respuesta = new Ok(result);
		} else {
			respuesta = new TodoMal(false); 
		}

		return respuesta;
	}

	private boolean valid(String nombre, String email, String ip,
			Integer port) {

		if(StringUtils.isEmpty(nombre)) {
			logger.error("Error intentando crear usuario: "+nombre+". El campo <nombre> no puede ser null o vacio");
			return false;
		}
		if(StringUtils.isEmpty(email)) {
			logger.error("Error intentando crear usuario: "+nombre+". El campo <email> no puede ser null o vacio");
			return false;
		}
		if(StringUtils.isEmpty(ip)) {
			logger.error("Error intentando crear usuario: "+nombre+". El campo <ip> no puede ser null o vacio");
			return false;
		}
		if(port == null || port < 1) {
			logger.error("Error intentando crear usuario: "+nombre+". El campo <port> no puede ser menor a 1 o vacio");
			return false;
		}
		return true;
	}

}
