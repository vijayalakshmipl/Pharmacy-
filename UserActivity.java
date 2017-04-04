package com.prop.pharmacyapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class UserActivity extends Activity {

	ImageButton onlineshop1,drugview2,storebtn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);
		
		
		onlineshop1 = (ImageButton) findViewById(R.id.o);
		drugview2 = (ImageButton) findViewById(R.id.imageButton2);
		storebtn1 = (ImageButton) findViewById(R.id.imageButton);
		
		
		onlineshop1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(UserActivity.this,OnlineShopActivity.class));
					
				
				
			}
			
		});
		
		drugview2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(UserActivity.this,DrugViewActivity.class));
					
				
				
			}
			
		});
		
		storebtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(UserActivity.this,MapPage.class));
					
				
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
