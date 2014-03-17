package com.example.bttelephone.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2014-01-03
 * 
 * 联系人对象
 * @author totoro
 *
 */
public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strName;
	private String strOrganization;
//	private String strEmail;
	private String strGroup;
	private ArrayList<String> alPhoneNumber;
	
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrAddress() {
		return strOrganization;
	}
	public void setStrAddress(String strAddress) {
		this.strOrganization = strAddress;
	}
//	public String getStrEmail() {
//		return strEmail;
//	}
//	public void setStrEmail(String strEmail) {
//		this.strEmail = strEmail;
//	}
	public String getStrGroup() {
		return strGroup;
	}
	public void setStrGroup(String strGroup) {
		this.strGroup = strGroup;
	}
	public ArrayList<String> getAlPhoneNumber() {
		return alPhoneNumber;
	}
	public void setAlPhoneNumber(ArrayList<String> alPhoneNumber) {
		this.alPhoneNumber = alPhoneNumber;
	}
	
	public static List<Person> convertStr2Person(String data) {
		List<Person> list = new ArrayList<Person>();
		
		return list;
	}
}
