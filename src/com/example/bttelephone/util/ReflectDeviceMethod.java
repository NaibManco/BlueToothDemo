package com.example.bttelephone.util;

import java.lang.reflect.Method;

import android.bluetooth.BluetoothDevice;

/**
 * 2014-01-02
 * 
 * 通过反射取得SDK隐藏的方法
 * 参考网址：http://www.eoeandroid.com/thread-52461-1-1.html
 * 
 * 使用该类方法容易，高效达到目的功能，但是在兼容性上无法有任何保证
 * @author totoro
 *
 */
public class ReflectDeviceMethod {
	
	/**
	* 与设备配对 参考源码：platform/packages/apps/Settings.git
	* \Settings\src\com\android\settings\bluetooth\CachedBluetoothDevice.java
	*/
	public static boolean createBond(Class btClass,BluetoothDevice btDevice) throws Exception {
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	/**
	* 与设备解除配对 参考源码：platform/packages/apps/Settings.git
	* \Settings\src\com\android\settings\bluetooth\CachedBluetoothDevice.java
	*/
	public static boolean removeBond(Class btClass,BluetoothDevice btDevice) throws Exception {
		Method removeBondMethod = btClass.getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
}
