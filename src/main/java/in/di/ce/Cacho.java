package in.di.ce;

import java.io.Serializable;

import lombok.Getter;

public class Cacho implements Serializable{

	@Getter final private long from;
	@Getter final private long lenght;

	public Cacho(Long from, Long lenght) {
		this.from = from;
		this.lenght = lenght;
	}
	
	public long lastByte(){
		return from + lenght - 1;
	}

	public boolean isChoterThan(Cacho newCacho) {
		if (newCacho == null) 
			return false;
		return this.from >= newCacho.getFrom() && this.lastByte() <= newCacho.lastByte();
	}
	
}
