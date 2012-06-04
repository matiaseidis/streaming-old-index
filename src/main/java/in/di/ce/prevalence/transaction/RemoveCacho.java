package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class RemoveCacho implements TransactionWithQuery {

	private final String usuario;
	private final String peli;
	private final Long from;
	private final Long lenght;
	
	public RemoveCacho(String usuario, String peli, Long from, Long lenght) {
		this.usuario = usuario;
		this.peli = peli;
		this.from = from;
		this.lenght = lenght;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		return model.removeCacho(usuario, peli, from, lenght);
	}

}
