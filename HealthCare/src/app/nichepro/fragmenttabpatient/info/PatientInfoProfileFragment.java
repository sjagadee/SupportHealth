package app.nichepro.fragmenttabpatient.info;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientInfoProfileFragment extends BaseFragment implements
		OnClickListener, ResponseParserListener {

	Button confirmProfile;
	EditText firstName, lastName, zipCode, phone, password, newPassword,
			checkNewPassword;
	Spinner state, city;

	TextView newPass, checkNewPass;
	
	String strFirstName, strMiddleName, strLastName, strState, strCity,
			strZipCode, strPhone, username, strPassword, strNewPassword,
			strCheckNewPassword;
	
	CheckBox changePassword;

	WebRequestTask profileRequestTask;
	ResponseMesssagType msgType;
	
	HealthCareApp myApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_info_profile_screen,
				container, false);

		myApp = (HealthCareApp) getActivity().getApplication();
		TextView patientName = (TextView) view.findViewById(R.id.patientTxt);
		patientName.setText(myApp.getLoggedInUser().LOGIN_DETAILS.lastName
				+ " " + myApp.getLoggedInUser().LOGIN_DETAILS.firstName);
		firstName = (EditText) view.findViewById(R.id.etFirstName);
		lastName = (EditText) view.findViewById(R.id.etLastName);
		zipCode = (EditText) view.findViewById(R.id.etZipCode);
		phone = (EditText) view.findViewById(R.id.etMobile);
		state = (Spinner) view.findViewById(R.id.sState);
		city = (Spinner) view.findViewById(R.id.sCity);
		password = (EditText) view.findViewById(R.id.etNowPassword);
		newPassword = (EditText) view.findViewById(R.id.etNewPassword);
		
		checkNewPassword = (EditText) view
				.findViewById(R.id.etCheckNewPassword);
		
		newPass = (TextView) view.findViewById(R.id.tvNewPassword);
		checkNewPass = (TextView) view.findViewById(R.id.tvCheckNewPassword);
		
		confirmProfile = (Button) view.findViewById(R.id.bPatientConfirm);
		changePassword =  (CheckBox) view.findViewById(R.id.changePassword);
		changePassword.setOnClickListener(this);
		
		confirmProfile.setOnClickListener(this);
		
		
		firstName.setText(myApp.getLoggedInUser().LOGIN_DETAILS.firstName);
		lastName.setText(myApp.getLoggedInUser().LOGIN_DETAILS.lastName);
		// state.setText(myApp.getLoggedInUser().LOGIN_DETAILS.state);
		// city.setText(myApp.getLoggedInUser().LOGIN_DETAILS.city);
		zipCode.setText(myApp.getLoggedInUser().LOGIN_DETAILS.pinCode);
		phone.setText(myApp.getLoggedInUser().LOGIN_DETAILS.mobileNo);
		
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

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.changePassword:
			if(changePassword.isChecked()){
				newPassword.setVisibility(View.VISIBLE);
				checkNewPassword.setVisibility(View.VISIBLE);
				newPass.setVisibility(View.VISIBLE);
				checkNewPass.setVisibility(View.VISIBLE);
			}else{
				newPassword.setVisibility(View.GONE);
				checkNewPassword.setVisibility(View.GONE);
				newPass.setVisibility(View.GONE);
				checkNewPass.setVisibility(View.GONE);
			}

			
			break;

		case R.id.bPatientConfirm:

			strFirstName = firstName.getText().toString();
			strLastName = lastName.getText().toString();
			strState = state.getSelectedItem().toString();
			strCity = city.getSelectedItem().toString();
			strZipCode = zipCode.getText().toString();
			strPhone = phone.getText().toString();
			strPassword = password.getText().toString();
			strNewPassword = newPassword.getText().toString();
			strCheckNewPassword = checkNewPassword.getText().toString();

			if (strFirstName.matches("") || strLastName.matches("")
					|| strState.matches("") || strCity.matches("")
					|| strZipCode.matches("") || strPhone.matches("")
					|| strPassword.matches("")) {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						R.string.fill_all_details, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			} else if(strPhone.length() < 10) {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						R.string.invalid_phone, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			}
			
			else
				editProfileList();

			break;
		}

	}

	public void editProfileList() {
		profileRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.LIST_OF_PROFILE), getActivity(), this);

		profileRequestTask.AddParam(Constants.PARAM_partyId, myApp
				.getLoggedInUser().getPartyId());

		profileRequestTask.AddParam(Constants.PARAM_firstName, strFirstName);
		profileRequestTask.AddParam(Constants.PARAM_middleName, strMiddleName);
		profileRequestTask.AddParam(Constants.PARAM_lastName, strLastName);
		profileRequestTask.AddParam(Constants.PARAM_mobileNo, strPhone);
		profileRequestTask.AddParam(Constants.PARAM_state, strState);
		profileRequestTask.AddParam(Constants.PARAM_city, strCity);
		profileRequestTask.AddParam(Constants.PARAM_pinCode, strZipCode);
		profileRequestTask.AddParam(Constants.PARAM_currentPassword,
				strPassword);
		profileRequestTask
				.AddParam(Constants.PARAM_newPassword, strNewPassword);
		profileRequestTask.AddParam(Constants.PARAM_newPasswordVerify,
				strCheckNewPassword);
		profileRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.ProfileUpdateMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					R.string.profile_updated, R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(getActivity(), ero.getErrorText());
			} else
				UIUtilities.showServerError(getActivity());

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(getActivity());
		}else{
			UIUtilities.showServerError(getActivity());
		}
	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(getActivity(), ex.getMessage());

	}

}
