package app.nichepro.activities.healthcare;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.nichepro.HealthCareApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class LoginActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	EditText userName, password;
	TextView loginTitle;
	Button login, createAccount, doctorRegistration, forgotPassword;
	String tName, tPass;
	String loginType;
	WebRequestTask loginRequestTask;
	ResponseMesssagType msgType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Intent intent = getIntent();

		loginType = intent.getStringExtra("loginType");
		loginTitle = (TextView) findViewById(R.id.loginTitle);
		if (loginType.compareTo(LoginType.Patient.name()) == 0) {
			loginTitle.setText(loginType + " Login");

		} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {

			TextView txt = (TextView) findViewById(R.id.provider);
			txt.setVisibility(View.VISIBLE);

			loginTitle.setText("Provider" + " Login");

		} else {// Sponsor
			loginTitle.setText(loginType + " Login");
		}

		userName = (EditText) findViewById(R.id.etUserName);

		if (loginType.compareTo(LoginType.Patient.name()) == 0) {
			userName.setHint("EHR id/ E-mail");

		} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {

			userName.setHint("EHR id/ E-mail");

			loginTitle.setText("Provider" + " Login");

		} else {// Sponsor
			userName.setHint("E-mail");
		}

		password = (EditText) findViewById(R.id.etPassword);
		login = (Button) findViewById(R.id.bLogin);
		createAccount = (Button) findViewById(R.id.bRegister);
		forgotPassword = (Button) findViewById(R.id.bForgotPassword);
		forgotPassword.setOnClickListener(this);
		login.setOnClickListener(this);
		createAccount.setOnClickListener(this);
		userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InitiatLoginRequest();
					return true;
				}
				return false;
			}
		});
		password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InitiatLoginRequest();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		HealthCareApp mAppInstance = (HealthCareApp) getApplication();
		LoginResponseObject user = mAppInstance.getLoggedInUser();
		if (user != null && user.LOGIN_DETAILS != null) {
			if (user.isLoggedIn() && user.isNewlyRegistered()) {
				if (loginType.compareTo(LoginType.Patient.name()) == 0) {
					Intent patientActivity = new Intent(LoginActivity.this,
							PatientTabActivity.class);
					startActivity(patientActivity);
				} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {
					Intent drActivity = new Intent(LoginActivity.this,
							DoctorTabActivity.class);
					startActivity(drActivity);
				} else {// Sponsor

				}
				user.setNewlyRegistered(false);
			}
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bLogin:
			//InitiatLoginRequest();
			Intent patientActivity = new Intent(LoginActivity.this,
					PatientTabActivity.class);
			startActivity(patientActivity);
			break;
		case R.id.bRegister: {
			Intent it = new Intent(LoginActivity.this,
					PatientRegisterationActivity.class);
			it.putExtra("loginType", loginType);
			startActivity(it);
		}
			break;
		case R.id.bForgotPassword: {
			Intent itm = new Intent(LoginActivity.this,
					PatientForgotPasswordActivity.class);
			startActivity(itm);
		}
			break;
		}
	}

	public void showAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information,
				R.string.invalid_credentials, R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public boolean onEditorAction(TextView exampleView, int actionId,
			KeyEvent event) {
		if (actionId == EditorInfo.IME_NULL
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			InitiatLoginRequest();
		}
		return true;
	}

	public void InitiatLoginRequest()

	{
		tName = userName.getText().toString();
		tPass = password.getText().toString();
		if (tName.trim().equals("") && tPass.trim().equals("")) {

			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					R.string.field_should_not_empty, R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (tName.trim().equals("")) {

			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, R.string.empty_user,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (tPass.trim().equals("")) {

			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, R.string.empty_pwd,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else {
			loginRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.LOGIN), this, this);

			if (loginType.compareTo(LoginType.Patient.name()) == 0) {
				loginRequestTask.AddParam(Constants.PARAM_partyTypeId,
						Constants.VALUE_PATIENT);
			} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {
				loginRequestTask.AddParam(Constants.PARAM_partyTypeId,
						Constants.VALUE_HC_DOCTOR);
			} else if (loginType.compareTo(LoginType.Sponsor.name()) == 0) {// Sponsor
				loginRequestTask.AddParam(Constants.PARAM_partyTypeId,
						Constants.VALUE_SPONSOR);
			}

			loginRequestTask.AddParam(Constants.PARAM_USER, tName);
			loginRequestTask.AddParam(Constants.PARAM_PASSWORD, tPass);
			loginRequestTask.execute();
		}
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		Log.i("Response", "as");
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.LoginMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				LoginResponseObject lro = (LoginResponseObject) items.get(0);
				HealthCareApp myApp = (HealthCareApp) getApplication();
				myApp.setLoggedInUser(lro);
				if (lro.isLoggedIn()) {
					if (loginType.compareTo(LoginType.Patient.name()) == 0) {
						Intent patientActivity = new Intent(LoginActivity.this,
								PatientTabActivity.class);
						startActivity(patientActivity);
					} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {
						Intent drActivity = new Intent(LoginActivity.this,
								DoctorTabActivity.class);
						startActivity(drActivity);
					} else if (loginType.compareTo(LoginType.Sponsor.name()) == 0) {// Sponsor
						Intent sActivity = new Intent(LoginActivity.this,
								SponsorTabActivity.class);
						startActivity(sActivity);
					}
				}

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

		Log.i("Response", "as");
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub

		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		String error = "Error";

		if (items != null && items.size() > 0) {
			ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
			error = ero.getErrorText();
		}
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, error,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}
}
