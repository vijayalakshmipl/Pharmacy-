package com.prop.pharmacyapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.prop.pharmacyapp.RegisterActivity.QuerySQL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMedicineActivity extends MainActivity {

	EditText edtDisease, edtMedicine, edtStore, edtArea,edtAddress;
Button btnSubmit,btnSubmit1;
Connection conn;

private String disease, medicine, store1, area, address;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmedicine);
		
		edtDisease = (EditText) findViewById(R.id.disease_name);
		edtMedicine = (EditText) findViewById(R.id.medicine_name);
		edtStore = (EditText) findViewById(R.id.store_name);
		
		edtArea = (EditText) findViewById(R.id.area_name);
		edtAddress = (EditText) findViewById(R.id.address);
	
		btnSubmit = (Button) findViewById(R.id.addmedicine_btn);
		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				disease = edtDisease.getText().toString();
				medicine = edtMedicine.getText().toString();
				store1 = edtStore.getText().toString();
				
				area = edtArea.getText().toString();
				address = edtAddress.getText().toString();
				try {
					if(verify())
					{
						new QuerySQL().execute();
					}
					
		
					} catch (Exception e) {
		        Log.e("ERRO",e.getMessage());
				}

				
			}
		});
		
		btnSubmit1 = (Button) findViewById(R.id.medicinebtn_cancel);
		btnSubmit1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),AdminActivity.class);
				startActivity(i);
				
				
			}
		});

	}
	

	public boolean verify()
	{
//		EditText name, userName, password, cpassword, email, phoneNumber;
		Boolean ret=true;
		if(edtDisease.getText().toString().length()<1){edtDisease.setError("Field Required");ret=false;}
		if(edtMedicine.getText().toString().length()<1){edtMedicine.setError("Field Required");ret=false;}
		if(edtStore.getText().toString().length()<1){edtStore.setError("Field Required");ret=false;}
		if(edtArea.getText().toString().length()<1){edtArea.setError("Field Required");ret=false;}
		if(edtAddress.getText().toString().length()<1){edtAddress.setError("Field Required");ret=false;}
		
		return ret;
	}
	
	
	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(AddMedicineActivity.this);
	        pDialog.setTitle("Adding Medicine");
	        pDialog.setMessage("Adding the medicine...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	
	    	
	    	
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
				Statement statement = conn.createStatement();
				int success=statement.executeUpdate("insert into medicinedetails values('"+disease+"','"+medicine+"','"+store1+"','"+area+"','"+address+"')");
			
				if (success >= 1) {
					// successfully created product
					
					return true;
					// closing this screen
//					finish();
				} else {
					// failed to create product
					return false;
				}


				
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
                
	    		Toast.makeText(getBaseContext(),"Successfully added a medicine." ,Toast.LENGTH_LONG).show();
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent i = new Intent(getApplicationContext(),
								AdminActivity.class);
						startActivity(i);		
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getApplicationContext(),error.toString() ,Toast.LENGTH_LONG).show();
	    			Log.d("Error not null...", error.toString());
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Not crreated your credentials!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}


}
