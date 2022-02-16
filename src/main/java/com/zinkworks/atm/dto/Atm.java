package com.zinkworks.atm.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Atm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int notes_5;
	private int notes_10;
	private int notes_20;
	private int notes_50;

	public Atm() {
		
	}

	public Atm(int id, int notes_5, int notes_10, int notes_20, int notes_50) {
		this.id = id;
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
	
	
}
