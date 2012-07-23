package in.di.ce.service.rta;

import java.io.Serializable;

public class Respuesta<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final T body;
	private final String code;
	
	public Respuesta(T body, String code){
		this.body = body;
		this.code = code;
	}
	
	public T getBody() {
		return body;
	}
	
	public String getCode() {
		return code;
	}
}
