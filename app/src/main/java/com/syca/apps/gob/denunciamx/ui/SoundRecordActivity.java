package com.syca.apps.gob.denunciamx.ui;

import android.support.v4.app.Fragment;

/**
 * Created by JARP on 10/1/14.
 */
public class SoundRecordActivity extends FragmentActivity {

  	private static final String LOG_TAG = SoundRecordActivity.class.getName();
  	public static final String EXTRA_OUTPUT = "OUTPUT_FILENAME";
  	private static String mFileName = null;
  	private boolean recorded = false;
  	private RecordButton mRecordButton = null;
  	private MediaRecorder mRecorder = null;
  	private PlayButton mPlayButton = null;
  	private MediaPlayer mPlayer = null;

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
  // The Record Button & its logic
  class RecordButton extends Button 
  {
		boolean mStartRecording = true;
		OnClickListener clicker = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				onRecord(mStartRecording);
				if (mStartRecording) 
				{
					setText("Stop recording");
				} 
				else 
				{
					Intent data = new Intent();
					data.putExtra("data", mFileName);
					setResult(RESULT_OK, data);
					setText("Start recording");
				}
				mStartRecording = !mStartRecording;
			}
		};

	  public RecordButton(Context ctx) 
	  {
		  super(ctx);
		  setText("Start recording");
		  setOnClickListener(clicker);
	  }
  }

  // The Play button & its logic
  class PlayButton extends Button 
  {
	  boolean mStartPlaying = true;
	  OnClickListener clicker = new OnClickListener() 
	  {
		  public void onClick(View v) 
		  {
		  	onPlay(mStartPlaying);
		  	if (mStartPlaying) {
		  	setText("Stop playing");
		  	} else {
		  	setText("Start playing");
		  	}
		  mStartPlaying = !mStartPlaying;
		  }
	  };

	  public PlayButton(Context ctx) {
	  super(ctx);
	  setText("Start playing");
	  setOnClickListener(clicker);
	  }
  }
  @Override
  /**
  * Sets up Activity's View
  * @see android.app.Activity#onCreate(android.os.Bundle)
  */
  public void onCreate(Bundle icicle) 
  {
  	super.onCreate(icicle);
  	/*LinearLayout ll = new LinearLayout(this);
  	mRecordButton = new RecordButton(this);
  	ll.addView(mRecordButton, new LinearLayout.LayoutParams(
  	ViewGroup.LayoutParams.WRAP_CONTENT,
  	ViewGroup.LayoutParams.WRAP_CONTENT, 0));
  	mPlayButton = new PlayButton(this);
  	ll.addView(mPlayButton, new LinearLayout.LayoutParams(
  	ViewGroup.LayoutParams.WRAP_CONTENT,
  	ViewGroup.LayoutParams.WRAP_CONTENT, 0));
  	setContentView(ll);*/
  	mRecordButton = new RecordButton(this);
  	mPlayButton = new PlayButton(this);
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
