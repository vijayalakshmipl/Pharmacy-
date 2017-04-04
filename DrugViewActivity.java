package com.prop.pharmacyapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.prop.pharmacyapp.LoginActivity.QuerySQL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrugViewActivity extends Activity {

	Button onlineshop1,cancel1;
	Button buybtn;
	ImageView image;
	TextView textView22,textView33,textView44;
	EditText edtsearch,edtqty,edtprice;
	String searchitem,sitem,price,totprice,qty,s1,s2,s3;
	int n=0;
	Connection conn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drug_view);
		
		
		textView22 = (TextView) findViewById(R.id.textView2);
		textView33 = (TextView) findViewById(R.id.textView3);
		textView44 = (TextView) findViewById(R.id.textView4);
		edtsearch=(EditText)findViewById(R.id.medicine_name);
		
		
		
		onlineshop1=(Button)findViewById(R.id.drugview_btn);

		
		onlineshop1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				searchitem=edtsearch.getText().toString();
				
					
				new QuerySQL().execute(searchitem);
				
			}
			
		});
	
		cancel1 = (Button) findViewById(R.id.drugcancel_btn);
		cancel1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),UserActivity.class);
				startActivity(i);
				
				
			}
		});
		
	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(DrugViewActivity.this);
	        pDialog.setTitle("View Drug");
	        pDialog.setMessage("Getting Drug details...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	sitem = new String(args[0]);
	    	
	    	
	    	
			
			try {
				
				
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://103.10.235.220:3306/pharmacyapp","root","password");			
			} catch (SQLException se) {
				Log.e("ERRO1",se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO2",e.getMessage());
			} catch (Exception e) {
			    Log.e("ERRO3",e.getMessage());
			}
			

			try {
				String COMANDOSQL="select * from drugdetails where medicinename='"+sitem+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
			if(rs.next()){
				
				s1=rs.getString(4);
				s2=rs.getString(2);
				s3=rs.getString(3);
				return true;
			}

			return false;
				
				// Toast.makeText(getBaseContext(),
				// "Successfully Inserted.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				error = e;
				return false;
//				Toast.makeText(getBaseContext(),"Successfully Registered...", Toast.LENGTH_LONG).show();
			}


	    }

	    @SuppressLint("NewApi")
		@Override
	    protected void onPostExecute(Boolean result1) {
	    	pDialog.dismiss ( ) ;
	    	if(result1)
	    	{
                
			
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						textView22.setText("Side effects : "+s1);
						textView33.setText("Chemical Composition : "+s2);
						textView44.setText("Usage : "+s3);
						
						//Intent intent=new Intent(OnlineShopActivity.this,UserActivity.class);
						//intent.putExtra("latitude", lati);
						//intent.putExtra("longitude", longi);
						//intent.putExtra("loginuser", user1);
					
					//	startActivity(intent);			
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Medicine not found!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
