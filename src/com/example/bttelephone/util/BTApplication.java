package com.example.bttelephone.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class BTApplication extends Application {
	public List<Person> contactsList = null;
	public BluetoothSocket mSocket = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public void cancel() {
		if (mSocket != null) {
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 发送数据
	 * @param data
	 */
	public void sendData(String data) 
	{		
		if (mSocket == null) 
		{
			Toast.makeText(this, "没有连接", Toast.LENGTH_SHORT).show();
			return;
		}
		try {				
			OutputStream os = mSocket.getOutputStream(); 
			Log.v("Totoro:app",data);
			os.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * 发送数据
	 * @param data
	 */
	public void sendData(byte[] data) 
	{		
		if (mSocket == null) 
		{
			Toast.makeText(this, "没有连接", Toast.LENGTH_SHORT).show();
			return;
		}
		try {				
			OutputStream os = mSocket.getOutputStream(); 
			Log.v("Totoro:app",data.toString());
			os.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * 接听电话，可用性待测试
	 * 2014-01-23
	 * 
	 * @author totoro
	 */
	public void answer_call() {
		Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
		localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		localIntent1.putExtra("state", 1);
		localIntent1.putExtra("microphone", 1);
		localIntent1.putExtra("name", "Headset");
		this.sendOrderedBroadcast(localIntent1,"android.permission.CALL_PRIVILEGED");

		Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
		KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_HEADSETHOOK);
		localIntent2.putExtra("android.intent.extra.KEY_EVENT",localKeyEvent1);
		this.sendOrderedBroadcast(localIntent2,"android.permission.CALL_PRIVILEGED");

		Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
		KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_HEADSETHOOK);
		localIntent3.putExtra("android.intent.extra.KEY_EVENT",localKeyEvent2);
		this.sendOrderedBroadcast(localIntent3,"android.permission.CALL_PRIVILEGED");

		Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
		localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		localIntent4.putExtra("state", 0);
		localIntent4.putExtra("microphone", 1);
		localIntent4.putExtra("name", "Headset");
		this.sendOrderedBroadcast(localIntent4,"android.permission.CALL_PRIVILEGED");
	}
}
