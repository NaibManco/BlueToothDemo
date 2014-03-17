package com.example.bttelephone.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.example.bttelephone.PhonebookFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * 读取数据线程
 * @author totoro
 *
 */
public class ReadThread extends Thread { 
	public static final String CONTACTS_BUNDLE_KEY = "CONTACTS";
	public boolean isStop = false;
	public BTApplication app;
	
	public ReadThread(BTApplication app) {
		this.app = app;		
	}
	
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;
        InputStream mmInStream = null;
        
		try {
			mmInStream = app.mSocket.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
        while (!isStop) {
            try {
                // 从输入流读取数据
                if( (bytes = mmInStream.read(buffer)) > 0 )
                {
                    byte[] buf_data = new byte[bytes];
			    	for(int i=0; i<bytes; i++)
			    	{
			    		buf_data[i] = buffer[i];
			    	}
			    	String temp = new String(buf_data);
			    	/**
			    	 * 呼叫电话
			    	 */
			    	if (temp.contains("tel")) {
			    		temp = temp.substring(1);
			    		Log.v("Totoro:readthread","temp:" + temp);
			    		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(temp));
			    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    		app.startActivity(intent);
			    	} 
			    	/**
			    	 * 发送的信息为联系人列表
			    	 */
			    	if (temp.contains("contacts")){
			    		Intent broadcastIntent = new Intent(PhonebookFragment.ACTION_CONTACTS);
			    		Bundle extras = new Bundle();
			    		extras.putSerializable(CONTACTS_BUNDLE_KEY, convert2Contacts(temp));
			    		broadcastIntent.putExtras(extras);
			    		app.sendBroadcast(broadcastIntent);
			    	}
                }
            } catch (IOException e) {
            	try {
					mmInStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                break;
            }
        }
    }
    
    /**
     * 将字符串解析成联系人列表
     * 
     * @param str 联系人信息串
     * @return ArrayList<Person>
     */
    public ArrayList<Person> convert2Contacts(String str) {
    	ArrayList<Person> list = new ArrayList<Person>();
    	/**
    	 * 去头去尾，提取联系人信息子串
    	 * 信息格式：#contacts：name-number,number$name-number%
    	 */
    	String temp1 = str.substring(str.indexOf(':') + 1, str.length() - 2);
    	/**
    	 * 提取单个联系人信息串
    	 * name-number
    	 */
    	String[] temp2 = temp1.split("$");
    	for (String temp3 : temp2) {
    		/**
    		 * 分离姓名和电话号码
    		 */
    		String[] temp4 = temp3.split("-");
    		/**
    		 * temp4第0位存放姓名
    		 */
    		Person p = new Person();
    		p.setStrName(temp4[0]);
    		/**
    		 * temp4第1位存放号码串
    		 */
    		ArrayList<String> numberList = new ArrayList<String>();
    		/**
    		 * number,number
    		 * 分离号码
    		 */
    		String[] temp5 = temp4[1].split(",");
    		for (String temp6 : temp5) {
    			numberList.add(temp6);
    		}
    		
    		p.setAlPhoneNumber(numberList);
    		p.setStrGroup("");
    		list.add(p);
    	}
		return list;	
    }
}