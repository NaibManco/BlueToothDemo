package com.example.bttelephone;

import java.util.ArrayList;
import java.util.List;

import com.example.bttelephone.adapter.NumberAdapter;
import com.example.bttelephone.adapter.PersonAdapter;
import com.example.bttelephone.util.BTApplication;
import com.example.bttelephone.util.Person;
import com.example.bttelephone.util.ReadThread;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 电话簿
 * 
 * 2014-01-07
 * @author totoro
 *
 */
public class PhonebookFragment extends Fragment{
	public static final String ACTION_CONTACTS = "com.example.bttelephone.action.INIT_CONTACTS";
	//父activity
	Activity activity;
	//联系人列表
	ListView personList;
	//联系人电话列表
	ListView phoneList;
	//联系人数据
	ArrayList<Person> pList;
	//联系人姓名
	TextView nameText;
	//联系人组织
	TextView organizationText;
	BTApplication app;
	ProgressDialog dialog;
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_CONTACTS)) {
				Bundle extras = intent.getExtras();
				pList = (ArrayList<Person>) extras.getSerializable(ReadThread.CONTACTS_BUNDLE_KEY);
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				app.contactsList = pList;
				personList.setAdapter(new PersonAdapter(activity,pList));	
			}
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		activity = getActivity();
		app = (BTApplication) activity.getApplication();
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.phone_book_fragment, null);
	}

	/**
	 * 初始化
	 * 
	 * 2014-01-07
	 * @author totoro
	 */
	void init() {
		testAddData();
		
		nameText = (TextView) activity.findViewById(R.id.name_tx);
		organizationText = (TextView) activity.findViewById(R.id.organization_tx);
		
		phoneList = (ListView) activity.findViewById(R.id.phone_number_list);
		personList = (ListView) activity.findViewById(R.id.person_list);
		personList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Person temp = pList.get(position);
				
				nameText.setText("姓名：" + temp.getStrName());
				phoneList.setAdapter(new NumberAdapter(activity,pList.get(position).getAlPhoneNumber()));
				organizationText.setText("组织：" + temp.getStrAddress());
			}
		});
		personList.setAdapter(new PersonAdapter(activity, pList));
		
		
//		if (app.contactsList != null) {
//			personList.setAdapter(new PersonAdapter(activity,app.contactsList));
//		} else {
//			if (app.mSocket != null) {
//				IntentFilter filter = new IntentFilter();
//				filter.addAction(ACTION_CONTACTS);
//				activity.registerReceiver(receiver, filter);
//				
//				initContactsData();
//			}
//		}
	}
	
	/**
	 * 2014-01-13
	 * 
	 * 显示加载对话框
	 * @author totoro
	 */
	void initContactsData() {
		app.sendData("?contacts");
		
		dialog  = new ProgressDialog(activity);
		dialog.setMessage("正在加载联系人......");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.show();
	}
	
	void testAddData() {
		pList = new ArrayList<Person>();
		
		Person p = new Person();
		p.setStrName("吴军");
		p.setStrAddress("中南海");
		ArrayList<String> numbers = new ArrayList<String>();
		numbers.add("151 0256 5698");
		numbers.add("151 0256 4517");
		p.setAlPhoneNumber(numbers);
		pList.add(p);
		
		p = new Person();
		p.setStrName("栅格");
		p.setStrAddress("马里亚纳");
		numbers = new ArrayList<String>();
		numbers.add("123 6985 4236");
		p.setAlPhoneNumber(numbers);
		pList.add(p);
		
		p = new Person();
		p.setStrName("刘达临");
		p.setStrAddress("马达加斯加");
		numbers = new ArrayList<String>();
		numbers.add("189 5425 1221");
		numbers.add("189 5425 4528");
		numbers.add("189 5425 6781");
		numbers.add("189 5425 0327");
		p.setAlPhoneNumber(numbers);
		pList.add(p);
		
		p = new Person();
		p.setStrName("totoro");
		p.setStrAddress("蒙德");
		numbers = new ArrayList<String>();
		numbers.add("15991690432");
		p.setAlPhoneNumber(numbers);
		pList.add(p);	
	}
}
