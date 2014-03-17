package com.example.bttelephone.util;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * 流播放器
 * @author totoro
 *
 */
public class Player {
	private static final String TAG = "Totoro:Player";
	// 音频类型
	private int streamType = AudioManager.STREAM_MUSIC;
	// 音频大小
	private int bufSize;
	// 音频模式
	private int mode = AudioTrack.MODE_STREAM;
	public boolean isOpen = false;
	public boolean isPlaying = false;
	private static AudioTrack mTrack;
	
	public Player() {
		bufSize = AudioTrack.getMinBufferSize(Recorder.sampleRateInHz, Recorder.channelConfig,
				Recorder.audioFormat);
		mTrack = new AudioTrack(streamType, Recorder.sampleRateInHz, Recorder.channelConfig,
				Recorder.audioFormat, bufSize, mode);
	}
	
	/**
	 * 打开播放器
	 */
	public void openPlayer() {
		if (mTrack != null) {
			mTrack.play();
			isOpen = true;
		} else {
			Log.v(TAG, "播放器未初始化！");
		}
	}
	
	/**
	 * 开始播放
	 * @param data 播放数据来源
	 */
	public void startPlay(byte[] data) {
		if (mTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
			isPlaying = true;
			mTrack.write(data, 0, data.length);
		}
	}
	
	/**
	 * 停止播放
	 */
	public void stopPlayer() {
		if (mTrack != null) {
			if (mTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
				mTrack.stop();
				isPlaying = false;
			}
		} else {
			Log.v(TAG,"试图关闭一个空的播放器！");
		}
	}
	
	/**
	 * 关闭播放器
	 */
	public void closePlayer() {
		if (mTrack != null) {
			mTrack.release();
			mTrack = null;
			isOpen = false;
		}
	}
}
