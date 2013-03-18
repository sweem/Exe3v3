package com.example.exe3v3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Exe3v3Activity extends ListActivity {
	String[] errands;
	ArrayList arr;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        arr = new ArrayList();
        loadReminder();
        
		errands = new String[arr.size()];
		
		for(int i = 0; i < arr.size(); i++) {
			Reminder rem = (Reminder) arr.get(i);
			errands[i] = rem.getErrand();
		}
        
        ListView lstV = getListView();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, errands));
    }
    
    public void onClick(View view) {
    	startActivity(new Intent(this, NextActivity.class));
    	finish();
    }

    @Override
    public void onListItemClick(ListView p, View v, int pos, long id) {
    	Toast.makeText(this, "You have selected " + pos, Toast.LENGTH_SHORT).show();
    	Intent i = new Intent(this, SelectedActivity.class);
    	Reminder tmp = (Reminder) arr.get(pos);
    	
    	//i.putExtra("Reminder", tmp);
    	i.putExtra("pos", pos);
    	i.putExtra("errand", tmp.getErrand());
    	i.putExtra("place", tmp.getPlace());
    	i.putExtra("notes", tmp.getNotes());
        startActivity(i);
        finish();
    }
    
    public void loadReminder() {
    	try {
    		FileInputStream fIn = openFileInput("reminders.txt");
    		InputStreamReader isr = new InputStreamReader(fIn);
    		BufferedReader reader = new BufferedReader(isr);
    		Reminder rem;
    		
    		int pos = 0;
    		int beg = 0;
    		int delim = 0;
    		int end = 0;
    		
    		StringBuilder sb;
    		String line, errand, place, notes;
    		while(((line = reader.readLine()) != null)) {
    			sb = new StringBuilder();
    			sb.append(line);
    			//Toast.makeText(getBaseContext(), "Read line: " + sb.toString() + " with length: " + sb.length() + " at pos: " + pos, Toast.LENGTH_SHORT).show();
    			end = sb.length();
    			
    			delim = sb.indexOf("|", beg);
    			errand = sb.substring(beg, delim);
    			//errands.add(errand);
    			beg = delim + 1;
    			
    			delim = sb.indexOf("|", beg);
    			place = sb.substring(beg, delim);
    			beg = delim + 1;
    			
    			notes = sb.substring(beg, end);
    		
    			//Toast.makeText(getBaseContext(), "Errand: " + errand + " place: " + place + " notes: " + notes, Toast.LENGTH_SHORT).show();
    			rem = new Reminder(errand, place, notes);
    			arr.add(rem);
    			//Toast.makeText(getBaseContext(), "Nbr of elements in the arr: " + arr.size(), Toast.LENGTH_SHORT).show();
    			pos++;
    			beg = 0;
    			delim = 0;
    			end = 0;
    		}
    		reader.close();
    		isr.close();
    		
    		Toast.makeText(getBaseContext(), "File loaded successfully!", Toast.LENGTH_SHORT).show();
    		
    	}catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
}