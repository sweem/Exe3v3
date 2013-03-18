package com.example.exe3v3;

import java.io.Serializable;

public class Reminder {
	String errand, place, notes;
	
	public Reminder (String err, String pla, String not) {
		errand = err;
		place = pla;
		notes = not;
	}
	
	public String getErrand() {
		return errand;
	}
	
	public String getPlace() {
		return place;
	}
	
	public String getNotes() {
		return notes;
	}
}
