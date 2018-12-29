package com.workspan.json.model;

public class Link {

	private int from;
	private int to;
	
	
	public Link(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	
}
