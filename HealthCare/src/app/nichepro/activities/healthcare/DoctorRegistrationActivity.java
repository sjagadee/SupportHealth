package app.nichepro.activities.healthcare;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import app.nichepro.HealthCareApp;
import app.nichepro.model.db.DoctorRegistrationInfo;
import app.nichepro.util.UIUtilities;

public class DoctorRegistrationActivity extends Activity implements
		OnClickListener {

	EditText title, name, specialization, zipCode, phoneNumber, email,
			password, confirmPassword;
	Spinner city, state;
	Button register;
	String tTitle, tName, tSpecialization, tCity, tState, tZip, tPhone, tEmail,
			tPass, tConPass;
	Dialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctorregisteration);
		title = (EditText) findViewById(R.id.etTitle);
		name = (EditText) findViewById(R.id.etName);
		specialization = (EditText) findViewById(R.id.etSpecialization);
		zipCode = (EditText) findViewById(R.id.etZipCode);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNum);
		email = (EditText) findViewById(R.id.etEmail);
		password = (EditText) findViewById(R.id.etRegPassword);
		confirmPassword = (EditText) findViewById(R.id.etRegConfirmPass);
		register = (Button) findViewById(R.id.bRegister);
		city = (Spinner) findViewById(R.id.sCity);
		state = (Spinner) findViewById(R.id.sState);

		register.setOnClickListener(this);

	}

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.etName:
			UIUtilities.showKeyboard(this, name);
			break;
		case R.id.bRegister:
			tTitle= title.getText().toString();
			tName = name.getText().toString();
			tSpecialization = specialization.getText().toString();
			tCity = city.toString();
			tState = state.toString();
			tZip = zipCode.getText().toString();
			tPhone = phoneNumber.getText().toString();
			tEmail = email.getText().toString();
			tPass = password.getText().toString();
			tConPass = confirmPassword.getText().toString();
			boolean emailCheck;
			emailCheck = UIUtilities.isEmailValid(tEmail);

			if (tTitle.matches("") || tName.matches("")
					|| tSpecialization.matches("") || tCity.matches("")
					|| tState.matches("") || tZip.matches("")
					|| tPhone.matches("") || tEmail.matches("")
					|| tPass.matches("") || tConPass.matches("")) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						R.string.fill_all_details, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			}

			else {
				if (!tPass.equals(tConPass)) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							R.string.password_cpassowrd_should_be_same,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (!emailCheck) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							R.string.invalid_email, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (tPhone.length() < 10) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							R.string.invalid_phone, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (tPass.length() < 6) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							R.string.invalid_password,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else {
					final DoctorRegistrationInfo user = new DoctorRegistrationInfo();
					user.setTitle(this.tTitle);
					user.setSpecilization(this.tSpecialization);
					user.setUsername(this.tName);
					user.setCity(this.tCity);
					user.setState(this.tState);
					user.setZipcode(this.tZip);
					user.setPhonenumber(this.tPhone);
					user.setEmail(this.tEmail);
					user.setPassword(this.tPass);
					user.setCpassword(this.tConPass);
					progress = ProgressDialog.show(this, "Loading",
							"Please wait...");
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					 	public void run() {
							progress.dismiss();
							HealthCareApp mAppInstance = (HealthCareApp)getApplication();
							mAppInstance.setDoctorLoginInfo(user);

							finish();
						}
					}, 3000); // 3000 milliseconds

				}

			}
			break;

		}
	}

}
