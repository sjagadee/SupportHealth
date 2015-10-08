package app.nichepro.activities.healthcare;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.model.db.DoctroAlarmData;
import app.nichepro.util.db.DbUtils;

public class AlarmDoctorAppReceiverActivity extends Activity {
	private MediaPlayer mMediaPlayer;
	int mAppId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alarmscreen);
		TextView tv = (TextView) findViewById(R.id.titleView);
		Bundle extras = getIntent().getExtras();
		String text = extras.getString("alarm");
		tv.setText(text);
		String appid = extras.getString("appid");
		mAppId = Integer.parseInt(appid);
		deleteExpiredAlarmFromDb();
		Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
		stopAlarm.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mMediaPlayer.stop();
				return false;
			}
		});

		playSound(this, getAlarmUri());
	}

	private void deleteExpiredAlarmFromDb() {
		List<DoctroAlarmData> alarmListData = null;

		alarmListData = DbUtils.getDoctorAppAlarmList(this);
		for (DoctroAlarmData element : alarmListData) {
			if (element.getAppointmentid() == mAppId) {
				DbUtils.removeDoctorAppAlarm(this, element);
			}
		}
	}

	private void playSound(Context context, Uri alert) {
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		} catch (IOException e) {
			System.out.println("OOPS");
		}
	}

	// Get an alarm sound. Try for an alarm. If none set, try notification,
	// Otherwise, ringtone.
	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}
}