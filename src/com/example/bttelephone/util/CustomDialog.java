package com.example.bttelephone.util;

import com.example.bttelephone.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 2014-01-13
 * 
 * ×Ô¶¨Òådialog
 * @author totoro
 *
 */
public class CustomDialog extends Dialog{
	Context mContext;
	ImageView iconView;
	TextView title;
	EditText input;
	Button yesBtn;
	Button noBtn;
	View view;
	onCustomDialogYesListener yesListener;
	onCustomDialogNoListener noListener;
	public interface onCustomDialogYesListener {
		public void yesClick() ;
	}
	public interface onCustomDialogNoListener {
		public void noClick() ;
	}
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		view = LayoutInflater.from(context).inflate(R.layout.send_msg_dialog, null);
		iconView = (ImageView) view.findViewById(R.id.custom_icon);
		title = (TextView) view.findViewById(R.id.custom_title);
		yesBtn =(Button) view.findViewById(R.id.custom_yes);
		yesBtn.setOnClickListener(clickYesListener);
		noBtn = (Button) view.findViewById(R.id.custom_no);
		noBtn.setOnClickListener(clickNoListener);
		input = (EditText) view.findViewById(R.id.msg_content);
		setContentView(view);
	}

	public void setIcon(int id) {
		this.iconView.setBackgroundResource(id);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		this.title.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		this.title.setText(titleId);
	}
	
	public String getInput() {
		return this.input.getText().toString();
	}
	
	
	public void setOnClickYesListener(onCustomDialogYesListener yesListener) {
		this.yesListener = yesListener;
	}
	
	public void setOnClickNoListener(onCustomDialogNoListener noListener) {
		this.noListener = noListener;
	}

	private View.OnClickListener clickYesListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			yesListener.yesClick();
			CustomDialog.this.dismiss();
		}
	};
	
	private View.OnClickListener clickNoListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			noListener.noClick();
			CustomDialog.this.dismiss();
		}
	};

}
