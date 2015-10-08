package app.nichepro.activities.healthcare;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import app.nichepro.HealthCareApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.db.UserRegistrationInfo;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientRegisterationActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	private String[] stateCode;

	Spinner salutation;
	EditText firstName;
	EditText middleName;
	EditText lastName;
	EditText dateOfBirth;
	EditText zipCode;
	EditText phoneNumber;
	EditText email;
	EditText password;
	EditText confirmPassword;
	Spinner specialization;
	TextView headerTitle;
	TextView specializationTxt;
	StringBuilder mDateOfBirth;
	Spinner city, state, gender;
	Button register;
	String tFirstName, tLastName, tMiddleName, tDob, tCity, tState, tZip,
			tPhone, tEmail, tPass, tConPass, tGender, tSalutation,
			tSpecialization;

	private int year;
	private int month;
	private int day;
	String loginType;
	WebRequestTask registrationRequestTask;
	ResponseMesssagType msgType;
	Dialog dialog;
	static final int DATE_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initialization();

	}

	void launchStateAlertDialog() {

		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.date_picker, null);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Select Date");
		// Set an EditText view to get user input
		alert.setView(textEntryView);

		final DatePicker datePicker = (DatePicker) textEntryView
				.findViewById(R.id.date_picker);
		Calendar currCal = Calendar.getInstance();

		datePicker.init(currCal.get(Calendar.YEAR), currCal.get(Calendar.MONTH), currCal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {

				if (isDateAfter(view)) {
					Calendar mCalendar = Calendar.getInstance();
					view.init(mCalendar.get(Calendar.YEAR),
							mCalendar.get(Calendar.MONTH),
							mCalendar.get(Calendar.DAY_OF_MONTH), this);
				}
			}

			private boolean isDateAfter(DatePicker tempView) {
				Calendar mCalendar = Calendar.getInstance();
				Calendar tempCalendar = Calendar.getInstance();
				tempCalendar.set(tempView.getYear(), tempView.getMonth(),
						tempView.getDayOfMonth(), 0, 0, 0);
				if (tempCalendar.after(mCalendar))
					return true;
				else
					return false;
			}
		});

		alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				year = datePicker.getYear();
				month = datePicker.getMonth() + 1;
				day = datePicker.getDayOfMonth();

				String M = month + "";
				String D = day + "";

				if (M != null && M.length() < 2) {
					M = "0" + M;
				}

				if (D != null && D.length() < 2) {
					D = "0" + D;
				}

				// set selected date into textview
				dateOfBirth.setText(new StringBuilder().append(M).append("-")
						.append(D).append("-").append(year).append(" "));
				mDateOfBirth = new StringBuilder().append(year).append("-")
						.append(M).append("-").append(D).append(" ")
						.append("00:00:00.000");

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

	void initialization() {
		Intent intent = getIntent();
		setStateCode();
		loginType = intent.getStringExtra("loginType");
		if (loginType != null && !loginType.isEmpty()) {
			TextView title = (TextView) findViewById(R.id.registrationtitle);
			title.setText(loginType);
		}
		headerTitle = (TextView) findViewById(R.id.registrationtitle);
		salutation = (Spinner) findViewById(R.id.sSalutation);
		firstName = (EditText) findViewById(R.id.etFirstName);
		middleName = (EditText) findViewById(R.id.etMiddleName);
		lastName = (EditText) findViewById(R.id.etLastName);
		dateOfBirth = (EditText) findViewById(R.id.etDOB);
		zipCode = (EditText) findViewById(R.id.etZipCode);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNum);
		email = (EditText) findViewById(R.id.etEmail);
		password = (EditText) findViewById(R.id.etRegPassword);
		confirmPassword = (EditText) findViewById(R.id.etRegConfirmPass);
		register = (Button) findViewById(R.id.bRegister);
		specialization = (Spinner) findViewById(R.id.sSpecialization);
		specializationTxt = (TextView) findViewById(R.id.textSpecialization);

		firstName.setFilters(new InputFilter[] { new InputFilter() {
			@Override
			public CharSequence filter(CharSequence src, int start, int end,
					Spanned dst, int dstart, int dend) {

				for (int i = start; i < end; i++) {
					if (!Character.isLetter(src.charAt(i))) {
						return "";
					}

				}
				return null;
			}
		} });

		middleName.setFilters(new InputFilter[] { new InputFilter() {
			@Override
			public CharSequence filter(CharSequence src, int start, int end,
					Spanned dst, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetter(src.charAt(i))) {
						return "";
					}
				}
				return null;
			}

		} });

		lastName.setFilters(new InputFilter[] { new InputFilter() {
			@Override
			public CharSequence filter(CharSequence src, int start, int end,
					Spanned dst, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetter(src.charAt(i))) {
						return "";
					}
				}
				return null;
			}

		} });

		if (loginType.compareTo(LoginType.Patient.name()) == 0) {
			specialization.setVisibility(View.GONE);
			specializationTxt.setVisibility(View.GONE);
			headerTitle.setText("Patient Registration");

		} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {
			headerTitle.setText("Provider Registration");

		} else {// Sponsor
			specialization.setVisibility(View.GONE);
			specializationTxt.setVisibility(View.GONE);
			headerTitle.setText("Sponsor Registration");

		}

		city = (Spinner) findViewById(R.id.sCity);
		state = (Spinner) findViewById(R.id.sState);
		gender = (Spinner) findViewById(R.id.sGender);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		register.setOnClickListener(this);
		dateOfBirth.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.etName:
			UIUtilities.showKeyboard(this, firstName);
			break;
		case R.id.bRegister:
			tFirstName = firstName.getText().toString();
			tLastName = lastName.getText().toString();
			if (mDateOfBirth != null) {
				tDob = mDateOfBirth.toString();
			}else
				tDob = dateOfBirth.toString();
			tCity = city.getSelectedItem().toString();
			tState = stateCode[state.getSelectedItemPosition()];
			tZip = zipCode.getText().toString();
			tPhone = phoneNumber.getText().toString();
			tEmail = email.getText().toString();
			tPass = password.getText().toString();
			tConPass = confirmPassword.getText().toString();
			tSpecialization = specialization.toString();
			tGender = gender.getSelectedItem().toString();
			tSalutation = salutation.getSelectedItem().toString();
			boolean emailCheck;
			emailCheck = UIUtilities.isEmailValid(tEmail);

			if (tFirstName.matches("") || tLastName.matches("")
					|| tDob.matches("") || tCity.matches("")
					|| tState.matches("") || tZip.matches("")
					|| tPhone.matches("") || tEmail.matches("")
					|| tPass.matches("") || tConPass.matches("")
					|| tGender.matches("")) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_error,
						R.string.fill_all_details, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			}

			else {
				if (tPhone.length() < 10) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_phone, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (!emailCheck) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_email, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (tPass.length() < 6 || tPass.length() > 30) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_password,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (!tPass.equals(tConPass)) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.password_cpassowrd_should_be_same,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else {
					final UserRegistrationInfo user = new UserRegistrationInfo();
					user.setUsername(this.tEmail);
					user.setFirstname(this.tFirstName);
					user.setLastname(this.tLastName);
					user.setGender(this.tGender);
					user.setSalutation(this.tSalutation);
					user.setMiddlename(this.tMiddleName);
					user.setDob(this.tDob);
					user.setCity(this.tCity);
					user.setState(this.tState);
					user.setZipcode(this.tZip);
					user.setPhonenumber(this.tPhone);
					user.setEmail(this.tEmail);
					user.setPassword(this.tPass);
					user.setCpassword(this.tConPass);
					user.setSpecialization(this.tSpecialization);
					registrationRequestTask = null;
					if (loginType.compareTo(LoginType.Patient.name()) == 0) {
						registrationRequestTask = new WebRequestTask(
								WebLinks.getLink(WebLinks.PATIENT_REGISTRATION),
								this, this);

						registrationRequestTask.AddParam(
								Constants.PARAM_roleTypeId,
								Constants.VALUE_PATIENT);

					} else if (loginType.compareTo(LoginType.Physician.name()) == 0) {
						registrationRequestTask = new WebRequestTask(
								WebLinks.getLink(WebLinks.DOCTOR_REGISTRATION),
								this, this);
						registrationRequestTask.AddParam(
								Constants.PARAM_specialization, specialization
										.getSelectedItem().toString());
						registrationRequestTask.AddParam(
								Constants.PARAM_roleTypeId,
								Constants.VALUE_HC_DOCTOR);

					} else {// Sponsor
						registrationRequestTask = new WebRequestTask(
								WebLinks.getLink(WebLinks.SPONSOR_REGISTRATION),
								this, this);
						registrationRequestTask.AddParam(
								Constants.PARAM_roleTypeId,
								Constants.VALUE_SPONSOR);

					}

					registrationRequestTask.AddParam(
							Constants.PARAM_salutation, user.getSalutation());
					registrationRequestTask.AddParam(Constants.PARAM_firstName,
							user.getFirstname());
					registrationRequestTask.AddParam(
							Constants.PARAM_middleName, user.getMiddlename());
					registrationRequestTask.AddParam(Constants.PARAM_lastName,
							user.getLastname());
					if (user.getGender().toString().compareTo("Male") == 0) {
						registrationRequestTask.AddParam(
								Constants.PARAM_gender, "M");
					}
					if (user.getGender().toString().compareTo("Female") == 0) {
						registrationRequestTask.AddParam(
								Constants.PARAM_gender, "F");
					}

					registrationRequestTask.AddParam(Constants.PARAM_birthDate,
							user.getDob());
					registrationRequestTask.AddParam(Constants.PARAM_mobileNo,
							user.getPhonenumber());
					registrationRequestTask.AddParam(
							Constants.PARAM_userLoginId, user.getUsername());
					registrationRequestTask
							.AddParam(Constants.PARAM_currentPassword,
									user.getPassword());
					registrationRequestTask.AddParam(
							Constants.PARAM_currentPasswordVerify,
							user.getCpassword());
					registrationRequestTask.AddParam(Constants.PARAM_state,
							user.getState());
					registrationRequestTask.AddParam(Constants.PARAM_city,
							user.getCity());
					registrationRequestTask.AddParam(Constants.PARAM_pinCode,
							user.getZipcode());
					registrationRequestTask.AddParam(Constants.PARAM_emailId,
							user.getEmail());

					registrationRequestTask.execute();

				}

			}

			break;
		case R.id.etDOB:
			launchStateAlertDialog();
			break;
		}
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

		if (this.msgType == ResponseMesssagType.UserRegistrationMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				LoginResponseObject lro = (LoginResponseObject) items.get(0);
				HealthCareApp myApp = (HealthCareApp) getApplication();
				myApp.setLoggedInUser(lro);
				if (lro.isLoggedIn()) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							"Registered successful", R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							});
				} else {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							"User Already Exist!", R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});

				}
			} else {

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
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	public void setStateCode() {
		this.stateCode = getResources()
				.getStringArray(R.array.state_code_array);
	}

}
