package com.prop.pharmacyapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OnlineShopActivity extends Activity {

	ImageButton onlineshop1;
	Button buybtn,buybtn1,buybtn2;
	ImageView image;
	TextView textView;
	EditText edtsearch,edtqty,edtprice;
	String searchitem,sitem,price,totprice,qty,username;
	int n=0;
	Connection conn;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_shop);
		
		SharedPreferences preferences1=getSharedPreferences("username", Context.MODE_PRIVATE);
		username=preferences1.getString("username",null);

		
		image = (ImageView) findViewById(R.id.imageView11);
		onlineshop1 = (ImageButton) findViewById(R.id.o1);
		textView = (TextView) findViewById(R.id.textView11);
		edtsearch=(EditText)findViewById(R.id.editText1);
		
		edtqty = (EditText) findViewById(R.id.med_qty);
		
		edtprice = (EditText) findViewById(R.id.tot_price);

		edtqty.addTextChangedListener(new TextWatcher() {

          public void afterTextChanged(Editable s) {

            // you can call or do what you want with your EditText here
           // yourEditText. ... 
        	  searchitem=edtsearch.getText().toString();
        	  qty=edtqty.getText().toString();
        	  n=1;
				new QuerySQL().execute(searchitem);

          }

          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          public void onTextChanged(CharSequence s, int start, int before, int count) {}
       });
		
		
buybtn=(Button)findViewById(R.id.buy_btn);

buybtn1=(Button)findViewById(R.id.buy_btn1);

buybtn2=(Button)findViewById(R.id.buy_btn2);
		
		
buybtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				//startActivity(new Intent(OnlineShopActivity.this,ConfirmBuyActivity.class));
				searchitem=edtsearch.getText().toString();
	        	  qty=edtqty.getText().toString();
	        	  totprice=edtprice.getText().toString();
				try {
					
						new QuerySQL1().execute();
					
					
		
					} catch (Exception e) {
		        Log.e("ERRO",e.getMessage());
				}
	
				
				
			}
			
		});

buybtn1.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		startActivity(new Intent(OnlineShopActivity.this,CartView.class));
		//new QuerySQL2().execute();	
		
		
	}
	
});

buybtn2.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		startActivity(new Intent(OnlineShopActivity.this,ConfirmBuyActivity.class));
			
		
		
	}
	
});
		
		onlineshop1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
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
				searchitem=edtsearch.getText().toString();
				
					
				new QuerySQL().execute(searchitem);
				
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
	        
	        pDialog = new ProgressDialog(OnlineShopActivity.this);
	        pDialog.setTitle("Medicine Price");
	        pDialog.setMessage("Getting Price...");
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
				String COMANDOSQL="select * from medicinedetails where medicine='"+sitem+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
			if(rs.next()){
				
				price=rs.getString(2);
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
						textView.setText("Price is : "+price);
						if(n==1)
						{
						int q1 = Integer.parseInt(qty);
						int totprice = Integer.parseInt(price);
						int totprice1 = q1 * totprice;
						edtprice.append(""+totprice1);
						}
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
	
	public class QuerySQL1 extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(OnlineShopActivity.this);
	        pDialog.setTitle("Add Cart");
	        pDialog.setMessage("Adding to cart...");
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
				int success=statement.executeUpdate("insert into cartitems values('"+username+"','"+searchitem+"','"+qty+"','"+totprice+"')");
			
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
                
	    		Toast.makeText(getBaseContext(),"Successfully added to cart." ,Toast.LENGTH_LONG).show();
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent i = new Intent(getApplicationContext(),
								OnlineShopActivity.class);
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
