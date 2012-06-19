package in.di.ce;

import java.io.Serializable;

import lombok.Getter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
