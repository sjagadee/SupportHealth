package app.nichepro.fragmenttabpatient.community;

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
import android.widget.ListView;
import android.widget.ViewAnimator;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.fragmenttabpatient.info.PatientInfoHospitalDetailFragment;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.PatientUpEventsListResponseObject;
import app.nichepro.model.PatientUpEventsResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientCommunityEventsFragments extends BaseFragment implements
		OnClickListener, ResponseParserListener {

	private WebRequestTask communityEventsRequestTask;
	private ResponseMesssagType msgType;

	private PatientUpEventsListResponseObject upEventsResponseObject;
	private PatientUpEventsListResponseObject pastEventsResponseObject;

	private Button btnUpEvents;
	private Button btnPrevEvents;
	private Boolean isToggle;
	private ViewAnimator viewAnimator;
	private ListView mUpcomingListView;
	private ListView mPastListView;
	private LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_events_screen, container,
				false);
		mInflater = inflater;
		mUpcomingListView = (ListView) view
				.findViewById(R.id.upcomingeventlist);
		mPastListView = (ListView) view.findViewById(R.id.posteventlist);

		// Creating the list adapter and populating the list

		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);

		btnUpEvents = (Button) view.findViewById(R.id.up_events);
		btnPrevEvents = (Button) view.findViewById(R.id.prev_events);
		btnUpEvents.setSelected(true);
		isToggle = true;
		btnUpEvents.setOnClickListener(this);
		btnPrevEvents.setOnClickListener(this);
		requestForUpComingEvents();
		return view;

	}

	void createUpcomingList() {
		ArrayAdapter<PatientUpEventsResponseObject> listAdapter = new UpcommingEventsListAdapter(
				mInflater.getContext(), mInflater,
				R.layout.upcoming_events_list_cell);

		for (PatientUpEventsResponseObject element : upEventsResponseObject.Event) {

			listAdapter.add(element);

		}
		mUpcomingListView.setAdapter(listAdapter);
		mUpcomingListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {

						if (upEventsResponseObject.Event.size() > position) {
							PatientInfoHospitalDetailFragment pihdf = new PatientInfoHospitalDetailFragment();
							PatientUpEventsResponseObject hro = upEventsResponseObject.Event
									.get(position);
							pihdf.url = hro.webLink;
							pihdf.header = hro.eventNote;
							mActivity.pushFragments(Constants.TAB_COMMUNITY,
									pihdf, true, true);

						} else {
							UIUtilities.showConfirmationAlert(getActivity(),
									R.string.dialog_title_information,
									"Event detail not found",
									R.string.dialog_button_Ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									});
						}

					}
				});
	}

	void createPastList() {
		ArrayAdapter<PatientUpEventsResponseObject> listAdapter1 = new UpcommingEventsListAdapter(
				mInflater.getContext(), mInflater,
				R.layout.upcoming_events_list_cell);

		for (PatientUpEventsResponseObject element : pastEventsResponseObject.Event) {
			listAdapter1.add(element);

		}
		mPastListView.setAdapter(listAdapter1);
		mPastListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {

						if (upEventsResponseObject.Event.size() > position) {
							PatientInfoHospitalDetailFragment pihdf = new PatientInfoHospitalDetailFragment();
							PatientUpEventsResponseObject hro = pastEventsResponseObject.Event
									.get(position);
							pihdf.url = hro.webLink;
							pihdf.header = hro.eventNote;
							mActivity.pushFragments(Constants.TAB_COMMUNITY,
									pihdf, true, true);

						} else {
							UIUtilities.showConfirmationAlert(getActivity(),
									R.string.dialog_title_information,
									"Event detail not found",
									R.string.dialog_button_Ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									});
						}

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.up_events:
			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnUpEvents.setTextColor(Color.BLACK);
				btnPrevEvents.setTextColor(Color.WHITE);
				btnUpEvents.setSelected(true);
				btnPrevEvents.setSelected(false);
			}
			isToggle = true;
			break;
		case R.id.prev_events:
			if (isToggle == true) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnUpEvents.setTextColor(Color.WHITE);
				btnPrevEvents.setTextColor(Color.BLACK);
				btnUpEvents.setSelected(false);
				btnPrevEvents.setSelected(true);
				if (this.pastEventsResponseObject == null) {
					requestForPastComingEvents();
				}
			}
			isToggle = false;
			break;
		}

	}

	public void requestForUpComingEvents() {
		communityEventsRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.LIST_OF_UPEVENTS), getActivity(),
				this);
		communityEventsRequestTask.execute();
	}

	public void requestForPastComingEvents() {
		communityEventsRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.LIST_OF_PASTEVENTS), getActivity(),
				this);
		communityEventsRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.UpcomingEventsMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {

				this.upEventsResponseObject = (PatientUpEventsListResponseObject) items
						.get(0);
				createUpcomingList();

			} else {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						"No Upcoming Events Found", R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			}
		} else if (this.msgType == ResponseMesssagType.PastEventsMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {

				this.pastEventsResponseObject = (PatientUpEventsListResponseObject) items
						.get(0);
				createPastList();

			} else {
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information,
						"No past Events found", R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			}
		}else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
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
