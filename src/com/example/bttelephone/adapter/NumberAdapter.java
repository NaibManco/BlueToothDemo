package com.example.bttelephone.adapter;

import java.util.List;

import com.example.bttelephone.R;
import com.example.bttelephone.util.BTApplication;
import com.example.bttelephone.util.CustomDialog;
import com.example.bttelephone.util.CustomDialog.onCustomDialogNoListener;
import com.example.bttelephone.util.CustomDialog.onCustomDialogYesListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * µÁª∞∫≈¬Î  ≈‰∆˜
 * 
 * 2014-01-07
 * @author totoro
 *
 */
public class NumberAdapter extends BaseAdapter {
	BTApplication app;
	List<String> mList;
	LayoutInflater inflater;
	Context mContext;
	class ViewHolder {
		TextView number;
		TextView msgBtn;
		TextView dialBtn;
	}
	
	public NumberAdapter(Context context,List<String> list) {
		mContext = context;
		mList = list;
		inflater = LayoutInflater.from(context);
		app = (BTApplication)((Activity)context).getApplication();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.number_item, null);
			holder.number = (TextView) convertView.findViewById(R.id.number_tx);
			holder.dialBtn = (TextView) convertView.findViewById(R.id.dial_btn);
			holder.msgBtn = (TextView) convertView.findViewById(R.id.msg_btn);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.number.setText(mList.get(position));
		holder.dialBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.sendData("#tel:" + mList.get(position) + "%");		
			}
		});
		holder.msgBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog);
				dialog.setTitle(R.string.msg_dialog_title);
				dialog.setIcon(R.drawable.msg_icon);
				dialog.setOnClickYesListener(new onCustomDialogYesListener() {
					
					@Override
					public void yesClick() {
						app.sendData("#smsto:" + mList.get(position)+ "-" + dialog.getInput() + "%");			
					}
				});
				dialog.setOnClickNoListener(new onCustomDialogNoListener() {
					
					@Override
					public void noClick() {
						// TODO Auto-generated method stub			
					}
				});
				dialog.show();				
			}
		});
		return convertView;
	}

}
