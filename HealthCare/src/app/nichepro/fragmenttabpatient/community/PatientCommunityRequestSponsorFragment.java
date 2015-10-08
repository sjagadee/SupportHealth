package app.nichepro.fragmenttabpatient.community;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewAnimator;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientCommunityRequestSponsorFragment extends BaseFragment
		implements ResponseParserListener, OnClickListener {

	EditText sponsorRequest;
	EditText sponContent;
	EditText sponEmail;
	EditText sponSubject;
	EditText sponCC;
	String dcontent, demail, dsubject, dcc;
	Button sendRequest;
	Button sendInviteSponsor;
	Boolean isToggle;
	ViewAnimator viewAnimator;
	Button btnRequestSponsor, btnAddNewSponsor;
	private WebRequestTask sponsorRequestTask;
	private ResponseMesssagType msgType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.patient_request_for_sponsor,
				container, false);

		isToggle = false;
		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);

		btnAddNewSponsor = (Button) view.findViewById(R.id.bAddNewSponsor);
		btnRequestSponsor = (Button) view.findViewById(R.id.bRequestSponsor);

		sendInviteSponsor = (Button) view.findViewById(R.id.spon_send_request);
		sponsorRequest = (EditText) view.findViewById(R.id.etRequestSponsor);
		sendRequest = (Button) view.findViewById(R.id.bSendRequest);

		sponSubject = (EditText) view.findViewById(R.id.spon_subject);
		sponEmail = (EditText) view.findViewById(R.id.spon_email);
		sponContent = (EditText) view.findViewById(R.id.spon_content);
		sponCC = (EditText) view.findViewById(R.id.spon_cc);

		sendRequest.setOnClickListener(this);
		btnRequestSponsor.setSelected(false);
		btnRequestSponsor.setOnClickListener(this);
		btnAddNewSponsor.setSelected(true);
		btnAddNewSponsor.setOnClickListener(this);
		sendInviteSponsor.setOnClickListener(this);
		return view;
	}

	public void refreshEditfields() {
		UIUtilities.hideKeyboard(getActivity(), sponSubject);
		UIUtilities.hideKeyboard(getActivity(), sponEmail);
		UIUtilities.hideKeyboard(getActivity(), sponContent);
		UIUtilities.hideKeyboard(getActivity(), sponCC);
		sponSubject.setText("");
		sponEmail.setText("");
		sponContent.setText("");
		sponCC.setText("");
	}

	public void refreshField() {
		sponsorRequest.setText("");
	}

	public void addSponsorRequest() {

		dsubject = sponSubject.getText().toString();
		demail = sponEmail.getText().toString();
		dcontent = sponContent.getText().toString();
		dcc = sponCC.getText().toString();
		boolean emailCheck;
		emailCheck = UIUtilities.isEmailValid(demail);
		if (dcontent.matches("") || demail.matches("") || dsubject.matches("")) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					R.string.fill_all_details, R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		} else if (!emailCheck) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, R.string.invalid_email,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		} else {

			boolean isWrongEmailId = true;
			String wEmail = null;
			if (dcc != null && dcc.length() > 0) {
				String token[] = dcc.split(";");
				if (token != null && token.length > 0) {
					for (String email : token) {
						isWrongEmailId = UIUtilities.isEmailValid(email);
						if (!isWrongEmailId) {
							wEmail = email;
							break;
						}
					}
				}
			}

			if (!isWrongEmailId) {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						"You have entered wrong CC Email: " + wEmail,
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			} else {

				sponsorRequestTask = new WebRequestTask(
						WebLinks.getLink(WebLinks.SendJoinUsRequest),
						getActivity(), this);

				HealthCareApp myApp = (HealthCareApp) getActivity()
						.getApplication();
				sponsorRequestTask.AddParam(Constants.PARAM_partyId, myApp
						.getLoggedInUser().getPartyId());

				sponsorRequestTask.AddParam(Constants.PARAM_To, demail);
				if (!dcc.matches("")) {
					sponsorRequestTask.AddParam(Constants.PARAM_cc, dcc);
				}
				sponsorRequestTask.AddParam(Constants.PARAM_subject, dsubject);

				sponsorRequestTask.AddParam(Constants.PARAM_message, dcontent);

				sponsorRequestTask.execute();
			}
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
		if (this.msgType == ResponseMesssagType.RequstForSponsor) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Your request is submitted!", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							refreshField();
						}
					});
		} else if (this.msgType == ResponseMesssagType.JoinUsRequestMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Your Invite Request has been sent successfully!!",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							refreshEditfields();
						}
					});
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(getActivity(),
						ero.getErrorText());
			} else
				UIUtilities.showServerError(getActivity());

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(getActivity());
		} else {
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.spon_send_request:
			addSponsorRequest();
			break;

		case R.id.bSendRequest: {
			sponsorRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.RequestForSponsor),
					getActivity(), this);
			String message = null;
			message = sponsorRequest.getText().toString();
			HealthCareApp myApp = (HealthCareApp) getActivity()
					.getApplication();
			if (message != null && !message.isEmpty()) {
				sponsorRequestTask.AddParam(Constants.PARAM_partyId, myApp
						.getLoggedInUser().getPartyId());
				sponsorRequestTask.AddParam(Constants.PARAM_description,
						message);
				sponsorRequestTask.execute();
			} else {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						"Please enter description!", R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			}

		}
			break;

		case R.id.bRequestSponsor: {
			if (isToggle == true) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnAddNewSponsor.setTextColor(Color.BLACK);
				btnRequestSponsor.setTextColor(Color.WHITE);
				btnAddNewSponsor.setSelected(true);
				btnRequestSponsor.setSelected(false);
			}

			isToggle = false;

		}
			break;

		case R.id.bAddNewSponsor: {
			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnRequestSponsor.setTextColor(Color.BLACK);
				btnAddNewSponsor.setTextColor(Color.WHITE);
				btnAddNewSponsor.setSelected(false);
				btnRequestSponsor.setSelected(true);
			}
			isToggle = true;
		}
			break;
		}

	}

}
