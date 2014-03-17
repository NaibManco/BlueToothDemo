package com.example.bttelephone.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 2014-01-09
 * 
 * 后台接收数据服务 
 * @author totoro
 *
 */
public class ReceiveDataService extends Service {
	ReadThread mThread;
	BTApplication app;

	@Override
	public void onCreate() {
		super.onCreate();
		app = (BTApplication) getApplication();
		mThread = new ReadThread(app);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mThread.start();
		Log.v("Totoro:onStartCommand","ReadThread is starting!");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mThread.isStop = true;
	}
	private class MyPhoneStateListener extends PhoneStateListener {
//		MediaRecorder recorder;
//		File audioFile;
		String phoneNumber;

		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE: /* 无任何状态时 */
//				if (recorder != null) {
//					recorder.stop();// 停止刻录
//					recorder.reset();// 重设
//					recorder.release();// 刻录完成一定要释放资源
//				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK: /* 接起电话时 */
//
//				try {
//					recorder = new MediaRecorder();
//					recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频采集原
//					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 内容输出格式
//					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 音频编码方式
//
//					// recorder.setOutputFile("/sdcard/myvoice.amr");
//					audioFile = new File(
//							Environment.getExternalStorageDirectory(),
//							phoneNumber + "_" + System.currentTimeMillis()
//									+ ".3gp");
//					recorder.setOutputFile(audioFile.getAbsolutePath());
//					Log.i("TAG", audioFile.getAbsolutePath());
//
//					recorder.prepare(); // 预期准备
//					recorder.start();
//
//				} catch (IllegalStateException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

				break;
			case TelephonyManager.CALL_STATE_RINGING: /* 电话进来时 */
				phoneNumber = incomingNumber;
				autoAnswercall();
				break;
			default:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}
	
	private void autoAnswercall(){
       Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);  
       KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);  
       meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT,keyEvent);  
       app.sendOrderedBroadcast(meidaButtonIntent, null);
   }

}
