package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.User;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.prevayler.TransactionWithQuery;

public class RegisterUser implements TransactionWithQuery {
	
	public static final Log log = LogFactory.getLog(RegisterUser.class);
 
	public RegisterUser(String nombre, String email, String ip, Integer port) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.ip = ip;
		this.port = port;
	}

	final String nombre; 
	final String email; 
	final String ip; 
	final Integer port;
	
	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking tracking = (Tracking) prevalentSystem;
		User user = new User(nombre,email,ip,port);
		if(tracking.newUser(user)) {
			log.info("Creado usuario "+nombre+" - "+email);
			return user;
		} else {
			log.error("No se pudo crear usuario "+nombre+" - "+email +"- el usuario ya existe" );
		}
		return null;
	}

}
