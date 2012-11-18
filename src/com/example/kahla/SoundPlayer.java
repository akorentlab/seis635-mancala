package com.example.kahla;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
	
	private  SoundPool mSoundPool;
	private  HashMap<Integer, Integer> mSoundPoolMap;
	private  AudioManager  mAudioManager;
	private  Context mContext;
	
//////////////Setting up sound Manager//////////	
	public void initSounds(Context theContext) {
	    mContext = theContext;
	    mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	    mSoundPoolMap = new HashMap<Integer, Integer>();
	    mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public void addSound(int index, int SoundID)
	{
	    mSoundPoolMap.put(index, mSoundPool.load(mContext, SoundID, 1));
	}
	
	
	public void playSound(int index)
	{
	float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	   // if (streamid == 0) System.exit(0);
	    
	}
	
	public void playLoopedSound(int index)
	{	
	    float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}
	
	
}
