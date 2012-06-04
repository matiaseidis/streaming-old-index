package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.error.UsuarioNoExisteException;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class AddCacho implements TransactionWithQuery {
	
	private final String usuario; 
	private final String peli; 
	private final Long from;
	private final Long lenght;

	public AddCacho(String usuario, String peli, Long from, Long lenght) {
		this.from = from;
		this.lenght = lenght;
		this.peli = peli;
		this.usuario = usuario;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		if( model.getUsersRepo().getById(usuario) == null ){
			throw new UsuarioNoExisteException();
		}
		return model.addCacho(usuario, peli, from, lenght);
	}

}
