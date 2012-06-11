package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;
import in.di.ce.error.UsuarioNoExisteException;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class AddCacho implements TransactionWithQuery {
	
	private final String userId; 
	private final String ip;
	private final int port;
	
	private final String peli; 
	private final String fileName;
	private final Long from;
	private final Long lenght;
	
	public AddCacho(String usuario, String ip, int port, String peli, String fileName, Long from, Long lenght) {
		this.from = from;
		this.lenght = lenght;
		this.peli = peli;
		this.userId = usuario;
		this.ip = ip;
		this.port = port;
		this.fileName = fileName;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		return model.addCacho(userId, ip, port, peli, fileName, from, lenght);
	}

}
