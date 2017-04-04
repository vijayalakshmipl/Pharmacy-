package com.prop.pharmacyapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPage extends FragmentActivity{

	LatLng fromPosition;
	MarkerOptions markerOptions;
	SupportMapFragment supportMapFragment;
	GoogleMap mGoogleMap;
	ArrayList<String> arr_name=new ArrayList<String>();
	ArrayList<String> arr_lat=new ArrayList<String>();
	ArrayList<String> arr_lng=new ArrayList<String>();
	ArrayList<String> arr_mobile=new ArrayList<String>();
	ArrayList<String> arr_email=new ArrayList<String>();
	ArrayList<String> arr_dis=new ArrayList<String>();
	ArrayList<String> arr_state=new ArrayList<String>();
	ArrayList<String> arr_gender=new ArrayList<String>();
	ArrayList<String> arr_dob=new ArrayList<String>();
	Connection conn;
	
	String lati, longi;
	double lativalueup, lativaluedown, logivalueleft, logivalueright;
	String sendername;
	double fromlat,fromlong;
	
	
	
	GPSTracker gpstracker;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mappage);
		
		getWindow().setWindowAnimations(0);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		Get_Current_GeoPoints();
		
		
//		district2 = intent.getStringExtra("district");
//		state2 = intent.getStringExtra("state");

//		try {
//			new QuerySQL().execute();
//		} catch (Exception e) {
//			Log.e("ERRO", e.getMessage());
//		}
		
	}
	
	private void Get_Current_GeoPoints()
	{
		gpstracker = new GPSTracker(MapPage.this);

		if (gpstracker.canGetLocation()) {

			fromlat = gpstracker.getLatitude();
			fromlong = gpstracker.getLongitude();

			System.out.println("the current lat is " + fromlat);
			System.out.println("the current long is "
					+ fromlong);
			
			Display_Route();

		} else {
			//showSettingsAlert();
		}
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("inside activity for result");
		if (requestCode == 1) {

			System.out.println("inside activity for result request code");
			gpstracker = new GPSTracker(MapPage.this);

			if (gpstracker.canGetLocation()) {
				Get_Current_GeoPoints();
			} else {
				System.out.println("the gpstracker is disabled");
			//	showSettingsAlert();
			}

		}
	}
	
	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;

		ResultSet rs;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MapPage.this);
			pDialog.setTitle("Store Details");
			pDialog.setMessage("Wait Getting Store  Details...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Object... args) {

			try {

				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://103.10.235.220:3306/pharmacyapp", "root",
						"password");

			} catch (SQLException se) {
				Log.e("ERRO1", se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO2", e.getMessage());
			} catch (Exception e) {
				Log.e("ERRO3", e.getMessage());
			}

			try {

//				String COMANDOSQL = "select * from bloodregister where bloodtype='"
//						+ bloodgroup2
//						+ "'&& district='"
//						+ district2
//						+ "'&& state='" + state2 + "' ";
				
				String COMANDOSQL = "select * from store";
				
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				
				arr_name.clear();
				arr_lat.clear();
				arr_lng.clear();
				arr_mobile.clear();
				arr_email.clear();
				arr_dis.clear();
				arr_state.clear();
				arr_gender.clear();
				arr_dob.clear();

				while (rs.next()) {
					
					String mobile=rs.getString("mobile");
					System.out.println("The mobile is "+mobile);
					arr_mobile.add(mobile);
					
					String lat=rs.getString("latitude");
					System.out.println("The lat is "+lat);
					arr_lat.add(lat);
					
					String lng=rs.getString("longitude");
					System.out.println("The lng is "+lng);
					arr_lng.add(lng);
					
					String name=rs.getString("storename");
					System.out.println("The name is "+name);
					arr_name.add(name);
					
					String email=rs.getString("emailid");
					System.out.println("The email is "+email);
					arr_email.add(email);
					
					
					
					String gender=rs.getString("address");
					System.out.println("The gender is "+gender);
					arr_gender.add(gender);
				
				}
				return true;
			} catch (Exception e) {
				error = e;
				return false;
				// Toast.makeText(getBaseContext(),"Successfully Registered...",
				// Toast.LENGTH_LONG).show();
			}

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss();
			if (result1) {

				try 
				{
					if(arr_name.size()>=1)
					{
						System.out.println("Add mareker the list is not null");
						Add_Marker();
					}
					else
					{
						Toast.makeText(getBaseContext(), "No records to show for your current location",
								Toast.LENGTH_LONG).show();
					}
				} 
				catch (Exception e1) 
				{
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();
				}

			} else {
				if (error != null) {
					// Toast.makeText(getBaseContext(),error.getMessage().toString()
					// ,Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result1);
		}
	}

	private void Display_Route() {
		
		FragmentManager myFM = MapPage.this.getSupportFragmentManager();

		supportMapFragment = (SupportMapFragment)myFM.findFragmentById(R.id.map);
		mGoogleMap = supportMapFragment.getMap();

		mGoogleMap.clear();
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		mGoogleMap.getUiSettings().setCompassEnabled(true);
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
		mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
		mGoogleMap.setTrafficEnabled(true);
		
//		fromPosition = new LatLng(fromlat, fromlong);
//		mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//				fromPosition, 12f));
		
//		markerOptions = new MarkerOptions();
//		markerOptions.draggable(true);
//		markerOptions.position(fromPosition).icon(
//		BitmapDescriptorFactory
//				.fromResource(R.drawable.maptomarker));

//		Add_Marker();
		
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				
				String title=marker.getTitle().toString();
				
				System.out.println("You clicked " + title);

				for (int i = 0; i < arr_name.size(); i++) 
				{
					String name = arr_name.get(i);
					if (name.equals(title)) 
					{
						ShowInfo(i);
						break;
					}
				}
				
				return false;
			}
		});
		
		try {
			new QuerySQL().execute();
		} catch (Exception e) {
			Log.e("ERRO", e.getMessage());
		}
		
	}
	
	private void Add_Marker()
	{
		for(int i=0;i<arr_lat.size();i++)
		{
			double fromlat=Double.parseDouble(arr_lat.get(i));
			double fromlong=Double.parseDouble(arr_lng.get(i));
			String name=arr_name.get(i);
			
//			drawMarker(new LatLng(fromlat, fromlong));
			
			Draw_Marker(fromlat,fromlong,name);
			
		}
		
		fromPosition = new LatLng(fromlat, fromlong);
		mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
				fromPosition, 12f));
		
	}
	
	private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
 
        // Setting latitude and longitude for the marker
        markerOptions.position(point);
 
        // Adding marker on the Google Map
        mGoogleMap.addMarker(markerOptions);
    }
	
	private void Draw_Marker(double fromlat, double fromlong, String name)
	{
		markerOptions = new MarkerOptions();
		markerOptions.draggable(true);
		fromPosition = new LatLng(fromlat, fromlong);
		markerOptions.position(fromPosition).icon(
				BitmapDescriptorFactory
						.fromResource(R.drawable.markerpin));
		mGoogleMap.addMarker(markerOptions).setTitle(name);
//		mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//				fromPosition, 12f));

		
	}
	
	private void ShowInfo(int i)
	{
		Window mAlertDialogWindow;

		final AlertDialog dialog1 = new AlertDialog.Builder(MapPage.this)
				.create();
		dialog1.show();

		dialog1.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog1.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		mAlertDialogWindow = dialog1.getWindow();
		View contv = LayoutInflater.from(MapPage.this).inflate(
				R.layout.dialog_info, null);
		contv.setFocusable(true);
		contv.setFocusableInTouchMode(true);

		mAlertDialogWindow
				.setBackgroundDrawableResource(R.drawable.material_dialog_window);

		mAlertDialogWindow.setContentView(contv);

		TextView tvname=(TextView)mAlertDialogWindow.findViewById(R.id.tvname);
		TextView tvmobile=(TextView)mAlertDialogWindow.findViewById(R.id.tvmobile);
		TextView tvemail=(TextView)mAlertDialogWindow.findViewById(R.id.tvemail);
		
		TextView tvgender=(TextView)mAlertDialogWindow.findViewById(R.id.tvgender);
		
		tvname.setText("Name : "+arr_name.get(i));
		tvmobile.setText("Mobile : "+arr_mobile.get(i));
		tvemail.setText("Email : "+arr_email.get(i));
		
		tvgender.setText("Address : "+arr_gender.get(i));
		
	}
	
}
