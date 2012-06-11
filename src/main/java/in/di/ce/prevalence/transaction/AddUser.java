package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.User;
import in.di.ce.error.UsuarioDuplicadoException;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class AddUser implements TransactionWithQuery {
	
	private final String nombre;
	private final String ip;
	private final Integer port;

	public AddUser(String nombre, String ip, Integer port) {
		this.nombre = nombre;
		this.ip = ip;
		this.port = port;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		if(model.getUsersRepo().getById(nombre) != null){
			throw new UsuarioDuplicadoException();
		}
		return model.getUsersRepo().addUser(new User(nombre, ip, port));
	}

}
