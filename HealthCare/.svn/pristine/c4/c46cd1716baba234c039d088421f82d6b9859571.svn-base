package app.nichepro.activities.healthcare;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import app.nichepro.base.BaseApp;
import app.nichepro.util.UIUtilities;

public class BaseDefaultFragmentActivity extends FragmentActivity implements BaseActivity {

	private boolean wasCreated, wasInterrupted;

	private int progressDialogTitleId;

	private int progressDialogMsgId;

	private Intent currentIntent;

	

	public Boolean noHistory() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.wasCreated = true;
		this.currentIntent = getIntent();

		Application application = getApplication();
		if (application instanceof BaseApp) {
			((BaseApp) application).setActiveContext(getClass()
					.getCanonicalName(), this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		wasInterrupted = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		wasCreated = wasInterrupted = false;
		UIUtilities.hideKeyboard(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.currentIntent = intent;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return BaseActivityHelper.createProgressDialog(getDialogContext(),
				progressDialogTitleId, progressDialogMsgId, showProgressTitle,
				id);
	}

	public void setProgressDialogTitleId(int progressDialogTitleId) {
		this.progressDialogTitleId = progressDialogTitleId;
	}

	public void setProgressDialogMsgId(int progressDialogMsgId) {
		this.progressDialogMsgId = progressDialogMsgId;
	}

	public int getWindowFeatures() {
		return BaseActivityHelper.getWindowFeatures(this);
	}

	public boolean isRestoring() {
		return wasInterrupted;
	}

	public boolean isResuming() {

		return !wasCreated;
	}

	public boolean isLaunching() {
		return !wasInterrupted && wasCreated;
	}


	public Intent getCurrentIntent() {
		return currentIntent;
	}

	public boolean isLandscapeMode() {
		return getWindowManager().getDefaultDisplay().getOrientation() == 1;
	}

	public boolean isPortraitMode() {
		return !isLandscapeMode();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	private boolean showProgressTitle = false;

	public void setShowProgressTitle(boolean show) {
		this.showProgressTitle = show;
	}

	

	@Override
	public void onBackPressed() {
			super.onBackPressed();
	}

	public Context getDialogContext() {
		return getDialogContext(this);
	}

	public Context getDialogContext(Context cxt) {
		return cxt;
	}

	public void handleActivityResult(int requestCode, int resultCode,
			Intent data) {
		onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean handleBackPressed() {
		return false;
	}

	@Override
	public void moveTo(Intent intent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getClickableResource() {
		// TODO Auto-generated method stub
		return 0;
	}

}
