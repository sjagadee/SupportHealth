package app.nichepro.activities.healthcare;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import app.nichepro.HealthCareApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.HtmlPageLinkListResponseObject;
import app.nichepro.model.HtmlPageLinkResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.MenuDialog;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class HealthCareAppStartupActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnClickListener, MenuDialog.MenuSelectListener {

	Button bCancerInfo;
	Button bPatientLogin;
	Button bDoctorLogin;
	Button bSponsorsLogin;
	Button bAboutUs;
	Button bPrivacyPolicy;
	Button bContactUs;
	SoundPool sp;
	int cancerInfo, patientLogin, doctorsLogin, sponsorsLogin;
	WebRequestTask htmlLinkRequestTask;
	private ResponseMesssagType msgType;
	HtmlPageLinkListResponseObject htmlLink;
	boolean isVolumeOn;
	boolean isCancerInfoTapped;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		UIUtilities.isPhoneAvailable(this);
		initialize();
		SharedPreferences prefs = getSharedPreferences("voice", MODE_PRIVATE);
		isVolumeOn = prefs.getBoolean("volumeOnOfstatus", false);
		bCancerInfo.setOnClickListener(this);
		bPatientLogin.setOnClickListener(this);
		bDoctorLogin.setOnClickListener(this);
		bSponsorsLogin.setOnClickListener(this);
		bAboutUs.setOnClickListener(this);
		bPrivacyPolicy.setOnClickListener(this);
		bContactUs.setOnClickListener(this);
//		requestForHtmlLinks();
		registerForContextMenu(bContactUs);

	}

	public void requestForHtmlLinks() {
		htmlLinkRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.LIST_OF_HTML_PAGE), this, this);

		htmlLinkRequestTask.execute();

	}

	private void initialize() {
		// TODO Auto-generated method stub
		isCancerInfoTapped = false;
		bCancerInfo = (Button) findViewById(R.id.binfo);
		bPatientLogin = (Button) findViewById(R.id.bpatient);
		bDoctorLogin = (Button) findViewById(R.id.bdoctor);
		bSponsorsLogin = (Button) findViewById(R.id.bsponsors);
		bAboutUs = (Button) findViewById(R.id.baboutus);
		bPrivacyPolicy = (Button) findViewById(R.id.bprivacypolicy);
		bContactUs = (Button) findViewById(R.id.bcontactus);
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		cancerInfo = sp.load(this, R.raw.cancer_info, 1);
		patientLogin = sp.load(this, R.raw.patient_login, 1);
		doctorsLogin = sp.load(this, R.raw.doctors_login, 1);
		sponsorsLogin = sp.load(this, R.raw.sponsor_login, 1);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.binfo:
			if (!isVolumeOn) {
				sp.play(cancerInfo, 1, 1, 0, 0, 1);
			}
			HealthCareApp myApp = (HealthCareApp) getApplication();
			if (myApp.getHtmlLinkMap().size() > 0) {
				launchAlertDialog();
			} else {
				isCancerInfoTapped = true;
				requestForHtmlLinks();
			}

			break;
		case R.id.bpatient:
			if (!isVolumeOn) {
				sp.play(patientLogin, 1, 1, 0, 0, 1);
			}

			Intent plogin = new Intent(HealthCareAppStartupActivity.this,
					LoginActivity.class);
			plogin.putExtra("loginType", LoginType.Patient.name());
			startActivity(plogin);

			break;
		case R.id.bdoctor:
			if (!isVolumeOn) {
				sp.play(doctorsLogin, 1, 1, 0, 0, 1);
			}

			Intent dlogin = new Intent(HealthCareAppStartupActivity.this,
					LoginActivity.class);
			dlogin.putExtra("loginType", LoginType.Physician.name());
			startActivity(dlogin);
			break;
		case R.id.bsponsors:
			if (!isVolumeOn) {
				sp.play(sponsorsLogin, 1, 1, 0, 0, 1);
			}

			Intent slogin = new Intent(HealthCareAppStartupActivity.this,
					LoginActivity.class);
			slogin.putExtra("loginType", LoginType.Sponsor.name());
			startActivity(slogin);
			break;
		case R.id.baboutus:
		case R.id.bprivacypolicy:
		case R.id.bcontactus:
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					R.string.under_construction, R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			break;
		}
	}

	void launchAlertDialog() {

		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.spinner_custom,
				null);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Select Language");
		// Set an EditText view to get user input
		alert.setView(textEntryView);

		final Spinner input = (Spinner) textEntryView
				.findViewById(R.id.hc_spinner);

		alert.setPositiveButton("Select",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						HealthCareApp myApp = (HealthCareApp) getApplication();
						int index = input.getSelectedItemPosition();
						Intent ita = new Intent(
								HealthCareAppStartupActivity.this,
								AboutCancerActivity.class);
						if (index == 0) {
							ita.putExtra(
									"url",
									WebLinks.getHtmlLink(myApp
											.getHtmlLinkMap(ResponseMesssagType.CancerInformationEnglishMessageType)));
						} else if (index == 1) {
							ita.putExtra(
									"url",
									WebLinks.getHtmlLink(myApp
											.getHtmlLinkMap(ResponseMesssagType.CancerInformationSpanishMessageType)));

						}
						startActivity(ita);
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.ListOfHtmlPageMessageType) {
			this.htmlLink = (HtmlPageLinkListResponseObject) items.get(0);
			HealthCareApp myApp = (HealthCareApp) getApplication();
			for (HtmlPageLinkResponseObject msg : this.htmlLink.RESPONSE) {
				myApp.getHtmlLinkMap().put(
						ResponseMesssagType.toEnum(msg.description),
						msg.location);
			}
			if (isCancerInfoTapped) {
				launchAlertDialog();
				isCancerInfoTapped = false;
			}

		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(this, ero.getErrorText());
			} else
				UIUtilities.showServerError(this);

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(this);
		} else {
			UIUtilities.showServerError(this);
		}

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());
	}

	public void setVolumeOff() {
		SharedPreferences prefs = getSharedPreferences("voice", MODE_PRIVATE);

		SharedPreferences.Editor prefEdit = prefs.edit();
		prefEdit.putBoolean("volumeOnOfstatus", true);
		prefEdit.commit();
		isVolumeOn = true;
	}

	public void setVolumeOn() {
		SharedPreferences prefs = getSharedPreferences("voice", MODE_PRIVATE);

		SharedPreferences.Editor prefEdit = prefs.edit();
		prefEdit.putBoolean("volumeOnOfstatus", false);
		prefEdit.commit();
		isVolumeOn = false;
	}

	static final MenuDialog.MenuItem[] MAIN_MENU_LIST = {
			new MenuDialog.MenuItem(R.drawable.volume_on, R.string.voice_on),
			new MenuDialog.MenuItem(R.drawable.volume_mute, R.string.voice_off), };

	/** must be implemented to intercept menu key */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			new MenuDialog(this, this, MAIN_MENU_LIST, "Change voice control"); // launch
			// main
			// menu

			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_warning, R.string.app_exit_msg,
					R.string.exit, R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							finish();
						}
					}, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * MenuDialog.MenuSelectListener implementation, gets invoked when the user
	 * selects a menu item to access submenu construct MenuDialog as a response
	 */
	@Override
	public void onMenuSelect(int index, long id) {
		switch (index) {
		case 0:
			setVolumeOn();
			break;
		case 1:
			setVolumeOff();
			break;
		default:
			break;
		}
	}
}
