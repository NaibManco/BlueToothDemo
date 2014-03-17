package com.example.bttelephone.adapter;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bttelephone.R;

/**
 * 2013-12-31
 * 
 * 设备列表适配器
 * @author totoro
 *
 */
public class DevicesAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<BluetoothDevice> mList;
	
	public DevicesAdapter(Context context,List<BluetoothDevice> list) {
		inflater = LayoutInflater.from(context);
		mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView  = inflater.inflate(R.layout.device_item, null);
		TextView tv = (TextView) convertView.findViewById(R.id.dv_name);
		tv.setText(mList.get(position).getName());
		return convertView;
	}
}