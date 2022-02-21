package com.zinkworks.atm.responses;

public class CashOutputDetails {
	
	private int notes_5;
	private int notes_10;
	private int notes_20;
	private int notes_50;
	
	public CashOutputDetails() {
		
	}
	
	public CashOutputDetails(int notes_5, int notes_10, int notes_20, int notes_50) {
		super();
		this.notes_5 = notes_5;
		this.notes_10 = notes_10;
		this.notes_20 = notes_20;
		this.notes_50 = notes_50;
	}

	public int getNotes_5() {
		return notes_5;
	}

	public void setNotes_5(int notes_5) {
		this.notes_5 = notes_5;
	}

	public int getNotes_10() {
		return notes_10;
	}

	public void setNotes_10(int notes_10) {
		this.notes_10 = notes_10;
	}

	public int getNotes_20() {
		return notes_20;
	}

	public void setNotes_20(int notes_20) {
		this.notes_20 = notes_20;
	}

	public int getNotes_50() {
		return notes_50;
	}

	public void setNotes_50(int notes_50) {
		this.notes_50 = notes_50;
	}

	@Override
	public String toString() {
		return "Cash [notes_5=" + notes_5 + ", notes_10=" + notes_10 + ", notes_20=" + notes_20 + ", notes_50="
				+ notes_50 + "]";
	}
	
	

}
