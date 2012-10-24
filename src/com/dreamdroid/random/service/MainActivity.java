package com.dreamdroid.random.service;

import java.util.Random;

import com.dreamdroid.random.service.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	SqlAdapter adapter;
	EditText edit_text;
	ListView listView;
	TextView result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpView();
	
		
	 Button info = (Button)findViewById(R.id.info);
	 Button quit = (Button)findViewById(R.id.quit);
	 
	 info.setOnClickListener(new View.OnClickListener() {        	
	 public void onClick(View v) {	                   
		 startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	 }});

	 quit.setOnClickListener(new View.OnClickListener() {        	
	     public void onClick(View v) {
             finish();
     }});
}
	public void addName(View view) {
		String name = edit_text.getText().toString().trim();
		if (!"".equals(name)) {
			Name newName = new Name(name);
			adapter.addItem(newName);
		}
		finishInput();
	}

	private void setUpView() {
		listView = getListView();
		result = (TextView) findViewById(R.id.results);
		edit_text = (EditText) findViewById(R.id.edit_text);
		adapter = new SqlAdapter(this);
		setListAdapter(adapter);
	}

	private void finishInput() {
		edit_text.getText().clear();
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(edit_text.getWindowToken(), 0);
	}
	
	public void removeEntry(View view) {
		String name = result.getText().toString();
		Name nameToRemove = new Name(name);
		adapter.removeItem(nameToRemove);
		result.setText("Your result");
	}
	
	public void getRandomName(View view) {
		 int count = adapter.getCount();
		 if (count < 2) {
			 Toast.makeText(this, "Please enter your variants ", Toast.LENGTH_SHORT).show();
			 return;
		 }
		 int position = (new Random()).nextInt(count);
		 result.setText(adapter.getItem(position).getName());
	 }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Name selectedName = adapter.getItem(position);
		result.setText(selectedName.getName());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		adapter.onDestroy();
	}
}