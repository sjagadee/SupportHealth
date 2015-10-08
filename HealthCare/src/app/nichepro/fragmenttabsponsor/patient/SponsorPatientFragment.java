package app.nichepro.fragmenttabsponsor.patient;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.fragmenttabpatient.ehr.PatientEHRFragment;
import app.nichepro.fragmenttabsponsor.request.SponsorRequestAdapter;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.SponsorShipListResponseObject;
import app.nichepro.model.SponsorShipResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class SponsorPatientFragment extends BaseFragment implements
		ResponseParserListener, UpdateUiFromAdapterListener {

	ArrayList<SponsorShipResponseObject> mSponsoredListData;
	ListView mSponsoredList;

	ArrayAdapter<SponsorShipResponseObject> mListAdapter;
	LayoutInflater mInflater;
	private WebRequestTask sponsorRequestTask;
	private ResponseMesssagType msgType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		this.mSponsoredListData = null;
		View view = inflater.inflate(R.layout.sponsored_patient_screen,
				container, false);
		mSponsoredList = (ListView) view.findViewById(R.id.sponsoredlist);
		requestForSponsorshipList();
		return view;
	}

	void requestForSponsorshipList() {
		sponsorRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ListOfSponsership), getActivity(),
				this);

		HealthCareApp myApp = (HealthCareApp) getActivity().getApplication();
		sponsorRequestTask.AddParam(Constants.PARAM_partyId, myApp
				.getLoggedInUser().getPartyId());

		sponsorRequestTask.execute();
	}

	void PopulateApprovedSponsorShip() {
		mListAdapter = new SponsorRequestAdapter(mInflater.getContext(),
				mInflater, R.layout.sponsor_request_cell_view, this, "Approved");
		for (SponsorShipResponseObject element : this.mSponsoredListData) {
			mListAdapter.add(element);
		}
		mSponsoredList.setAdapter(mListAdapter);
	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub
		if (message != null && message.length() > 0
				&& message.compareTo("EHR") == 0) {
			SponsorShipResponseObject element = this.mSponsoredListData
					.get(position);

			PatientEHRFragment pef = new PatientEHRFragment(LoginType.Sponsor,
					Constants.TAB_SPONSORED_PATIENT, element.partyId);
			// pef.isFromDoctor = true;
			// pef.mTabName = Constants.TAB_SPONSORED_PATIENT;
			// pef.mPatientId = element.partyId;
			mSponsorActivity.pushFragments(Constants.TAB_SPONSORED_PATIENT,
					pef, true, true);
		} else if (message != null && message.length() > 0
				&& message.compareTo("ViewRequest") == 0) {
			SponsorShipResponseObject element = this.mSponsoredListData
					.get(position);

			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, element.description,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});

		}
	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.SponsorshipListMessageType) {

			if (items != null && !items.isEmpty() && items.size() > 0) {
				SponsorShipListResponseObject palro = (SponsorShipListResponseObject) items
						.get(0);
				if (palro.RESPONSE.size() > 0) {
					{
						boolean isApprovedRequestFound = false;
						this.mSponsoredListData = new ArrayList<SponsorShipResponseObject>();
						for (SponsorShipResponseObject element : palro.RESPONSE) {
							if (element.status != null
									&& element.status.length() > 0
									&& element.status.compareTo("approve") == 0) {
								isApprovedRequestFound = true;
								mSponsoredListData.add(element);
							}
						}
						if (isApprovedRequestFound) {
							PopulateApprovedSponsorShip();
						} else {
							UIUtilities.showConfirmationAlert(getActivity(),
									R.string.dialog_title_information,
									"No Sponsored request found",
									R.string.dialog_button_Ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									});
						}
					}
				} else {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							"No Sponsored request found",
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
