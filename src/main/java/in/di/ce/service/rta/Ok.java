package in.di.ce.service.rta;

public class Ok<T> extends Respuesta<T> {
	
	public Ok(T body) {
		super(body, "OK");
	}

}
