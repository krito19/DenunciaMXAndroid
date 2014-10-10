package com.syca.apps.gob.denunciamx.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageButton;

import com.syca.apps.gob.denunciamx.R;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JARP on 10/1/14.
 */
public class SoundRecordActivity extends FragmentActivity {

  	private static final String LOG_TAG = SoundRecordActivity.class.getName();
  	public static final String EXTRA_OUTPUT = "OUTPUT_FILENAME";
  	private static String mFileName = null;
  	private boolean recorded = false;

  	private MediaRecorder mRecorder = null;
  	private MediaPlayer mPlayer = null;
    boolean mStartRecording = true;

    //@InjectView(R.id.btn_action_record_audio) ImageButton mRecordButton ;

  	private void onRecord(boolean start)
  	{
		  if (start) {
		  startRecording();
		  } else {
		  stopRecording();
		  }
  	}

  	private void onPlay(boolean start) {
		  if (start) {
		  startPlaying();
		  } else {
		  stopPlaying();
		  }
  	}

  	private void startPlaying() {
	  mPlayer = new MediaPlayer();
	  try 
  	  {
		  mPlayer.setDataSource(mFileName);
		  mPlayer.prepare();
		  mPlayer.start();
	  } catch (IOException e) {
	  	Log.e(LOG_TAG, "prepare() failed");
	  }
    }

  private void stopPlaying() 
  {
	  mPlayer.release();
	  mPlayer = null;
  }

  private void startRecording() 
  {
	  mRecorder = new MediaRecorder();
	  mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	  mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	  mRecorder.setOutputFile(mFileName);
	  mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	  try 
	  {
	  	mRecorder.prepare();
	  } catch (IOException e) {
	  	Log.e(LOG_TAG, "prepare() failed");
	  }
	  	mRecorder.start();
  }
  
  private void stopRecording() 
  {
	  mRecorder.stop();
	  mRecorder.release();
	  mRecorder = null;
	  recorded = true;
  }


    @OnClick(R.id.btn_action_record_audio)
    public void onClickRecordButton(ImageButton button)
    {

        onRecord(mStartRecording);
        if (mStartRecording)
        {
            button.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
        }
        else
        {
            Intent data = new Intent();
            data.putExtra("data", mFileName);
            setResult(RESULT_OK, data);
            finish();
        }
        mStartRecording = !mStartRecording;
    }

  @Override
  /**
  * Sets up Activity's View
  * @see android.app.Activity#onCreate(android.os.Bundle)
  */
  public void onCreate(Bundle icicle)
  {
  	super.onCreate(icicle);
    setContentView(R.layout.activity_sound_record);
    ButterKnife.inject(this);

  	Intent caller = getIntent();
  	mFileName = caller.getStringExtra(EXTRA_OUTPUT);
  	Log.i("zzz", mFileName);
  }

  @Override
  /**
  * Handle onPause to release the media Recorder and Player instances.
  * @see android.app.Activity#onPause()
  */
  public void onPause() 
  {
  	super.onPause();
  	if (mRecorder != null) 
  	{
	  	mRecorder.release();
	  	mRecorder = null;
  	}
  	if (mPlayer != null) 
  	{
  		mPlayer.release();
  		mPlayer = null;
  	}
  }

  @Override
  /**
  * @see android.app.Activity#onStop()
  */
  public void onStop() 
  {
  	super.onStop();
  }

  @Override
  public void onBackPressed() 
  {
  	  if(recorded == false)
  	  {
	  	setResult(RESULT_CANCELED, null);
	  }

	  super.onBackPressed();
  }
  
}
