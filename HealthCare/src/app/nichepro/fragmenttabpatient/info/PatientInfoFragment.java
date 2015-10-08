package app.nichepro.fragmenttabpatient.info;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.HospitalListResponseObject;
import app.nichepro.model.SymtomListResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientInfoFragment extends BaseFragment implements
		ResponseParserListener {

	ArrayList<String> infoData;
	WebRequestTask infoRequestTask;
	private ResponseMesssagType msgType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoData = new ArrayList<String>();
		infoData.add("Profile");
		// infoData.add("Cancer Information");
		infoData.add("Notes");
		infoData.add("Hospital Locator");
		infoData.add("Symptom Tracker");

		View view = inflater.inflate(R.layout.info_first_screen, container,
				false);

		ListView list = (ListView) view.findViewById(R.id.lvInfoItems);
		ArrayAdapter<String> listAdapter = new PatientInfoListAdapter(
				inflater.getContext(), inflater, R.layout.patient_list_cell);
		for (String element : infoData) {
			listAdapter.add(element);
		}
		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				loadView(position);
			}
		});
		return view;
	}

	public void loadView(int position) {

		switch (position) {
		case 0: {
			// My Profile
			mActivity.pushFragments(Constants.TAB_INFO,
					new PatientInfoProfileFragment(), true, true);
		}
			break;
		// case 1: {
		// // Cancer Information
		// PatientCommunityHealthPartnersFragment pchpf = new
		// PatientCommunityHealthPartnersFragment();
		// // HealthCareApp myApp = (HealthCareApp) getActivity()
		// // .getApplication();
		//
		// pchpf.url = WebLinks
		// .getHtmlLink("/CancerCare/images/cancer/breast_cancer.html");
		// // pchpf.title = "Cancer Forum";
		//
		// mActivity.pushFragments(Constants.TAB_INFO, pchpf, true, true);
		//
		// }
		// break;
		case 1: {
			// My Notes
			mActivity.pushFragments(Constants.TAB_INFO,
					new PatientInfoMyNotesFragment(LoginType.Patient, null,
							null), true, true);
		}
			break;
		case 2: {
			// Hospital Locator
			// PatientInfoHospitalListFragment pihlf = new
			// PatientInfoHospitalListFragment();
			// pihlf.mHospitalData = (HospitalListResponseObject) bro;
			mActivity.pushFragments(Constants.TAB_INFO,
					new PatientInfoHospitalListFragment(), true, true);
//			listOfHospitals();
		}
			break;
		case 3: {
			// Symptom Tracker
			listOfSymtoms();

		}
			break;

		default:
			break;
		}

	}

	public void listOfHospitals() {

		infoRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.HOSPITAL_LIST), getActivity(), this);
		infoRequestTask.AddParam(Constants.PARAM_state, "AL");
		infoRequestTask.execute();

	}

	public void listOfSymtoms() {

		infoRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ListOfSymtoms), getActivity(), this);
		infoRequestTask.execute();

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.HospitalListMessageType) {
			BaseResponseObject bro = items.get(0);
			if (bro instanceof HospitalListResponseObject) {
				PatientInfoHospitalListFragment pihlf = new PatientInfoHospitalListFragment();
				pihlf.mHospitalData = (HospitalListResponseObject) bro;
				mActivity.pushFragments(Constants.TAB_INFO, pihlf, true, true);
			} else {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information, bro.getResult(),
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								PatientInfoHospitalListFragment pihlf = new PatientInfoHospitalListFragment();
								pihlf.mHospitalData = null;
								mActivity.pushFragments(Constants.TAB_INFO,
										pihlf, true, true);
							}
						});
			}
		} else if (this.msgType == ResponseMesssagType.SymtomsListMessageType) {

			SymtomListResponseObject slro = (SymtomListResponseObject) items
					.get(0);

			PatientInfoSymptomFragment pihlf = new PatientInfoSymptomFragment();
			pihlf.setmSymtomsList(slro);
			mActivity.pushFragments(Constants.TAB_INFO, pihlf, true, true);
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
}