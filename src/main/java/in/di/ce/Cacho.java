package in.di.ce;

import lombok.Getter;

public class Cacho {

	@Getter final private long from;
	@Getter final private long lenght;

	public Cacho(Long from, Long lenght) {
		this.from = from;
		this.lenght = lenght;
	}
	
	public long lastByte(){
		return from + lenght;
	}

	public boolean isChoterThan(Cacho newCacho) {
		return this.from > newCacho.getFrom() && this.lastByte() < newCacho.lastByte();
	}
	
}
