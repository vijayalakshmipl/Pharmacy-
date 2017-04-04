package com.prop.pharmacyapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.ImageView;
import android.widget.Toast;

public class AddMedicineActivity extends MainActivity {

	EditText edtDisease, edtMedicine, edtprice, edtavail;
Button btnSubmit,btnSubmit1,btnSubmit2;
ImageView image;

Connection conn;

private String medicine,price,avail;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmedicine);
		
		
		edtMedicine = (EditText) findViewById(R.id.medicine_name);
		edtprice = (EditText) findViewById(R.id.med_price);
		
		
		edtavail = (EditText) findViewById(R.id.med_avail);
		image = (ImageView) findViewById(R.id.imageView1);
	
		btnSubmit = (Button) findViewById(R.id.addmedicine_btn);
		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				medicine = edtMedicine.getText().toString();
				price = edtprice.getText().toString();
				
				avail = edtavail.getText().toString();
				
				
				
				
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
		
		btnSubmit2 = (Button) findViewById(R.id.view_btn);
		btnSubmit2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final Random rand = new Random();
				int diceRoll = rand.nextInt(8) + 1;
				
				if(diceRoll==1)
				{
					image.setImageResource(R.drawable.m1);
				}
				if(diceRoll==2)
				{
					image.setImageResource(R.drawable.m2);
				}
				if(diceRoll==3)
				{
					image.setImageResource(R.drawable.m3);
				}
				if(diceRoll==4)
				{
					image.setImageResource(R.drawable.m4);
				}
				if(diceRoll==5)
				{
					image.setImageResource(R.drawable.m5);
				}
				if(diceRoll==6)
				{
					image.setImageResource(R.drawable.m6);
				}
				if(diceRoll==7)
				{
					image.setImageResource(R.drawable.m7);
				}
				if(diceRoll==8)
				{
					image.setImageResource(R.drawable.m8);
				}
				
				
			}
		});

	}
	

	public boolean verify()
	{
//		EditText name, userName, password, cpassword, email, phoneNumber;
		Boolean ret=true;
		if(edtMedicine.getText().toString().length()<1){edtMedicine.setError("Field Required");ret=false;}
		if(edtprice.getText().toString().length()<1){edtprice.setError("Field Required");ret=false;}
		String expression = "^([0-9\\+]|\\(\\d{0,1}\\))[0-9\\-\\. ]{0,15}$";
        CharSequence inputString = edtprice.getText().toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
		
        }
        else
        {
        	edtprice.setError("Invalid Number");ret=false;
        }
		if(edtavail.getText().toString().length()<1){edtavail.setError("Field Required");ret=false;}
		String expression1 = "^([0-9\\+]|\\(\\d{0,1}\\))[0-9\\-\\. ]{0,15}$";
        CharSequence inputString1 = edtavail.getText().toString();
        Pattern pattern1 = Pattern.compile(expression1);
        Matcher matcher1 = pattern.matcher(inputString1);
        if (matcher1.matches())
        {
		
        }
        else
        {
        	edtavail.setError("Invalid Number");ret=false;
        }
		
		
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
				int success=statement.executeUpdate("insert into medicinedetails values('"+medicine+"','"+price+"','"+avail+"')");
			
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
