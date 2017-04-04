package com.prop.pharmacyapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String,String>> listvalue;

	public CartAdapter(CartView listOfFriendsActivity,
			ArrayList<HashMap<String,String>> usersList) {
		// TODO Auto-generated constructor stub
		con = listOfFriendsActivity;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(listOfFriendsActivity);
	}

	

	public int getCount() {
		// TODO Auto-generated method stub
		return listvalue.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listvalue.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.cartlistview, null);
			viewHolder = new ViewHolder();
			viewHolder.txtUsername = (TextView) convertView
					.findViewById(R.id.t1);
			viewHolder.txtMobileNumber = (TextView) convertView
					.findViewById(R.id.t2);
			viewHolder.txtother = (TextView) convertView
					.findViewById(R.id.t3);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtUsername.setText(listvalue.get(position).get("medname")
				.toString());
		viewHolder.txtMobileNumber.setText(listvalue.get(position)
				.get("price").toString());
		
		viewHolder.txtother.setText(listvalue.get(position)
				.get("totprice").toString());
		return convertView;

	}

	class ViewHolder {
		TextView txtUsername, txtMobileNumber,txtother;

	}

}
