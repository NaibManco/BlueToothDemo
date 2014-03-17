package com.example.bttelephone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bttelephone.R;
import com.example.bttelephone.util.Person;

/**
 * ¡™œµ»À  ≈‰∆˜
 * 
 * @author totoro
 *
 */
public class PersonAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<Person> pList;
	class ViewHolder {
		TextView tvName;
	}
	
	public PersonAdapter(Context context,List<Person> list) {
		inflater = LayoutInflater.from(context);
		pList = list;
	}
	
	@Override
	public int getCount() {
		return pList.size();
	}

	@Override
	public Object getItem(int position) {
		return pList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.contact_item, null);
			
			holder.tvName = (TextView) convertView.findViewById(R.id.person_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tvName.setText(pList.get(position).getStrName());
		return convertView;
	}
}