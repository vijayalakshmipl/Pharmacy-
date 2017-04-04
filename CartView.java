package com.prop.pharmacyapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CartView extends Activity {
	ListView listView;
	Connection conn;
	Double lat,lon;
	protected LocationManager mlocManager;
	String lati,longi,loginname;
	double lativalueup,lativaluedown,logivalueleft,logivalueright;
	String sendername,username;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_list);
		 listView = (ListView) findViewById(R.id.listView1);

		 SharedPreferences preferences1=getSharedPreferences("username", Context.MODE_PRIVATE);
			username=preferences1.getString("username",null);

			
		
		
		try{
			 
					  
		
		
		
		new QuerySQL().execute();
		}
		catch (NumberFormatException e){
			  System.out.println("NumberFormatException: " + e.getMessage());
			  }
	}

	
	
	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		
		
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(CartView.this);
	        pDialog.setTitle("Cart View");
	        pDialog.setMessage("View a Cart...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(Object... args) {
	    	
	    	
			
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
				String COMANDOSQL="select * from cartitems where username='"+username+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
			while(rs.next()){
				usersList1 = new HashMap<String, String>();			
				usersList1.put("medname",rs.getString(2));                                    
				usersList1.put("price",rs.getString(3));
				usersList1.put("totprice",rs.getString(4));
	            Log.d("List Map :",usersList1.toString());
	            
	            usersList2.add(usersList1);
				
			
			}


				return true;
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
						
						listView.setAdapter(new CartAdapter(CartView.this, usersList2));
						listView.setOnItemClickListener(new OnItemClickListener() {

							public void onItemClick(AdapterView<?> parent, View v,
									int position, long id) {
								
						/*		Intent intent = new Intent(
										CartView.this,
										HospitalMessage.class);
								intent.putExtra("hospital", usersList2.get(position)
										.get("hospital"));
								intent.putExtra("location",
										usersList2.get(position).get("location"));
								//intent.putExtra("sendername",loginname);
								startActivity(intent);*/
							}
						});			
						
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
	    	}
	    	super.onPostExecute(result1);
	    }
	}

}
