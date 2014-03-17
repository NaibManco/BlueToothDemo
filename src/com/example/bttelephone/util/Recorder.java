package com.example.bttelephone.util;

import java.io.IOException;
import java.io.OutputStream;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * 音频采集器
 * @author totoro
 *
 */
public class Recorder {
	private static final String TAG = "Totoro:Recorder";
	// 音频获取源为电话通话内容
	private int audioSource = MediaRecorder.AudioSource.VOICE_CALL;
	// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
	public static int sampleRateInHz = 8000;// 44100;
	// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
	@SuppressWarnings("deprecation")
	public static int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_STEREO;// AudioFormat.CHANNEL_IN_STEREO;
	// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
	public static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	// 音频大小
	private int bufSize;
	public boolean isRecording = false;
	public boolean isOpen = false;
	private static AudioRecord mRecord;
	
	public Recorder () {
		bufSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig,
				audioFormat);
		mRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig,
				audioFormat, bufSize);
	}
	
	/**
	 * 开始录音
	 * 
	 * @param out 音频输出流
	 * @return boolean 
	 */
	public boolean startRecord(OutputStream out) {
		byte[] audioData = new byte[bufSize];
		while(isRecording) {
			int result = mRecord.read(audioData,0, bufSize);
			if (result > 0) {
				try {
					out.write(audioData, 0, result);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}		
		}
		return isRecording;
	}
	
	/**
	 * 开始录音
	 * 
	 * @return boolean 
	 */
	public boolean startRecord( ) {
		byte[] audioData = new byte[bufSize];
		while(isRecording) {
			int result = mRecord.read(audioData,0, bufSize);
			if (result > 0) {
				Log.v(TAG,new String(audioData));
//				try {
//					out.write(audioData, 0, result);
//				} catch (IOException e) {
//					e.printStackTrace();
//					return false;
//				}
			}		
		}
		return isRecording;
	}
	
	/**
	 * 打开录音器
	 */
	public void openRecorder() {
		if (mRecord != null) {
			if (mRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
				Log.v(TAG, "播放器未初始化成功！");
			} else {
				mRecord.startRecording();
				isOpen = true;
				isRecording = true;
			}
		} else {
			Log.v(TAG, "播放器未初始化！");
		}
	}
	
	/**
	 * 停止录音
	 */
	public void stopRecord() {
		if (mRecord != null) {
			mRecord.stop();
			isOpen = false;
		} else {
			Log.v(TAG,"试图关闭一个空的录音器！");
		}
	}
	
	/**
	 * 关闭录音
	 */
	public void closeRecorder() {
		if (mRecord != null) {
			mRecord.release();
			mRecord = null;
			isOpen = false;
		}
	}
}
