package app.nichepro.fragmenttabdoctor.patient;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.fragmenttabpatient.ehr.PatientEHRFragment;
import app.nichepro.fragmenttabpatient.ehr.PatientEhrShareHealthRecordFragment;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.PartyListResponseObject;
import app.nichepro.model.PartySharingListResponseObject;
import app.nichepro.model.PartySharingResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class DoctorPatientListFragment extends BaseFragment implements
		OnClickListener, ResponseParserListener, UpdateUiFromAdapterListener {
	private Boolean isToggle;

	private Button btnListOfPatient;
	private Button btnAddNewPatient;
	private ViewAnimator viewAnimator;
	private WebRequestTask shareEhrRequestTask;
	private ResponseMesssagType msgType;
	private EditText doctorContent;
	private EditText doctorEmail;
	private EditText doctorSubject;
	private EditText doctorCC;
	private Button sendRequest;
	private String dcontent, demail, dsubject, dcc;
	private HealthCareApp myApp;
	private PartySharingListResponseObject patientListObject;
	private ListView mPatientListview;
	ArrayAdapter<PartySharingResponseObject> mPatientlistAdapter;
	private String selectedPatientId;

	public void initializeHash() {

		myApp = (HealthCareApp) getActivity().getApplication();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initializeHash();
		View view = inflater.inflate(
				R.layout.doctor_patientlist_tab_first_screen, container, false);
		btnListOfPatient = (Button) view.findViewById(R.id.listofpatient);
		btnAddNewPatient = (Button) view.findViewById(R.id.addnewpatient);

		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);
		sendRequest = (Button) view.findViewById(R.id.doc_send_request);

		doctorSubject = (EditText) view.findViewById(R.id.doc_subject);
		doctorEmail = (EditText) view.findViewById(R.id.doc_email);
		doctorContent = (EditText) view.findViewById(R.id.doc_content);
		doctorCC = (EditText) view.findViewById(R.id.doc_cc);

		btnListOfPatient.setSelected(true);
		btnListOfPatient.setOnClickListener(this);
		btnAddNewPatient.setOnClickListener(this);
		sendRequest.setOnClickListener(this);
		btnAddNewPatient.setTextColor(Color.WHITE);
		isToggle = true;
		TextView nameTxt = (TextView) view.findViewById(R.id.patientName);
		nameTxt.setText(myApp.getLoggedInUser().LOGIN_DETAILS.lastName + " "
				+ myApp.getLoggedInUser().LOGIN_DETAILS.firstName);
		mPatientListview = (ListView) view.findViewById(R.id.patientlist);

		// Creating the list adapter and populating the list
		mPatientlistAdapter = new DoctorPatientListAdapter(
				inflater.getContext(), inflater,
				R.layout.doctor_patient_list_cell, this);

		requestForPatient();
		return view;
	}

	public void refreshEditfields() {
		UIUtilities.hideKeyboard(getActivity(), doctorSubject);
		UIUtilities.hideKeyboard(getActivity(), doctorEmail);
		UIUtilities.hideKeyboard(getActivity(), doctorContent);
		UIUtilities.hideKeyboard(getActivity(), doctorCC);
		doctorSubject.setText("");
		doctorEmail.setText("");
		doctorContent.setText("");
		doctorCC.setText("");
	}

	public void populateUI() {
		for (PartySharingResponseObject element : this.patientListObject.CCRSharingList) {
			mPatientlistAdapter.add(element);
		}
		mPatientListview.setAdapter(mPatientlistAdapter);
		mPatientListview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						updateUI(position);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.doc_send_request:
			addDoctorRequest();

			break;

		case R.id.listofpatient: {
			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnListOfPatient.setTextColor(Color.BLACK);
				btnAddNewPatient.setTextColor(Color.WHITE);
				btnListOfPatient.setSelected(true);
				btnAddNewPatient.setSelected(false);
			}
			isToggle = true;

		}
			break;

		case R.id.addnewpatient: {
			if (isToggle == true) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnListOfPatient.setTextColor(Color.WHITE);
				btnAddNewPatient.setTextColor(Color.BLACK);
				btnListOfPatient.setSelected(false);
				btnAddNewPatient.setSelected(true);
			}
			isToggle = false;

		}
			break;
		}
	}

	public void requestForPatient() {
		shareEhrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ListOfCCRSharing), getActivity(),
				this);
		shareEhrRequestTask.AddParam(Constants.PARAM_partyIdTo, myApp
				.getLoggedInUser().getPartyId());

		shareEhrRequestTask.execute();
	}

	public void requestForDoctor() {
		shareEhrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ACTOR_LIST), getActivity(), this);

		shareEhrRequestTask.AddParam(Constants.PARAM_partyType, "Doctor");
		shareEhrRequestTask.AddParam(Constants.PARAM_roleTypeId, "HC_PROVIDER");
		shareEhrRequestTask.execute();
	}

	public void addDoctorRequest() {

		dsubject = doctorSubject.getText().toString();
		demail = doctorEmail.getText().toString();
		dcontent = doctorContent.getText().toString();
		dcc = doctorCC.getText().toString();
		boolean emailCheck;
		emailCheck = UIUtilities.isEmailValid(demail);
		if (dcontent.matches("") || demail.matches("") || dsubject.matches("")) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					R.string.enter_all_details, R.string.dialog_button_Ok,
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

				shareEhrRequestTask = new WebRequestTask(
						WebLinks.getLink(WebLinks.SendJoinUsRequest),
						getActivity(), this);

				shareEhrRequestTask.AddParam(Constants.PARAM_partyId, myApp
						.getLoggedInUser().getPartyId());

				shareEhrRequestTask.AddParam(Constants.PARAM_To, demail);
				if (!dcc.matches("")) {
					shareEhrRequestTask.AddParam(Constants.PARAM_cc, dcc);
				}

				shareEhrRequestTask.AddParam(Constants.PARAM_subject, dsubject);
				shareEhrRequestTask.AddParam(Constants.PARAM_message, dcontent);

				shareEhrRequestTask.execute();
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
		if (this.msgType == ResponseMesssagType.DoctorListMessageType) {

			if (items != null && !items.isEmpty() && items.size() > 0) {
				PatientEhrShareHealthRecordFragment peshrf = new PatientEhrShareHealthRecordFragment();
				peshrf.isFromDoctor = true;
				peshrf.patientId = this.selectedPatientId;
				peshrf.tabName = Constants.TAB_PATIENT;
				peshrf.doctorListObject = (PartyListResponseObject) items
						.get(0);
				mDoctorActivity.pushFragments(Constants.TAB_PATIENT, peshrf,
						true, true);

			}
		} else if (this.msgType == ResponseMesssagType.PushCCREventListMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Your EHR has been shared successfully!!",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

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
		} else if (this.msgType == ResponseMesssagType.listCCRSharingMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {

				this.patientListObject = (PartySharingListResponseObject) items
						.get(0);
				if (this.patientListObject != null
						&& this.patientListObject.CCRSharingList != null
						&& this.patientListObject.CCRSharingList.size() > 0) {
					populateUI();
				} else {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							"Patient not found for you!",
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
				}
			}
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
	public void updateUI(String message) {
		// TODO Auto-generated method stub
		// Share ehr
		int position = Integer.parseInt(message);
		PartySharingResponseObject element = patientListObject.CCRSharingList
				.get(position);
		selectedPatientId = element.partyId;
		requestForDoctor();
	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub
		// view ehr
		PartySharingResponseObject element = patientListObject.CCRSharingList
				.get(position);

		PatientEHRFragment pef = new PatientEHRFragment(LoginType.Physician,
				Constants.TAB_PATIENT, element.partyId);
		// pef.isFromDoctor = true;
		// pef.mTabName = Constants.TAB_PATIENT;
		// pef.mPatientId = element.partyId;
		mDoctorActivity.pushFragments(Constants.TAB_PATIENT, pef, true, true);
	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub

	}

}
