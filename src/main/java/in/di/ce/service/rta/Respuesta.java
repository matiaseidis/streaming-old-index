package in.di.ce.service.rta;

import java.io.Serializable;

public class Respuesta<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final T body;
	
	public Respuesta(T body){
		this.body = body;
	}
	
	public T getBody() {
		return body;
	}
}
